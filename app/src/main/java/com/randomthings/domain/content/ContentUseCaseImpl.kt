package com.randomthings.domain.content

import com.randomthings.data.repository.ImageRepository
import com.randomthings.domain.entity.RandomImageContent
import com.randomthings.presentation.random.RandomThingViewModel.Companion.MAX_IMAGE_INDEX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContentUseCaseImpl(
    private val imageRepository: ImageRepository
) : ContentUseCase {

    override suspend fun getRandomImageContent(): Flow<RandomImageContent> {
        val randomId = (0..MAX_IMAGE_INDEX).random()

        return imageRepository.getImageInfo(randomId)
            .map {
                val randomImageContent = RandomImageContent(
                    id = it.id,
                    width = it.width,
                    height = it.height,
                    author = it.author,
                    downloadUrl = it.downloadUrl
                )
                randomImageContent
            }
    }

}
