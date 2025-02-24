package com.randomthings.domain.content

import com.randomthings.data.local.db.entity.FavouriteDataType
import com.randomthings.data.local.db.entity.ImageEntity
import com.randomthings.data.local.db.entity.MemeEntity
import com.randomthings.data.repository.FavouriteRepository
import com.randomthings.data.repository.ImageRepository
import com.randomthings.data.repository.MemeRepository
import com.randomthings.domain.entity.ImageContent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.zip
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class ImageContentUseCaseImpl(
    private val imageRepository: ImageRepository,
    private val memeRepository: MemeRepository,
    private val favouriteRepository: FavouriteRepository
) : ImageContentUseCase {

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
                    postLink = it.postLink,
                    author = it.author,
                    title = it.title,
                    url = it.url,
                    nsfw = it.nsfw,
                    favourite = favouriteRepository.isFavourite(FavouriteDataType.Meme, it.postLink).single(),
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
            return imageRepository.saveImageToDB(imageEntity)
                .flatMapConcat {
                    favouriteRepository.saveAsFavourite(FavouriteDataType.Image, content.id)
                }
        }

        if (content is ImageContent.MemeImageContent)
        {
            val memeEntity = MemeEntity (
                postLink = content.postLink,
                author = content.author,
                title = content.title,
                url = content.url,
                nsfw = content.nsfw,
                createdAt = Date()
            )
            return memeRepository.saveMemeToDB(memeEntity)
                .flatMapConcat {
                    favouriteRepository.saveAsFavourite(FavouriteDataType.Meme, content.postLink)
                }
        }
        return flowOf(Long.MIN_VALUE)
    }

    override suspend fun unFavoriteContent(content: ImageContent): Flow<Int> {
        if (content is ImageContent.RandomImageContent)
        {
            return imageRepository.removeImageFromDB(content.id)
                .flatMapConcat {
                    favouriteRepository.removeFavourite(FavouriteDataType.Image, content.id)
                }
        }
        if (content is ImageContent.MemeImageContent)
        {
            return memeRepository.removeImageFromDB(content.postLink)
                .flatMapConcat {
                    favouriteRepository.removeFavourite(FavouriteDataType.Meme, content.postLink)
                }
        }
        return flowOf(Int.MIN_VALUE)
    }

    override suspend fun getAllFavouriteContents(): Flow<List<ImageContent>> {
        return favouriteRepository.getAllFavourites()
            .flatMapConcat { favouriteEntities ->

                val imageEntities = favouriteEntities.filter { it.type == FavouriteDataType.Image }.map { it.dataId }
                val memeEntities = favouriteEntities.filter { it.type == FavouriteDataType.Meme }.map { it.dataId }

                imageRepository.getSavedImagesFromDB(imageEntities)
                    .zip(memeRepository.getSavedImagesFromDB(memeEntities)) { images, memes ->
                        images to memes
                    }
            }.map { dataPair ->

                (dataPair.first + dataPair.second)
                    .sortedByDescending {
                        when (it) {
                            is ImageEntity -> it.createdAt
                            is MemeEntity -> it.createdAt
                            else -> error("")
                        }
                    }
                    .map {
                        when (it) {
                            is ImageEntity -> ImageContent.RandomImageContent(
                                id = it.id,
                                width = it.width,
                                height = it.height,
                                author = it.author,
                                url = it.url,
                                downloadUrl = it.downloadUrl,
                                favourite = favouriteRepository.isFavourite(FavouriteDataType.Image, it.id).single(),
                            )
                            is MemeEntity -> ImageContent.MemeImageContent(
                                postLink = it.postLink,
                                title = it.title,
                                nsfw = it.nsfw,
                                author = it.author,
                                url = it.url,
                                favourite = favouriteRepository.isFavourite(FavouriteDataType.Meme, it.postLink).single(),
                            )
                            else -> error("")
                        }
                    }
            }
    }

}
