package com.randomthings.presentation.meme

import androidx.compose.runtime.mutableStateListOf
import com.randomthings.domain.content.ContentUseCase
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemeViewModel @Inject constructor(
    private val contentUseCase: ContentUseCase,
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
        launchNetwork() {
            contentUseCase.getRandomMemeContent()
                .collect {
                    _randomMeme.clear()
                    _randomMeme.add(it)
                }
        }
    }
}