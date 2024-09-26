package com.randomthings.data.remote.apis

import com.randomthings.data.entity.ImageInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface PicSumApi {

    @GET(Endpoints.IMAGE_INFO)
    suspend fun getImageInfo(
        @Path("id") imageId: Int,
    ): ImageInfo
}
