package com.randomthings.presentation.random

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.randomthings.R
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.common.list.EndlessLazyColumn
import com.randomthings.presentation.loader.LoadingView
import com.randomthings.presentation.theme.HomeGreetingOrange
import com.randomthings.presentation.theme.HomePeachBackground
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomScreen(modifier: Modifier = Modifier, viewModel: RandomThingViewModel) {
    val pullRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val greeting = remember {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        when {
            hour < 12 -> R.string.greeting_morning
            hour < 17 -> R.string.greeting_afternoon
            else -> R.string.greeting_evening
        }
    }

    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            viewModel.refreshData()
            isRefreshing = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HomePeachBackground),
    ) {
        if (viewModel.randomImages.isEmpty()) {
            LoadingView(
                modifier = Modifier.fillMaxSize(),
                loading = true,
            )
        } else {
            PullToRefreshBox(
                state = pullRefreshState,
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
            ) {
                EndlessLazyColumn(
                    modifier = Modifier
                        .testTag("RandomThingsColumn")
                        .fillMaxSize()
                        .statusBarsPadding(),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    loadMore = { viewModel.fetchRandomContent() },
                ) {
                    item(key = "header") {
                        DiscoveryHeader(
                            greetingRes = greeting,
                        )
                    }
                    items(viewModel.randomImages.size) { index ->
                        val item = viewModel.randomImages[index] as ImageContent.RandomImageContent
                        if (index == 0) {
                            FeaturedImageCard(
                                item = item,
                                modifier = Modifier.fillMaxWidth(),
                                favouriteClick = { viewModel.toggleContentFavourite(it) },
                            )
                        } else {
                            DiscoveryImageCard(
                                item = item,
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
private fun DiscoveryHeader(
    greetingRes: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Text(
            text = stringResource(R.string.top_bar_title_home),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A1A),
        )
        Text(
            text = stringResource(greetingRes),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = HomeGreetingOrange,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}
