package com.randomthings.presentation.loader

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.randomthings.R

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    loading: Boolean
) {
    if (!loading) return
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieLoader(
            rawRes = R.raw.loading,
            forever = true
        )
    }
}


@Composable
fun LottieLoader(
    modifier: Modifier = Modifier,
    @RawRes rawRes: Int,
    forever: Boolean = false,
    onComplete: () -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawRes))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = if (forever) LottieConstants.IterateForever else 1
    )
    if (!forever && progress == 1.0f) onComplete()

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
    )
}