package com.randomthings.data.repository

import com.randomthings.data.remote.apis.PicSumApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val picSumApi: PicSumApi,
) {

    suspend fun getImageInfo(
        imageId: Int,
    ): Flow<com.randomthings.data.entity.ImageEntity> =
        flow {
            emit(picSumApi.getImageInfo(imageId))
        }.map { it }

    suspend fun getImageInfoList(
        page: Int,
        limit: Int,
    ): Flow<List<com.randomthings.data.entity.ImageEntity>> =
        flow {
            emit(picSumApi.getImageList(page, limit))
        }.map { it }

}
