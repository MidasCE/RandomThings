package com.randomthings.data.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Networking {

    private const val NETWORK_CALL_TIMEOUT = 15

    fun <T> createService(baseUrl: String, client: OkHttpClient, service: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().build()
                )
            )
            .build()
            .create(service)
}
