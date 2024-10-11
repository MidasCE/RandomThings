package com.randomthings.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meme(

    @Json(name = "postLink")
    val postLink: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "url")
    val url: String,

    @Json(name = "nsfw")
    val nsfw: Boolean,

    @Json(name = "author")
    val author: String
)
