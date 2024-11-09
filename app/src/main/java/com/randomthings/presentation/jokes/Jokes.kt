package com.randomthings.presentation.jokes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.randomthings.domain.entity.Joke
import com.randomthings.presentation.common.SearchBar
import com.randomthings.presentation.common.list.EndlessLazyColumn


@Composable
fun JokesScreen(modifier: Modifier = Modifier, viewModel: JokesViewModel) {

    val query = viewModel.query.collectAsStateWithLifecycle().value
    val results = viewModel.jokesSearchResult.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = modifier,
        topBar = {
            SearchBar(
                modifier = Modifier,
                query = query,
                onQueryChange = { viewModel.searchJokes(it) },
                onSearch = { },
                expanded = false,
                onExpandedChange = { },
                content = {}
            )
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) {
        EndlessLazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            loadMore = { viewModel.fetchNextPage() }
        ) {
            items(results.size) { index ->
                JokeItem(text = results[index].joke)
            }
        }
    }
}


@Preview()
@Composable
private fun JokesScreenPreview() {

    val results: List<Joke> = listOf(
        Joke("jokeId1", "test joke 1"),
        Joke("jokeId2", "test joke 2")
    )

    Scaffold(
        modifier = Modifier,
        topBar = {
            SearchBar(
                modifier = Modifier,
                query = "",
                onQueryChange = { },
                onSearch = { },
                expanded = false,
                onExpandedChange = { },
                content = {}
            )
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) {
        EndlessLazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            loadMore = {  }
        ) {
            items(results.size) { index ->
                JokeItem(text = results[index].joke)
            }
        }
    }
}
