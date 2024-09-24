package com.weirdthings.data.repository

import com.weirdthings.data.remote.apis.PicSumApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val picSumApi: PicSumApi,
) {

    suspend fun getImageInfo(
        imageId: String,
    ): Flow<com.weirdthings.data.entity.ImageInfo> =
        flow {
            emit(picSumApi.getImageInfo(imageId))
        }.map { it }

}
