package com.weirdthings.presentation.random

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.weirdthings.data.entity.ImageInfo
import com.weirdthings.presentation.common.NetworkRandomImage


@Composable
fun RandomThingItem(
    modifier: Modifier = Modifier,
    item: ImageInfo,
) {
    NetworkRandomImage(
        url = item.downloadUrl,
        contentDescription = null,
        modifier = modifier,
    )
    Text(item.author)
}
