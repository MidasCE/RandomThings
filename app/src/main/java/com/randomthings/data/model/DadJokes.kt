package com.randomthings.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DadJokes(
    @Json(name = "results")
    val results: List<DadJoke>,

    @Json(name = "current_page")
    val currentPage: Int,

    @Json(name = "limit")
    val limit: Int,

    @Json(name = "next_page")
    val nextPage: Int,

    @Json(name = "previous_page")
    val previousPage: Int,

    @Json(name = "total_pages")
    val totalPages: Int,
)

@JsonClass(generateAdapter = true)
data class DadJoke(

    @Json(name = "id")
    val id: String,

    @Json(name = "joke")
    val joke: String,

)