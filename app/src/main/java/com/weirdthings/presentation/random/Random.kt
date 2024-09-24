package com.weirdthings.presentation.random

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.weirdthings.BuildConfig
import com.weirdthings.presentation.common.NetworkRandomImage
import com.weirdthings.presentation.preview.ImageUrlPreviewParameterProvider
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun RandomScreen(
    modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NetworkRandomImage(
            url = BuildConfig.BASE_PICS_SUM_URL + "512",
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(240.dp),
        )
        Text("DESCRIPTION")
    }
}

@Preview()
@Composable
private fun RandomScreenPreview() {
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NetworkRandomImage(
            url = BuildConfig.BASE_PICS_SUM_URL + "512",
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(240.dp),
        )
        Text("DESCRIPTION")
    }
}
