package com.randomthings.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ImageContent : Parcelable{
    @Parcelize
    data class RandomImageContent(
        val id: String,
        val author: String,
        val width: Int,
        val height: Int,
        val downloadUrl: String
    ) : ImageContent()

    @Parcelize
    data class MemeImageContent(
        val author: String,
        val url: String
    ) : ImageContent()
}
