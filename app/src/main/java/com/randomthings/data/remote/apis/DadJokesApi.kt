package com.randomthings.data.remote.apis

import com.randomthings.data.model.DadJokes
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DadJokesApi {

    @GET(Endpoints.DAD_JOKES_SEARCH)
    @Headers("Accept: application/json")
    suspend fun searchDadJokes(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("term") term: String
    ): DadJokes
}
