package com.randomthings.presentation.random

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.common.NetworkRandomImage


@Composable
fun RandomThingImage(
    modifier: Modifier = Modifier,
    item: ImageContent,
) {
    Surface(
        shape = RoundedCornerShape(8),
        modifier = modifier
    ) {
        NetworkRandomImage(
            url = item.downloadUrl,
            contentDescription = null,
            modifier = Modifier,
        )
    }
}

@Composable
fun RandomThingDetail(
    modifier: Modifier = Modifier,
    item: ImageContent,
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        Text(text = item.author,
            style = MaterialTheme.typography.titleSmall,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
        )
    }
}

@Composable
fun RandomThingItem(
    modifier: Modifier = Modifier,
    item: ImageContent,
    imageHeight : Dp = 240.dp,
) {
    ConstraintLayout (
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        val (image, titleView) = createRefs()
        RandomThingImage(
            item = item,
            modifier = Modifier.constrainAs(image) {
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.preferredWrapContent
                height = Dimension.value(imageHeight);
            }
        )
        RandomThingDetail(
            item = item,
            modifier = Modifier.constrainAs(titleView) {
                top.linkTo(image.bottom, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
                start.linkTo(image.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.wrapContent
            }
        )
    }
}

@Preview()
@Composable
private fun RandomThingItemPreview() {
    val content = ImageContent(
        id = "1",
        author = "Author : This is Sheryl",
        width = 512,
        height = 256,
        downloadUrl = "https://fastly.picsum.photos/id/176/"
    )
    RandomThingItem(
        modifier = Modifier,
        item = content
    )
}
