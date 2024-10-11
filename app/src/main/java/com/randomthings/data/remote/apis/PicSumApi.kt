package com.randomthings.data.remote.apis

import com.randomthings.data.model.Image
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PicSumApi {

    @GET(Endpoints.IMAGE_INFO)
    suspend fun getImageInfo(
        @Path("id") imageId: Int,
    ): Image

    @GET(Endpoints.IMAGE_LIST)
    suspend fun getImageList(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<Image>
}
