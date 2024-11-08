package com.randomthings.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JokeContent(
    val currentPage: Int,
    val nextPage: Int,
    val totalPages: Int,
    val jokes : List<Joke>
) : Parcelable


@Parcelize
data class Joke(
    val id: String,
    val joke: String,
) : Parcelable
