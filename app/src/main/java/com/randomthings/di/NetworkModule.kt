package com.randomthings.di

import android.content.Context
import coil.ImageLoader
import com.randomthings.BuildConfig
import com.randomthings.data.remote.Networking
import com.randomthings.data.remote.apis.MemeApi
import com.randomthings.data.remote.apis.PicSumApi
import com.randomthings.di.qualifier.BaseMemeUrl
import com.randomthings.di.qualifier.BasePicsSumUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiOkHttpClient(
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(getHttpLoggingInterceptor())
        .build()

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
    ): ImageLoader = ImageLoader.Builder(context)
        .build()

    @Provides
    @Singleton
    fun providePicSumApi(
        @BasePicsSumUrl basePicsSumUrl: String,
        okHttpClient: OkHttpClient
    ): PicSumApi = Networking.createService(
        basePicsSumUrl,
        okHttpClient,
        PicSumApi::class.java
    )

    @Provides
    @Singleton
    fun provideMemeApi(
        @BaseMemeUrl baseMemeUrl: String,
        okHttpClient: OkHttpClient
    ): MemeApi = Networking.createService(
        baseMemeUrl,
        okHttpClient,
        MemeApi::class.java
    )

    @Provides
    @Singleton
    @BasePicsSumUrl
    fun provideBasePicsSumUrl(): String = BuildConfig.BASE_PICS_SUM_URL

    @Provides
    @Singleton
    @BaseMemeUrl
    fun provideBaseMemeUrl(): String = BuildConfig.BASE_MEME_URL

    private fun getHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
}