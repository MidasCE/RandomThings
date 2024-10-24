package com.randomthings.data.repository

import com.randomthings.AppDispatcher
import com.randomthings.data.local.db.AppDatabase
import com.randomthings.data.local.db.entity.FavouriteDataType
import com.randomthings.data.local.db.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FavouriteRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val dispatcher: AppDispatcher
) {

    suspend fun saveAsFavourite(dataType: FavouriteDataType, dataId: String): Flow<Long> =
        flow {
            val favouriteEntity = FavouriteEntity (
                type = dataType,
                dataId = dataId,
            )
            emit(appDatabase.favouriteDao().insert(favouriteEntity))
        }.flowOn(dispatcher.io())

    suspend fun removeFavourite(dataType: FavouriteDataType, dataId: String): Flow<Int> =
        flow {
            val favouriteEntity = FavouriteEntity (
                type = dataType,
                dataId = dataId,
            )
            emit(appDatabase.favouriteDao().delete(favouriteEntity))
        }.flowOn(dispatcher.io())

    suspend fun isFavourite(dataType: FavouriteDataType, dataId: String): Flow<Boolean> =
        flow {
            emit(appDatabase.favouriteDao().isRowIsExist(dataType, dataId))
        }.flowOn(dispatcher.io())

    suspend fun getAllFavourites(): Flow<List<FavouriteEntity>> =
        flow {
            emit(appDatabase.favouriteDao().getAll())
        }.flowOn(dispatcher.io())
}
