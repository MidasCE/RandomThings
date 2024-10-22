package com.randomthings.di

import com.randomthings.data.repository.FavouriteRepository
import com.randomthings.data.repository.ImageRepository
import com.randomthings.data.repository.MemeRepository
import com.randomthings.domain.content.ContentUseCase
import com.randomthings.domain.content.ContentUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideContentUseCase(
        imageRepository: ImageRepository,
        memeRepository: MemeRepository,
        favouriteRepository: FavouriteRepository
    ): ContentUseCase = ContentUseCaseImpl(
        imageRepository, memeRepository, favouriteRepository
    )
}