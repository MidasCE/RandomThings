package com.randomthings.presentation.meme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.common.NetworkImage


@Composable
fun MemeImage(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    item: ImageContent.MemeImageContent,
    contentScale: ContentScale,
) {
    Surface(
        shape = shape,
        modifier = modifier
    ) {
        NetworkImage(
            url = item.url,
            contentDescription = null,
            modifier = Modifier,
            contentScale = contentScale,
        )
    }
}

@Composable
fun MemeDetail(
    modifier: Modifier = Modifier,
    item: ImageContent.MemeImageContent,
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
fun MemeItem(
    modifier: Modifier = Modifier,
    item: ImageContent.MemeImageContent,
) {
    ConstraintLayout (
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        val (image, titleView) = createRefs()
        MemeImage(
            item = item,
            modifier = Modifier.constrainAs(image) {
                top.linkTo(parent.top, 16.dp)
            }.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        MemeDetail(
            item = item,
            modifier = Modifier.constrainAs(titleView) {
                top.linkTo(image.bottom, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.wrapContent
            }
        )
    }
}

@Preview()
@Composable
private fun MemeItemPreview() {
    val content = ImageContent.MemeImageContent(
        author = "Author : This is Sheryl",
        url= "https://i.redd.it/0oc1h5gtuosd1.gif",
        favourite = false
    )
    MemeItem(
        modifier = Modifier,
        item = content
    )
}
