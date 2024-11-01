package com.randomthings.data.repository

import com.randomthings.data.model.DadJokes
import com.randomthings.data.remote.apis.DadJokesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DadJokesRepository @Inject constructor(
    private val dadJokesApi: DadJokesApi,
) {

    suspend fun searchJokes(page: Int, limit: Int, searchTerm: String): Flow<DadJokes> =
        flow {
            emit(dadJokesApi.searchDadJokes(page, limit, searchTerm))
        }

}
