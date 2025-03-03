package com.randomthings.di.content

import com.randomthings.domain.content.ImageContentUseCase
import com.randomthings.domain.entity.ImageContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestImageContentUseCase : ImageContentUseCase {
    override suspend fun getRandomImageContent(): Flow<ImageContent> {
        val content = ImageContent.RandomImageContent(
                id = "id1",
                width = 200,
                height = 200,
                author = "author1",
                url = "url1",
                downloadUrl = "downloadUrl1",
                favourite = false,
            )
        return flowOf(content)
    }

    override suspend fun getRandomImageContents(page: Int, limit: Int): Flow<List<ImageContent>> {
        val result = listOf(
            ImageContent.RandomImageContent(
                id = "id1",
                width = 200,
                height = 200,
                author = "author1",
                url = "url1",
                downloadUrl = "downloadUrl1",
                favourite = false,
            ),
            ImageContent.RandomImageContent(
                id = "id2",
                width = 200,
                height = 200,
                author = "author2",
                url = "url2",
                downloadUrl = "downloadUrl2",
                favourite = true,
            ),
        )

        return flowOf(result)
    }

    override suspend fun getRandomMemeContent(): Flow<ImageContent> {
        val memeContent = ImageContent.MemeImageContent(
            postLink = "postLink",
            title = "title",
            url = "url",
            nsfw = false,
            author = "author",
            favourite = true,
        )

        return flowOf(memeContent)
    }

    override suspend fun favoriteContent(content: ImageContent): Flow<Long> {
        return flowOf(1)
    }

    override suspend fun unFavoriteContent(content: ImageContent): Flow<Int> {
        return flowOf(1)
    }

    override suspend fun getAllFavouriteContents(): Flow<List<ImageContent>> {
        return flowOf(emptyList())
    }
}
