package com.randomthings.presentation.meme

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.randomthings.domain.content.ImageContentUseCase
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemeViewModel @Inject constructor(
    private val imageContentUseCase: ImageContentUseCase,
) : BaseViewModel() {

    private val _randomMeme = MutableStateFlow<ImageContent.MemeImageContent?>(null)
    val randomMeme: StateFlow<ImageContent.MemeImageContent?> = _randomMeme.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private var memeCollectionJob: Job? = null

    companion object {
        const val TAG = "MemeViewModel"
    }

    init {
        fetchRandomMeme()
    }

    fun fetchRandomMeme() {
        memeCollectionJob?.cancel()

        memeCollectionJob = viewModelScope.launch {
            imageContentUseCase.getRandomMemeContent()
                .onStart { _isRefreshing.value = true }
                .catch { e ->
                    Log.e("ERROR", e.message.orEmpty())
                    _randomMeme.value = null // Clear on error
                    _isRefreshing.value = false
                }
                .collect { memeFromApi ->
                    if (memeFromApi is ImageContent.MemeImageContent) {
                        _randomMeme.value = memeFromApi
                    }
                    _isRefreshing.value = false
                }
        }
    }

    fun toggleContentFavourite() {
        val content = _randomMeme.value ?: return
        launchNetwork(
            error = { e ->
                Log.e("ERROR", e.message.orEmpty());
            }
        ) {
            val favourite = !(content.favourite)
            val callingFlow =
                if (favourite) {
                    imageContentUseCase.favoriteContent(content)
                } else {
                    imageContentUseCase.unFavoriteContent(content)
                }

            // We just trigger the call and let the
            // collector in fetchRandomMeme handle the UI update.
            callingFlow.first()
        }
    }
}