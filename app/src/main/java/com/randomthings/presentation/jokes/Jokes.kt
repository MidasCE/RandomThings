package com.randomthings.presentation.jokes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.randomthings.domain.entity.Joke
import com.randomthings.presentation.common.SearchBar


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
        JokeSearchResults (
            modifier = Modifier.padding(it),
            results = results,
        )
    }
}

@Composable
private fun JokeSearchResults(
    modifier: Modifier = Modifier,
    results: List<Joke>,
) {
    LazyColumn(modifier = modifier) {
        items(results.size) { index ->
            JokeItem(text = results[index].joke)
        }
    }
}


@Preview()
@Composable
private fun JokesScreenPreview() {

    val results: List<Joke> = listOf(Joke("jokeId1", "test joke 1"))

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
        JokeSearchResults (
            modifier = Modifier.padding(it),
            results = results,
        )
    }
}
