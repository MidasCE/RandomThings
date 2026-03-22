package com.randomthings.presentation.jokes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.randomthings.R
import com.randomthings.presentation.common.SearchBar
import com.randomthings.presentation.common.list.EndlessLazyColumn
import com.randomthings.presentation.theme.JokeSkyBlueDark
import com.randomthings.presentation.theme.JokeSkyBlueLight
import com.randomthings.presentation.theme.JokeSkyBlueMid
import com.randomthings.presentation.theme.JokeYellowCard
import com.randomthings.presentation.theme.JokeYellowCardText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokesScreen(modifier: Modifier = Modifier, viewModel: JokesViewModel) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val results by viewModel.jokesSearchResult.collectAsStateWithLifecycle()
    val jokeOfTheDay by viewModel.jokeOfTheDay.collectAsStateWithLifecycle()
    val isLoadingJotd by viewModel.isLoadingJokeOfTheDay.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(JokeSkyBlueLight, JokeSkyBlueMid, JokeSkyBlueDark),
                ),
            ),
    ) {
        EndlessLazyColumn(
            modifier = Modifier.fillMaxSize().statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            loadMore = { viewModel.fetchNextPage() },
        ) {
            item(key = "title") {
                Text(
                    text = stringResource(R.string.top_bar_title_joke),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
                )
            }

            item(key = "searchbar") {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    query = query,
                    placeHolderString = stringResource(R.string.search_jokes_placeholder),
                    onQueryChange = { viewModel.searchJokes(it) },
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    content = {},
                )
            }

            item(key = "jotd") {
                JokeOfTheDayCard(
                    joke = jokeOfTheDay?.joke,
                    isLoading = isLoadingJotd,
                )
            }

            item(key = "randomize") {
                Button(
                    onClick = { viewModel.randomizeJoke() },
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF333333),
                    ),
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .height(52.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.randomize_joke),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }

            if (results.isNotEmpty()) {
                item(key = "resultsHeader") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.15f))
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.search_results),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                    }
                }
                items(results.size) { index ->
                    JokeItem(
                        text = results[index].joke,
                        index = index,
                    )
                }
            } else if (query.isNotBlank()) {
                item(key = "empty") {
                    Text(
                        text = stringResource(R.string.no_jokes_found),
                        color = Color.White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun JokeOfTheDayCard(
    joke: String?,
    isLoading: Boolean,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = JokeYellowCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.joke_of_the_day),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = JokeYellowCardText,
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = JokeYellowCardText,
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text(
                        text = joke ?: "—",
                        style = MaterialTheme.typography.bodyLarge,
                        color = JokeYellowCardText,
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.EmojiEmotions,
                contentDescription = null,
                tint = Color(0xFFFF8F00),
                modifier = Modifier
                    .size(56.dp)
                    .padding(start = 8.dp),
            )
        }
    }
}
