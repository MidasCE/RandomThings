package com.weirdthings.data.remote.apis

import com.weirdthings.data.entity.ImageInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface PicSumApi {

    @GET(Endpoints.IMAGE_INFO)
    suspend fun getImageInfo(
        @Path("id") contentId: String,
    ): ImageInfo
}
