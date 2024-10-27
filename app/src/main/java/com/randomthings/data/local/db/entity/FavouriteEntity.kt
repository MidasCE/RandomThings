package com.randomthings.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "favourites", indices = [Index(value = ["type", "dataId"], unique = true)])
data class FavouriteEntity(

    @Json(name = "id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "type")
    val type: FavouriteDataType,

    @ColumnInfo(name = "dataId")
    val dataId: String,
)

enum class FavouriteDataType(val value: Int){
    Image(0),
    Meme(1)
}
