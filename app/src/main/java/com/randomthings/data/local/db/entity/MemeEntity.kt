package com.randomthings.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "memes")
data class MemeEntity(

    @PrimaryKey
    @ColumnInfo(name = "postLink")
    val postLink: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "nsfw")
    val nsfw: Boolean,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "createdAt")
    val createdAt: Date,

)
