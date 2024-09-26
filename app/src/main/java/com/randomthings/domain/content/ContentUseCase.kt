package com.randomthings.domain.content

import com.randomthings.domain.entity.RandomImageContent
import kotlinx.coroutines.flow.Flow

interface ContentUseCase {

    suspend fun getRandomImageContent(): Flow<RandomImageContent>
}