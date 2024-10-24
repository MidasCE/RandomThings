package com.randomthings.presentation.random

import android.util.Log
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

    private var _pageSet = mutableListOf<Int>()
    private var currentPageIndex = 0

    companion object {
        const val TAG = "RandomThingViewModel"
        const val MAX_PAGE = 100
    }

    init {
        randomSetNumber()
        fetchRandomContent()
    }

    fun refreshData() {
        currentPageIndex = 0
        _randomImages.clear()
        randomSetNumber()
        fetchRandomContent()
    }

    fun fetchRandomContent() {
        if (currentPageIndex >= _pageSet.count())
        {
            return
        }

        launchNetwork(
            error = { e ->
                Log.e("ERROR", e.message.orEmpty());
            }
        ) {
            contentUseCase.getRandomImageContents(_pageSet[currentPageIndex++], 10)
                .collect {
                    _randomImages.addAll(it)
                }
        }
    }

    private fun randomSetNumber() {
        _pageSet = generateSequence {
                (1..MAX_PAGE).random()
            }
            .distinct()
            .take(100)
            .toMutableList()
    }

    fun toggleContentFavourite(content: ImageContent.RandomImageContent) {
        launchNetwork(
            error = { e ->
                Log.e("ERROR", e.message.orEmpty());
            }
        ) {
            val favourite = !(content.favourite)
            val callingFlow =
                if (favourite)
                {
                    contentUseCase.favoriteContent(content)
                } else {
                    contentUseCase.unFavoriteContent(content)
                }
            callingFlow.collect {
                val index = _randomImages.indexOf(content)
                if (index > -1) {
                    _randomImages[index] = content.copy(
                        favourite = favourite,
                    )
                }
            }
        }
    }


}