package com.randomthings.domain.content

import com.randomthings.domain.entity.ImageContent
import kotlinx.coroutines.flow.Flow

interface ImageContentUseCase {

    fun getRandomImageContent(): Flow<ImageContent>

    fun getRandomImageContents(page: Int, limit : Int): Flow<List<ImageContent>>

    fun getRandomMemeContent(): Flow<ImageContent>

    fun favoriteContent(content: ImageContent): Flow<Long>

    fun unFavoriteContent(content: ImageContent): Flow<Int>

    fun getAllFavouriteContents(): Flow<List<ImageContent>>
}