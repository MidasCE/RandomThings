package com.randomthings.presentation.meme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.randomthings.R
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.loader.LoadingView
import com.randomthings.presentation.topbar.TopBarTitle
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemeScreen(modifier: Modifier = Modifier, viewModel: MemeViewModel) {
    val pullRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            viewModel.fetchRandomMeme();
            isRefreshing = false
        }
    }

    if (viewModel.randomMemes.isEmpty())
    {
        LoadingView(
            modifier = modifier.fillMaxSize(),
            loading = true)
    } else
    {
        PullToRefreshBox(
            state = pullRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item(key = "TopBarTitle") {
                    TopBarTitle(title = stringResource(R.string.top_bar_title_meme))
                }
                items(viewModel.randomMemes.size) {
                    MemeItem(
                        item = viewModel.randomMemes[it] as ImageContent.MemeImageContent,
                        modifier = Modifier.fillMaxWidth(),
                        favouriteClick = { viewModel.toggleContentFavourite(it) }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
private fun MemeScreenPreview() {
    val pullRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    val onRefresh: () -> Unit = {
        isRefreshing = true
    }

    val content = ImageContent.MemeImageContent(
        postLink = "https://fastly.picsum.photos/id/176/",
        title = "title",
        nsfw = false,
        author = "Test Long Long Long Author",
        url = "https://fastly.picsum.photos/id/176/",
        favourite = false,
    )

    PullToRefreshBox(
        state = pullRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(1) {
                MemeItem(
                    item = content,
                    modifier = Modifier.fillMaxWidth(),
                    favouriteClick = { },
                )
            }
        }
    }
}
