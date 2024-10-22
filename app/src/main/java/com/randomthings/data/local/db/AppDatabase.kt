package com.randomthings.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.randomthings.data.local.db.dao.FavouriteDao
import com.randomthings.data.local.db.dao.ImageDao
import com.randomthings.data.local.db.entity.FavouriteEntity
import com.randomthings.data.local.db.entity.ImageEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        FavouriteEntity::class,
        ImageEntity::class,
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao

    abstract fun imageDao(): ImageDao
}