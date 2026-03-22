package com.randomthings.presentation.meme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.randomthings.R
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.loader.LoadingView
import com.randomthings.presentation.theme.MemeDarkBackground
import com.randomthings.presentation.theme.MemeDarkCard
import com.randomthings.presentation.theme.MemeDarkCardBack
import com.randomthings.presentation.theme.MemeCardBackground
import com.randomthings.presentation.theme.MemeLikeRed
import com.randomthings.presentation.theme.MemeRefreshPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemeScreen(modifier: Modifier = Modifier, viewModel: MemeViewModel) {
    val pullRefreshState = rememberPullToRefreshState()
    val meme by viewModel.randomMeme.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val currentMeme = meme

    PullToRefreshBox(
        state = pullRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = viewModel::fetchRandomMeme,
        modifier = modifier
            .fillMaxSize()
            .background(MemeDarkBackground),
    ) {
        if (currentMeme == null) {
            LoadingView(
                modifier = Modifier.fillMaxSize(),
                loading = true,
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = stringResource(R.string.top_bar_title_meme),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    MemeCardStack(
                        meme = currentMeme,
                        onFavouriteClick = { viewModel.toggleContentFavourite() },
                    )
                }

                // Purple FAB - refresh
                FloatingActionButton(
                    onClick = { viewModel.fetchRandomMeme() },
                    containerColor = MemeRefreshPurple,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(R.string.meme_refresh_content_description),
                        modifier = Modifier.size(28.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun MemeCardStack(
    meme: ImageContent.MemeImageContent,
    onFavouriteClick: () -> Unit,
) {
    var heartPressed by remember { mutableStateOf(false) }
    val heartScale by animateFloatAsState(
        targetValue = if (heartPressed) 1.3f else 1f,
        animationSpec = tween(durationMillis = 150),
        finishedListener = { heartPressed = false },
        label = "heartScale",
    )

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Back card (decorative)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    rotationZ = 5f
                    translationX = 24f
                    translationY = -16f
                    scaleX = 0.88f
                    scaleY = 0.88f
                },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MemeDarkCardBack),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Spacer(modifier = Modifier.height(280.dp))
        }

        // Middle card (decorative)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    rotationZ = 2.5f
                    translationX = 12f
                    translationY = -8f
                    scaleX = 0.94f
                    scaleY = 0.94f
                },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MemeDarkCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        ) {
            Spacer(modifier = Modifier.height(280.dp))
        }

        // Front card - actual meme content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MemeCardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            ) {
                MemeImage(
                    item = meme,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    shape = RoundedCornerShape(24.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Red heart FAB
            FloatingActionButton(
                onClick = {
                    heartPressed = true
                    onFavouriteClick()
                },
                containerColor = MemeLikeRed,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .size(60.dp)
                    .scale(heartScale),
            ) {
                Icon(
                    imageVector = if (meme.favourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.meme_favourite_content_description),
                    modifier = Modifier.size(30.dp),
                )
            }
        }
    }
}
