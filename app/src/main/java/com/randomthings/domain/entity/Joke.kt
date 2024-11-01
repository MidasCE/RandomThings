package com.randomthings.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Joke(
    val id: String,
    val joke: String,
) : Parcelable
