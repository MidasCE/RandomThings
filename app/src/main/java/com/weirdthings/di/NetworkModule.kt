package com.weirdthings.di

import android.content.Context
import coil.ImageLoader
import com.weirdthings.BuildConfig
import com.weirdthings.di.qualifier.BasePicsSumUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiOkHttpClient(
    ): OkHttpClient = OkHttpClient.Builder()
        .build()

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
    ): ImageLoader = ImageLoader.Builder(context)
        .build()

    @Provides
    @Singleton
    @BasePicsSumUrl
    fun provideBasePicsSumUrl(): String = BuildConfig.BASE_PICS_SUM_URL
}