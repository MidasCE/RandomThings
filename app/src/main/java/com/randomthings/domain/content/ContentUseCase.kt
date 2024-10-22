package com.randomthings.domain.content

import com.randomthings.domain.entity.ImageContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ContentUseCase {

    suspend fun getRandomImageContent(): Flow<ImageContent>

    suspend fun getRandomImageContents(page: Int, limit : Int): Flow<List<ImageContent>>

    suspend fun getRandomMemeContent(): Flow<ImageContent>

    suspend fun favoriteContent(content: ImageContent): Flow<Long>

    suspend fun unFavoriteContent(content: ImageContent): Flow<Int>
}