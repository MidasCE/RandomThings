package com.randomthings.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.randomthings.R

@Composable
fun NetworkRandomImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    @DrawableRes placeholder: Int = R.drawable.placeholder
) {
    val context = LocalContext.current
    val request = ImageRequest.Builder(context)
        .data(url)
        .allowHardware(true)
        .diskCachePolicy(CachePolicy.DISABLED)
        .memoryCachePolicy(CachePolicy.DISABLED)
        .build()

    AsyncImage(
        model = request,
        contentDescription = contentDescription,
        placeholder = painterResource(placeholder),
        modifier = modifier,
        contentScale = contentScale
    )
}
