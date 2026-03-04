package com.randomthings.presentation.random

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.randomthings.R
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.common.NetworkImage


@Composable
fun RandomThingImage(
    modifier: Modifier = Modifier,
    item: ImageContent.RandomImageContent,
) {
    Surface(
        shape = RoundedCornerShape(8),
        modifier = modifier
    ) {
        NetworkImage(
            url = item.downloadUrl,
            contentDescription = null,
            modifier = Modifier,
        )
    }
}

@Composable
fun RandomThingDetail(
    modifier: Modifier = Modifier,
    item: ImageContent.RandomImageContent,
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
    item: ImageContent.RandomImageContent,
    favouriteClick: (ImageContent.RandomImageContent) -> Unit,
    maxWidth : Dp = 600.dp,
) {
    ConstraintLayout (
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),

    ) {
        val (image, titleView, favouriteIcon) = createRefs()
        createHorizontalChain(favouriteIcon, titleView, chainStyle = ChainStyle.Packed)
        RandomThingImage(
            item = item,
            modifier = Modifier
                .widthIn(max = maxWidth)
                .constrainAs(image) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    height = Dimension.ratio("16:9")
                }
                .clip(RoundedCornerShape(12.dp))
        )
        RandomThingDetail(
            item = item,
            modifier = Modifier.constrainAs(titleView) {
                // Keep it vertically aligned with the icon
                top.linkTo(favouriteIcon.top)
                bottom.linkTo(favouriteIcon.bottom)
                // The chain handles the horizontal centering automatically
                // Add the 8dp spacing between the icon and text:
                start.linkTo(favouriteIcon.end, margin = 8.dp)
                width = Dimension.wrapContent
            }
        )

        IconButton(
            onClick = { favouriteClick(item) },

            modifier = Modifier.constrainAs(favouriteIcon) {
                // Link to the bottom of the image, not the titleView
                top.linkTo(image.bottom, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            }.size(24.dp)
        ) {
            Icon(
                imageVector = if (item.favourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = if (item.favourite) Color.Red else MaterialTheme.colorScheme.onSurface,
            )

        }
    }
}

@Preview(name = "Item - Default", showBackground = true)
@Composable
private fun RandomThingItemDefaultPreview() {
    val context = LocalContext.current
    val localImageUri = "android.resource://${context.packageName}/${R.drawable.placeholder}"

    val content = ImageContent.RandomImageContent(
        id = "1",
        author = "Author : This is Sheryl",
        width = 512,
        height = 256,
        url = localImageUri,
        downloadUrl = localImageUri,
        favourite = false,
    )

    Surface {
        RandomThingItem(
            modifier = Modifier,
            item = content,
            favouriteClick = {},
        )
    }
}

@Preview(name = "Item - Favourited", showBackground = true)
@Composable
private fun RandomThingItemFavouritedPreview() {
    val content = ImageContent.RandomImageContent(
        id = "1",
        author = "Author : This is Sheryl",
        width = 512,
        height = 256,
        url = "https://fastly.picsum.photos/id/176/",
        downloadUrl = "https://fastly.picsum.photos/id/176/",
        favourite = true, // <-- Set to true
    )

    Surface {
        RandomThingItem(
            modifier = Modifier,
            item = content,
            favouriteClick = {},
        )
    }
}
