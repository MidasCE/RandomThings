package com.randomthings.data.remote.apis

import com.randomthings.data.model.Meme
import retrofit2.http.GET

interface MemeApi {

    @GET(Endpoints.RANDOM_MEME)
    suspend fun getRandomMeme(): Meme

}