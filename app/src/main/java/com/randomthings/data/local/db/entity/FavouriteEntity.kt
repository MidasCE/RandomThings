package com.randomthings.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "type")
    val type: FavouriteDataType,

    @ColumnInfo(name = "dataId")
    val dataId: String,
)

enum class FavouriteDataType(val value: Int){
    Image(0)
}
