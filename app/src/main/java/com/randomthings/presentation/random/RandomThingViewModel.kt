package com.randomthings.presentation.random

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.randomthings.domain.content.ImageContentUseCase
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomThingViewModel @Inject constructor(
    private val imageContentUseCase: ImageContentUseCase,
) : BaseViewModel() {

    private val _randomImages = mutableStateListOf<ImageContent>()

    val randomImages: List<ImageContent> = _randomImages

    // --- State for Pull-to-Refresh and Pagination Loading ---
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()
    // ---

    private var _pageSet = mutableListOf<Int>()
    private var currentPageIndex = 0

    companion object {
        const val TAG = "RandomThingViewModel"
        const val MAX_PAGE = 100
    }

    init {
        // Start refreshing
        refreshData()

        observeFavoriteChanges()
    }

    /**
     * This collector is the *only* thing responsible for updating
     * the 'favourite' property of items *already in the list*.
     */
    private fun observeFavoriteChanges() {
        viewModelScope.launch {
            imageContentUseCase.observeFavouriteImageIds()
                .catch { e -> Log.e(TAG, "Error observing favorites: ${e.message}") }
                .collect { favouriteIds ->
                    // We have the new list of ALL favorite IDs.
                    // Let's update our list in-place.
                    val updatedList = _randomImages.map { content ->
                        if (content is ImageContent.RandomImageContent) {
                            // Check if this content's ID is in the new list of favorites
                            val isFavourite = content.id in favouriteIds
                            if (content.favourite != isFavourite) {
                                content.copy(favourite = isFavourite)
                            } else {
                                content
                            }
                        } else {
                            content
                        }
                    }

                    _randomImages.clear()
                    _randomImages.addAll(updatedList)
                }
        }
    }

    fun refreshData() {
        currentPageIndex = 0
        _pageSet.clear()
        _randomImages.clear()
        randomSetNumber()
        fetchRandomContent(isRefresh = true)
    }

    fun fetchRandomContent(isRefresh: Boolean = false) {
        if (currentPageIndex >= _pageSet.count())
        {
            return
        }
        if (_isLoadingMore.value && !isRefresh)
        {
            return
        }

        if (isRefresh) {
            _isRefreshing.value = true
        } else {
            _isLoadingMore.value = true
        }

        launchNetwork(
            error = { e ->
                Log.e("ERROR", e.message.orEmpty());
            }
        ) {
            val newImages = imageContentUseCase.getRandomImageContents(
                _pageSet[currentPageIndex++],
                10
            ).first()

            _randomImages.addAll(newImages)

            if (isRefresh) {
                _isRefreshing.value = false
            } else {
                _isLoadingMore.value = false
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
                    imageContentUseCase.favoriteContent(content)
                } else {
                    imageContentUseCase.unFavoriteContent(content)
                }
            callingFlow.first()
        }
    }


}