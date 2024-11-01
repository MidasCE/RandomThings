package com.randomthings.di

import com.randomthings.data.repository.DadJokesRepository
import com.randomthings.data.repository.FavouriteRepository
import com.randomthings.data.repository.ImageRepository
import com.randomthings.data.repository.MemeRepository
import com.randomthings.domain.content.ImageContentUseCase
import com.randomthings.domain.content.ImageContentUseCaseImpl
import com.randomthings.domain.joke.JokesContentUsecase
import com.randomthings.domain.joke.JokesContentUsecaseImpl
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
    fun provideImageContentUseCase(
        imageRepository: ImageRepository,
        memeRepository: MemeRepository,
        favouriteRepository: FavouriteRepository
    ): ImageContentUseCase = ImageContentUseCaseImpl(
        imageRepository, memeRepository, favouriteRepository
    )

    @Provides
    @Singleton
    fun provideJokesContentUseCase(
        jokesRepository: DadJokesRepository,
    ): JokesContentUsecase = JokesContentUsecaseImpl(
        jokesRepository
    )
}