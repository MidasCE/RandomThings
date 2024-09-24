package com.weirdthings.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider


class ImageUrlPreviewParameterProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf("https://fastly.picsum.photos/id/724/512/512.jpg")
}