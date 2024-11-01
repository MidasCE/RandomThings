package com.randomthings.domain.content

import com.randomthings.domain.entity.ImageContent
import kotlinx.coroutines.flow.Flow

interface ImageContentUseCase {

    suspend fun getRandomImageContent(): Flow<ImageContent>

    suspend fun getRandomImageContents(page: Int, limit : Int): Flow<List<ImageContent>>

    suspend fun getRandomMemeContent(): Flow<ImageContent>

    suspend fun favoriteContent(content: ImageContent): Flow<Long>

    suspend fun unFavoriteContent(content: ImageContent): Flow<Int>

    suspend fun getAllFavouriteContents(): Flow<List<ImageContent>>
}