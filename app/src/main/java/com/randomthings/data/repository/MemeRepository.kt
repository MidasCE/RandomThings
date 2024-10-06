package com.randomthings.data.repository

import com.randomthings.data.entity.MemeEntity
import com.randomthings.data.remote.apis.MemeApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemeRepository @Inject constructor(
    private val memeApi: MemeApi,
) {

    suspend fun getRandomMeme(): Flow<MemeEntity> =
        flow {
            emit(memeApi.getRandomMeme())
        }.map { it }

}
