package com.randomthings.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.util.Date

@Entity(tableName = "images")
data class ImageEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "width")
    val width: Int,

    @ColumnInfo(name = "height")
    val height: Int,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "downloadUrl")
    val downloadUrl: String,

    @ColumnInfo(name = "createdAt")
    val createdAt: Date,

)
