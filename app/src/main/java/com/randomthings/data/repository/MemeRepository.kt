package com.randomthings.data.repository

import com.randomthings.AppDispatcher
import com.randomthings.data.local.db.AppDatabase
import com.randomthings.data.local.db.entity.MemeEntity
import com.randomthings.data.model.Meme
import com.randomthings.data.remote.apis.MemeApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemeRepository @Inject constructor(
    private val memeApi: MemeApi,
    private val appDatabase: AppDatabase,
    private val dispatcher: AppDispatcher
) {

    fun getRandomMeme(): Flow<Meme> =
        flow {
            emit(memeApi.getRandomMeme())
        }.flowOn(dispatcher.io())


    fun saveMemeToDB(memeEntity: MemeEntity): Flow<Long> =
        flow {
            emit(appDatabase.memeDao().insert(memeEntity))
        }.flowOn(dispatcher.io())

    fun removeImageFromDB(postLink: String): Flow<Int> =
        flow {
            emit(appDatabase.memeDao().delete(postLink))
        }.flowOn(dispatcher.io())

    fun getSavedImagesFromDB(postLinks: List<String>): Flow<List<MemeEntity>> =
        appDatabase.memeDao().getFromPostlinks(postLinks)
            .flowOn(dispatcher.io())
}
