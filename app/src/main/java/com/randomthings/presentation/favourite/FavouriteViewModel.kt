package com.randomthings.presentation.favourite

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.randomthings.domain.content.ImageContentUseCase
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val imageContentUseCase: ImageContentUseCase,
) : BaseViewModel() {

    companion object {
        const val TAG = "FavouriteViewModel"
    }

    private val _favouriteContents = mutableStateListOf<ImageContent>()

    val favouriteContents: List<ImageContent> = _favouriteContents

    fun fetchFavouriteContents() {
        launchNetwork(
            error = { e ->
                Log.e("ERROR", e.message.orEmpty());
            }
        ) {
            val items = imageContentUseCase.getAllFavouriteContents().first()
            _favouriteContents.clear()
            _favouriteContents.addAll(items)
        }
    }


    fun toggleContentFavourite(imageContent: ImageContent) {
        launchNetwork(
            error = { e ->
                Log.e("ERROR", e.message.orEmpty());
            }
        ) {
            val favourite = !(imageContent.favourite)
            val callingFlow =
                if (favourite) {
                    imageContentUseCase.favoriteContent(imageContent)
                } else {
                    imageContentUseCase.unFavoriteContent(imageContent)
                }
            callingFlow.collect {
                val index = favouriteContents.indexOfFirst { it.url == imageContent.url }
                if (index > -1) {
                    _favouriteContents[index] = when (imageContent) {
                        is ImageContent.RandomImageContent -> imageContent.copy(favourite = favourite)
                        is ImageContent.MemeImageContent -> imageContent.copy(favourite = favourite)
                    }
                }
            }
        }
    }

}
