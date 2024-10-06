package com.randomthings.domain.content

import com.randomthings.data.repository.ImageRepository
import com.randomthings.data.repository.MemeRepository
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.random.RandomThingViewModel.Companion.MAX_IMAGE_INDEX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContentUseCaseImpl(
    private val imageRepository: ImageRepository,
    private val memeRepository: MemeRepository
) : ContentUseCase {

    override suspend fun getRandomImageContent(): Flow<ImageContent> {
        val randomId = (0..MAX_IMAGE_INDEX).random()

        return imageRepository.getImageInfo(randomId)
            .map {
                val randomImageContent = ImageContent.RandomImageContent(
                    id = it.id,
                    width = it.width,
                    height = it.height,
                    author = it.author,
                    downloadUrl = it.downloadUrl
                )
                randomImageContent
            }
    }

    override suspend fun getRandomMemeContent(): Flow<ImageContent> {
        return memeRepository.getRandomMeme()
            .map {
                val randomImageContent = ImageContent.MemeImageContent(
                    author = it.author,
                    url = it.url
                )
                randomImageContent
            }
    }

}
