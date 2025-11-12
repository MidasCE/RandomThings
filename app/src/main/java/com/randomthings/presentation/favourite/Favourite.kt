package com.randomthings.presentation.favourite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.randomthings.R
import com.randomthings.domain.entity.ImageContent
import com.randomthings.presentation.loader.LoadingView
import com.randomthings.presentation.meme.MemeItem
import com.randomthings.presentation.random.RandomThingItem
import com.randomthings.presentation.topbar.TopBarTitle

@Composable
fun FavouriteScreen(modifier: Modifier = Modifier, viewModel: FavouriteViewModel) {

    LaunchedEffect(Unit) {
        viewModel.fetchFavouriteContents()
    }

    if (viewModel.favouriteContents.isEmpty())
    {
        LoadingView(
            modifier = modifier.fillMaxSize(),
            loading = true)
    } else
    {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item(key = "TopBarTitle") {
                TopBarTitle(title = stringResource(R.string.top_bar_title_favourite))
            }
            items(viewModel.favouriteContents.size)  { index ->
                when (val content = viewModel.favouriteContents[index])
                {
                    is ImageContent.RandomImageContent ->
                    {
                        RandomThingItem(
                            item = content,
                            modifier = Modifier.fillMaxWidth(),
                            favouriteClick = { viewModel.toggleContentFavourite(it) }
                        )
                    }

                    is ImageContent.MemeImageContent ->
                        MemeItem (
                            item = content,
                            modifier = Modifier.fillMaxWidth(),
                            favouriteClick = { viewModel.toggleContentFavourite(it) }
                        )
                }
            }
        }
    }
}
