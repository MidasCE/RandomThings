package com.randomthings.presentation.random

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.randomthings.domain.entity.RandomImageContent
import com.randomthings.presentation.common.NetworkRandomImage


@Composable
fun RandomThingItem(
    modifier: Modifier = Modifier,
    item: RandomImageContent,
) {
    NetworkRandomImage(
        url = item.downloadUrl,
        contentDescription = null,
        modifier = modifier,
    )
    Text(item.author)
}
