package com.randomthings.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ImageContent : Parcelable {
    abstract val favourite: Boolean

    @Parcelize
    data class RandomImageContent(
        val id: String,
        val author: String,
        val width: Int,
        val height: Int,
        val downloadUrl: String,
        override val favourite: Boolean,
    ) : ImageContent()

    @Parcelize
    data class MemeImageContent(
        val author: String,
        val url: String,
        override val favourite: Boolean,
    ) : ImageContent()

}
