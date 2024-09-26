package com.randomthings.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomImageContent(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val downloadUrl: String
) : Parcelable
