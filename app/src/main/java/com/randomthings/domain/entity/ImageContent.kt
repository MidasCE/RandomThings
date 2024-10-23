package com.randomthings.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ImageContent : Parcelable {
    abstract val favourite: Boolean
    abstract val url: String

    @Parcelize
    data class RandomImageContent(
        val id: String,
        val author: String,
        val width: Int,
        val height: Int,
        override val url: String,
        val downloadUrl: String,
        override val favourite: Boolean,
    ) : ImageContent()

    @Parcelize
    data class MemeImageContent(
        val author: String,
        override val url: String,
        override val favourite: Boolean,
    ) : ImageContent()

}
