package com.randomthings.presentation.random

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.randomthings.R
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.common.list.EndlessLazyColumn
import com.randomthings.presentation.loader.LoadingView
import com.randomthings.presentation.topbar.TopBarTitle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomScreen(modifier: Modifier = Modifier, viewModel: RandomThingViewModel) {
    val pullRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            viewModel.refreshData();
            isRefreshing = false
        }
    }

    if (viewModel.randomImages.isEmpty())
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
            EndlessLazyColumn(
                modifier = modifier
                    .testTag("RandomThingsColumn")
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                loadMore = { viewModel.fetchRandomContent() }
            ) {
                item(key = "TopBarTitle") {
                    TopBarTitle(title = stringResource(R.string.top_bar_title_home))
                }
                items(viewModel.randomImages.size) { randomImageIndex ->
                    RandomThingItem(
                        item = viewModel.randomImages[randomImageIndex] as ImageContent.RandomImageContent,
                        modifier = Modifier.fillMaxWidth(),
                        favouriteClick = { viewModel.toggleContentFavourite(it) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun RandomScreenPreview() {

    val packageName = LocalContext.current.packageName
    val localImageUri = "android.resource://$packageName/${R.drawable.placeholder}"
    // A list of dummy data
    val dummyContentList = listOf(
        ImageContent.RandomImageContent(
            id = "1",
            author = "Test Long Long Long Author",
            width = 512,
            height = 256,
            url = localImageUri,
            downloadUrl = localImageUri,
            favourite = false,
        ),
        ImageContent.RandomImageContent(
            id = "2",
            author = "Another Favourited Author",
            width = 512,
            height = 256,
            url = localImageUri,
            downloadUrl = localImageUri,
            favourite = true, // <-- Show a favourited item
        ),
        ImageContent.RandomImageContent(
            id = "3",
            author = "The Third Author",
            width = 512,
            height = 256,
            url = localImageUri,
            downloadUrl = localImageUri,
            favourite = false,
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        items(dummyContentList) { content ->
            RandomThingItem(
                item = content,
                modifier = Modifier.fillMaxWidth(),
                favouriteClick = { }
            )
        }
    }
}
