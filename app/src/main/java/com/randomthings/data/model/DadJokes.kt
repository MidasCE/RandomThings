package com.randomthings.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DadJokes(
    @Json(name = "results")
    val results: List<DadJoke>

)

@JsonClass(generateAdapter = true)
data class DadJoke(

    @Json(name = "id")
    val id: String,

    @Json(name = "joke")
    val joke: String,

)