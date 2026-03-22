package com.randomthings.presentation.random

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.randomthings.R
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.common.NetworkImage
import com.randomthings.presentation.theme.HomeFeaturedGradientEnd
import com.randomthings.presentation.theme.HomeFeaturedGradientStart

@Composable
fun FeaturedImageCard(
    modifier: Modifier = Modifier,
    item: ImageContent.RandomImageContent,
    favouriteClick: (ImageContent.RandomImageContent) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Box {
            NetworkImage(
                url = item.downloadUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop,
            )

            // "New!" badge
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart),
                shape = RoundedCornerShape(6.dp),
                color = Color.White,
            ) {
                Text(
                    text = stringResource(R.string.badge_new),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }

            // Bottom gradient overlay with author info
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(HomeFeaturedGradientStart, HomeFeaturedGradientEnd)
                        )
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "By @${item.author}",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                    )
                    IconButton(onClick = { favouriteClick(item) }) {
                        Icon(
                            imageVector = if (item.favourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = if (item.favourite) Color(0xFFFF4081) else Color.White,
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DiscoveryImageCard(
    modifier: Modifier = Modifier,
    item: ImageContent.RandomImageContent,
    favouriteClick: (ImageContent.RandomImageContent) -> Unit,
    maxWidth: androidx.compose.ui.unit.Dp = 600.dp,
) {
    Card(
        modifier = modifier
            .widthIn(max = maxWidth)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column {
            NetworkImage(
                url = item.downloadUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "By @${item.author}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF555555),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                )
                IconButton(
                    onClick = { favouriteClick(item) },
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        imageVector = if (item.favourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (item.favourite) Color(0xFFFF4081) else Color(0xFF999999),
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }
    }
}

@Preview(name = "Featured Card", showBackground = true)
@Composable
private fun FeaturedImageCardPreview() {
    val context = LocalContext.current
    val localImageUri = "android.resource://${context.packageName}/${R.drawable.placeholder}"
    FeaturedImageCard(
        item = ImageContent.RandomImageContent(
            id = "1",
            author = "SarahHolmes",
            width = 1600,
            height = 900,
            url = localImageUri,
            downloadUrl = localImageUri,
            favourite = false,
        ),
        favouriteClick = {},
    )
}

@Preview(name = "Discovery Card", showBackground = true)
@Composable
private fun DiscoveryImageCardPreview() {
    val context = LocalContext.current
    val localImageUri = "android.resource://${context.packageName}/${R.drawable.placeholder}"
    DiscoveryImageCard(
        item = ImageContent.RandomImageContent(
            id = "2",
            author = "ChrisBrignola",
            width = 1600,
            height = 900,
            url = localImageUri,
            downloadUrl = localImageUri,
            favourite = true,
        ),
        favouriteClick = {},
    )
}
