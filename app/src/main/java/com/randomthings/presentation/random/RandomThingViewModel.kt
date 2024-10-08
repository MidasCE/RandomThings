package com.randomthings.presentation.random

import androidx.compose.runtime.mutableStateListOf
import com.randomthings.domain.content.ContentUseCase
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RandomThingViewModel @Inject constructor(
    private val contentUseCase: ContentUseCase,
) : BaseViewModel() {

    private val _randomImages = mutableStateListOf<ImageContent>()

    val randomImages: List<ImageContent> = _randomImages

    companion object {
        const val TAG = "RandomThingViewModel"
        const val MAX_IMAGE_INDEX = 1084
    }

    init {
        fetchRandomContent()
    }

    fun fetchRandomContent() {
        launchNetwork() {
            contentUseCase.getRandomImageContents(1, 10)
                .collect {
                    _randomImages.clear()
                    _randomImages.addAll(it)
                }
        }
    }
}