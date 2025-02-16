package com.randomthings.presentation.meme

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.randomthings.domain.content.ImageContentUseCase
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemeViewModel @Inject constructor(
    private val imageContentUseCase: ImageContentUseCase,
) : BaseViewModel() {


    private val _randomMeme = mutableStateListOf<ImageContent>()

    val randomMemes: List<ImageContent> = _randomMeme

    companion object {
        const val TAG = "MemeViewModel"
    }

    init {
        fetchRandomMeme()
    }

    fun fetchRandomMeme() {
        launchNetwork(
            error = { e ->
                Log.e("ERROR", e.message.orEmpty());
            }
        )  {
            imageContentUseCase.getRandomMemeContent()
                .collect {
                    _randomMeme.clear()
                    _randomMeme.add(it)
                }
        }
    }

    fun toggleContentFavourite(content: ImageContent.MemeImageContent) {
        launchNetwork(
            error = { e ->
                Log.e("ERROR", e.message.orEmpty());
            }
        ) {
            val favourite = !(content.favourite)
            val callingFlow =
                if (favourite)
                {
                    imageContentUseCase.favoriteContent(content)
                } else {
                    imageContentUseCase.unFavoriteContent(content)
                }
            callingFlow.collect {
                val index = _randomMeme.indexOf(content)
                if (index > -1) {
                    _randomMeme[index] = content.copy(
                        favourite = favourite,
                    )
                }
            }
        }
    }
}