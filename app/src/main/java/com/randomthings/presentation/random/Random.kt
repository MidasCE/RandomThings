package com.randomthings.presentation.random

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.randomthings.presentation.loader.LoadingView

@Composable
fun RandomScreen(modifier: Modifier = Modifier, viewModel: RandomThingViewModel) {
    if (viewModel.randomImages.isEmpty())
    {
        LoadingView(loading = true)
    } else
    {
        Column (
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RandomThingItem(
                item = viewModel.randomImages[0],
                modifier = Modifier.fillMaxWidth().height(240.dp),
            )
        }
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
        Text("DESCRIPTION")
    }
}
