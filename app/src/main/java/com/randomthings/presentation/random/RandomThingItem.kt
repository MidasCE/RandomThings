package com.randomthings.presentation.random

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.randomthings.domain.entity.RandomImageContent
import com.randomthings.presentation.common.NetworkRandomImage


@Composable
fun RandomThingImage(
    modifier: Modifier = Modifier,
    item: RandomImageContent,
) {
    Surface(
        shape = RoundedCornerShape(8),
        modifier = modifier
    ) {
        NetworkRandomImage(
            url = item.downloadUrl,
            contentDescription = null,
            modifier = modifier,
        )
    }
}

@Composable
fun RandomThingDetail(
    modifier: Modifier = Modifier,
    item: RandomImageContent,
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
    ) {
        Text(item.author)
    }
}

@Composable
fun RandomThingItem(
    modifier: Modifier = Modifier,
    item: RandomImageContent,
) {

    Column (
        modifier = Modifier
            .wrapContentSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        RandomThingImage(
            modifier = modifier,
            item = item,
        )
        RandomThingDetail(
            modifier = Modifier,
            item = item,
        )
    }
}

@Preview()
@Composable
private fun RandomThingItemPreview() {
    val content = RandomImageContent(
        id = "1",
        author = "Test Long Long Long Author",
        width = 512,
        height = 256,
        downloadUrl = "https://fastly.picsum.photos/id/176/"
    )
    RandomThingItem(
        modifier = Modifier,
        item = content
    )
}
