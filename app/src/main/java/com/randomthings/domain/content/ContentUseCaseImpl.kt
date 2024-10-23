package com.randomthings.domain.content

import com.randomthings.data.local.db.entity.FavouriteDataType
import com.randomthings.data.local.db.entity.ImageEntity
import com.randomthings.data.repository.FavouriteRepository
import com.randomthings.data.repository.ImageRepository
import com.randomthings.data.repository.MemeRepository
import com.randomthings.domain.entity.ImageContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import java.util.Date

class ContentUseCaseImpl(
    private val imageRepository: ImageRepository,
    private val memeRepository: MemeRepository,
    private val favouriteRepository: FavouriteRepository
) : ContentUseCase {

    companion object {
        const val MAX_IMAGE_INDEX = 1024
    }

    override suspend fun getRandomImageContent(): Flow<ImageContent> {
        val randomId = (0..MAX_IMAGE_INDEX).random()

        return imageRepository.getImageInfo(randomId)
            .map {
                val randomImageContent = ImageContent.RandomImageContent(
                    id = it.id,
                    width = it.width,
                    height = it.height,
                    author = it.author,
                    url = it.url,
                    downloadUrl = it.downloadUrl,
                    favourite = favouriteRepository.isFavourite(FavouriteDataType.Image, it.id).single(),
                )
                randomImageContent
            }
    }

    override suspend fun getRandomImageContents(page: Int, limit : Int): Flow<List<ImageContent>> {
        return imageRepository.getImageInfoList(page, limit)
            .map { list ->
                list.map {
                    val isFavourite = favouriteRepository.isFavourite(FavouriteDataType.Image, it.id).single()
                    val randomImageContent = ImageContent.RandomImageContent(
                        id = it.id,
                        width = it.width,
                        height = it.height,
                        author = it.author,
                        url = it.url,
                        downloadUrl = it.downloadUrl,
                        favourite = isFavourite,
                    )
                    randomImageContent
                }
            }
    }

    override suspend fun getRandomMemeContent(): Flow<ImageContent> {
        return memeRepository.getRandomMeme()
            .map {
                val randomImageContent = ImageContent.MemeImageContent(
                    author = it.author,
                    url = it.url,
                    favourite = false,
                )
                randomImageContent
            }
    }

    override suspend fun favoriteContent(content: ImageContent): Flow<Long> {
        if (content is ImageContent.RandomImageContent)
        {
            val imageEntity = ImageEntity (
                id = content.id,
                author = content.author,
                url = content.url,
                downloadUrl = content.downloadUrl,
                width = content.width,
                height = content.height,
                createdAt = Date()
            )
            imageRepository.saveImageToDB(imageEntity)
            return favouriteRepository.saveAsFavourite(FavouriteDataType.Image, content.id)
        }
        return flowOf(Long.MIN_VALUE)
    }

    override suspend fun unFavoriteContent(content: ImageContent): Flow<Int> {
        if (content is ImageContent.RandomImageContent)
        {
            imageRepository.removeImageFromDB(content.id)
            return favouriteRepository.removeFavourite(FavouriteDataType.Image, content.id)
        }
        return flowOf(Int.MIN_VALUE)
    }

}
