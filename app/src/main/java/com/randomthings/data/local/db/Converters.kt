package com.randomthings.data.local.db

import androidx.room.TypeConverter
import com.randomthings.data.local.db.entity.FavouriteDataType
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toFavouriteDataType(value: String) = enumValueOf<FavouriteDataType>(value)

    @TypeConverter
    fun fromFavouriteDataType(value: FavouriteDataType) = value.name

}