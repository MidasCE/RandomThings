package com.randomthings.presentation.jokes

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.randomthings.domain.entity.Joke
import com.randomthings.domain.joke.JokesContentUsecase
import com.randomthings.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesContentUsecase: JokesContentUsecase,
) : BaseViewModel() {

    private val _query = MutableStateFlow("")
    private val _jokesSearchResult = MutableStateFlow<List<Joke>>(listOf())
    private val _jokeOfTheDay = MutableStateFlow<Joke?>(null)
    private val _isLoadingJokeOfTheDay = MutableStateFlow(false)

    val jokesSearchResult = _jokesSearchResult.asStateFlow()
    val query = _query.asStateFlow()
    val jokeOfTheDay = _jokeOfTheDay.asStateFlow()
    val isLoadingJokeOfTheDay = _isLoadingJokeOfTheDay.asStateFlow()

    private var _availablePages: Int = 0
    private var _currentPage = 1
    private var _nextPage = 1

    private val randomTerms = listOf(
        "why", "what", "how", "cat", "dog", "time", "love",
        "work", "computer", "pizza", "school", "music", "water",
    )

    companion object {
        const val TAG = "JokesViewModel"
    }

    private val queryFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        BufferOverflow.DROP_OLDEST,
    )

    init {
        queryFlow.debounce(500)
            .distinctUntilChanged()
            .mapLatest {
                _currentPage = 1
                _nextPage = 1
                if (it.isEmpty() || it.isBlank()) {
                    return@mapLatest emptyFlow()
                }
                return@mapLatest jokesContentUsecase.searchJokes(_currentPage, 20, it)
            }
            .onEach {
                it.collect { jokeContent ->
                    _currentPage = jokeContent.currentPage
                    _nextPage = jokeContent.nextPage
                    _availablePages = jokeContent.totalPages
                    _jokesSearchResult.value = jokeContent.jokes
                }
            }
            .catch { e ->
                Log.e("ERROR", e.message.orEmpty())
            }
            .launchIn(viewModelScope)

        randomizeJoke()
    }

    fun searchJokes(query: String) {
        _query.value = query
        queryFlow.tryEmit(query)
    }

    fun fetchNextPage() {
        if (_nextPage > _availablePages) {
            return
        }

        launchNetwork(
            error = { e ->
                Log.e("ERROR", e.message.orEmpty())
            },
        ) {
            jokesContentUsecase.searchJokes(_nextPage, 20, _query.value)
                .collect {
                    _currentPage = it.currentPage
                    _nextPage = it.nextPage
                    _availablePages = it.totalPages
                    val concatList = _jokesSearchResult.value.toMutableList()
                    concatList.addAll(it.jokes)
                    _jokesSearchResult.value = concatList.distinctBy { joke ->
                        joke.id
                    }.toList()
                }
        }
    }

    fun randomizeJoke() {
        _isLoadingJokeOfTheDay.value = true
        launchNetwork(
            error = { e ->
                Log.e("ERROR", e.message.orEmpty())
                _isLoadingJokeOfTheDay.value = false
            },
        ) {
            val term = randomTerms.random()
            jokesContentUsecase.searchJokes(1, 1, term).collect { jokeContent ->
                _jokeOfTheDay.value = jokeContent.jokes.firstOrNull()
                _isLoadingJokeOfTheDay.value = false
            }
        }
    }
}
