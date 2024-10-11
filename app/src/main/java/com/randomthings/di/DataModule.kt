package com.randomthings.di

import android.content.Context
import androidx.room.Room
import com.randomthings.data.local.db.AppDatabase
import com.randomthings.di.qualifier.DatabaseInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    @DatabaseInfo
    fun provideDatabaseName(): String = "random-db"

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseInfo dbName: String
    ): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java,
        dbName
    ).build()

}