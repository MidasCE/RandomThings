package com.weirdthings.presentation.random

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weirdthings.data.entity.ImageInfo
import com.weirdthings.presentation.common.NetworkRandomImage


@Composable
fun RandomThingItem(
    modifier: Modifier = Modifier,
    item: ImageInfo,
) {
    NetworkRandomImage(
        url = item.url,
        contentDescription = null,
        modifier = Modifier,
    )
    Text(item.author)
}
