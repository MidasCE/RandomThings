package com.randomthings.domain.content

import com.randomthings.domain.entity.ImageContent
import kotlinx.coroutines.flow.Flow

interface ContentUseCase {

    suspend fun getRandomImageContent(): Flow<ImageContent>

    suspend fun getRandomImageContents(page: Int, limit : Int): Flow<List<ImageContent>>

    suspend fun getRandomMemeContent(): Flow<ImageContent>
}