package com.randomthings.di

import com.randomthings.data.repository.DadJokesRepository
import com.randomthings.data.repository.FavouriteRepository
import com.randomthings.data.repository.ImageRepository
import com.randomthings.data.repository.MemeRepository
import com.randomthings.di.content.TestImageContentUseCase
import com.randomthings.di.content.TestJokesContentUsecase
import com.randomthings.domain.content.ImageContentUseCase
import com.randomthings.domain.joke.JokesContentUsecase
import com.randomthings.domain.joke.JokesContentUsecaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DomainModule::class]
)
class TestDomainModule {

    @Provides
    @Singleton
    fun provideImageContentUseCase(
        imageRepository: ImageRepository,
        memeRepository: MemeRepository,
        favouriteRepository: FavouriteRepository
    ): ImageContentUseCase = TestImageContentUseCase()


    @Provides
    @Singleton
    fun provideJokesContentUseCase(
        jokesRepository: DadJokesRepository,
    ): JokesContentUsecase = TestJokesContentUsecase()
}
