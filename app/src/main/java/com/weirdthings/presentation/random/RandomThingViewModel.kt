package com.weirdthings.presentation.random

import androidx.compose.runtime.mutableStateListOf
import com.weirdthings.data.entity.ImageInfo
import com.weirdthings.data.repository.ImageRepository
import com.weirdthings.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class RandomThingViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
) : BaseViewModel() {

    private val _randomImagesInfo = mutableStateListOf<ImageInfo>()

    val randomImagesInfo: List<ImageInfo> = _randomImagesInfo

    companion object {
        const val TAG = "RandomThingViewModel"
        const val MAX_IMAGE_INDEX = 1084
    }

    init {
        initRandomContent()
    }

    private fun initRandomContent() {
        val randomId = (0..MAX_IMAGE_INDEX).random()
        launchNetwork() {
            imageRepository.getImageInfo(randomId)
                .catch {
                    throw it
                }
        }
    }
}