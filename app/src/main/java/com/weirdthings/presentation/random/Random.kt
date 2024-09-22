package com.weirdthings.presentation.random

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.weirdthings.BuildConfig
import com.weirdthings.presentation.common.NetworkRandomImage

@Composable
fun RandomScreen(
    modifier: Modifier = Modifier) {
    NetworkRandomImage(
        url = BuildConfig.BASE_PICS_SUM_URL + "512",
        contentDescription = null,
        modifier = modifier.fillMaxWidth(),
    )
}
