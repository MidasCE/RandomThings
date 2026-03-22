package com.randomthings.presentation.favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.randomthings.R
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.common.NetworkImage
import com.randomthings.presentation.meme.MemeItem
import com.randomthings.presentation.theme.CollectionBeigeBg
import com.randomthings.presentation.theme.CollectionDarkBrown
import com.randomthings.presentation.theme.CollectionTabSelected

@Composable
fun FavouriteScreen(modifier: Modifier = Modifier, viewModel: FavouriteViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.fetchFavouriteContents()
    }

    val photos = viewModel.favouriteContents
        .filterIsInstance<ImageContent.RandomImageContent>()
    val memes = viewModel.favouriteContents
        .filterIsInstance<ImageContent.MemeImageContent>()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CollectionBeigeBg)
            .statusBarsPadding(),
    ) {
        // Title
        Text(
            text = stringResource(R.string.top_bar_title_favourite),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = CollectionDarkBrown,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
        )

        // Tab row
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = CollectionBeigeBg,
            contentColor = CollectionTabSelected,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = CollectionTabSelected,
                )
            },
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        text = stringResource(R.string.favourites_tab_photos),
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 0) CollectionTabSelected else Color(0xFF999999),
                    )
                },
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        text = stringResource(R.string.favourites_tab_memes),
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTab == 1) CollectionTabSelected else Color(0xFF999999),
                    )
                },
            )
        }

        when (selectedTab) {
            0 -> {
                if (photos.isEmpty()) {
                    CollectionEmptyState(message = stringResource(R.string.favourites_empty_photos))
                } else {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalItemSpacing = 10.dp,
                    ) {
                        items(photos) { photo ->
                            CollectionPhotoItem(
                                photo = photo,
                                onToggleFavourite = { viewModel.toggleContentFavourite(it) },
                            )
                        }
                    }
                }
            }

            1 -> {
                if (memes.isEmpty()) {
                    CollectionEmptyState(message = stringResource(R.string.favourites_empty_memes))
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                    ) {
                        items(memes.size) { index ->
                            MemeItem(
                                item = memes[index],
                                modifier = Modifier.fillMaxWidth(),
                                favouriteClick = { viewModel.toggleContentFavourite(it) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CollectionPhotoItem(
    photo: ImageContent.RandomImageContent,
    onToggleFavourite: (ImageContent) -> Unit,
) {
    val aspectRatio = if (photo.width > 0 && photo.height > 0) {
        photo.width.toFloat() / photo.height.toFloat()
    } else {
        1f
    }

    Box(modifier = Modifier.alpha(if (photo.favourite) 1f else 0.45f)) {
        NetworkImage(
            url = photo.downloadUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
                .clip(RoundedCornerShape(14.dp)),
            contentScale = ContentScale.Crop,
        )
        IconButton(
            onClick = { onToggleFavourite(photo) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(40.dp),
        ) {
            Icon(
                imageVector = if (photo.favourite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                contentDescription = null,
                tint = if (photo.favourite) CollectionDarkBrown else Color(0xFFAAAAAA),
                modifier = Modifier.size(22.dp),
            )
        }
    }
}

@Composable
private fun CollectionEmptyState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF888888),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(32.dp),
        )
    }
}
