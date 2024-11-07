package com.randomthings.presentation.jokes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.randomthings.presentation.common.SearchBar


@Composable
fun JokesScreen(modifier: Modifier = Modifier, viewModel: JokesViewModel) {

    val query = viewModel.query.collectAsStateWithLifecycle().value
    Box(modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)) {
        SearchBar(
            modifier = Modifier,
            query = query,
            onQueryChange = { viewModel.searchJokes(it) },
            onSearch = { },
            expanded = false,
            onExpandedChange = { },
            content = {}
        )
    }
}


@Preview()
@Composable
private fun JokesScreenPreview() {

    Box(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
        SearchBar(
            modifier = Modifier,
            query = "",
            onQueryChange = { },
            onSearch = { },
            expanded = false,
            onExpandedChange = { },
            content = {}
        )
    }
}
