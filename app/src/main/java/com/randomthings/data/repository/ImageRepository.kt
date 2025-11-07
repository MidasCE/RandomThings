package com.randomthings.data.repository

import com.randomthings.AppDispatcher
import com.randomthings.data.local.db.AppDatabase
import com.randomthings.data.local.db.entity.ImageEntity
import com.randomthings.data.model.Image
import com.randomthings.data.remote.apis.PicSumApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val picSumApi: PicSumApi,
    private val appDatabase: AppDatabase,
    private val dispatcher: AppDispatcher
) {

    fun getImageInfo(
        imageId: Int,
    ): Flow<Image> =
        flow {
            emit(picSumApi.getImageInfo(imageId))
        }.map { it }

    fun getImageInfoList(
        page: Int,
        limit: Int,
    ): Flow<List<Image>> =
        flow {
            emit(picSumApi.getImageList(page, limit))
        }.map { it }

     fun saveImageToDB(imageEntity: ImageEntity): Flow<Long> =
        flow {
            emit(appDatabase.imageDao().insert(imageEntity))
        }.flowOn(dispatcher.io())

    fun removeImageFromDB(imageId: String): Flow<Int> =
        flow {
            emit(appDatabase.imageDao().delete(imageId))
        }.flowOn(dispatcher.io())

    fun getSavedImagesFromDB(imageIds: List<String>): Flow<List<ImageEntity>> =
        flow {
            emit(appDatabase.imageDao().getFromIds(imageIds))
        }.flowOn(dispatcher.io())

}
