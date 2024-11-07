package com.randomthings.presentation.jokes

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.randomthings.domain.entity.ImageContent
import com.randomthings.domain.entity.Joke
import com.randomthings.domain.joke.JokesContentUsecase
import com.randomthings.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesContentUsecase: JokesContentUsecase,
) : BaseViewModel() {

    private val _query = MutableStateFlow("")
    private val _jokesSearchResult = MutableStateFlow<List<Joke>>(emptyList())

    val jokesSearchResult = _jokesSearchResult.asStateFlow()
    val query = _query.asStateFlow()

    companion object {
        const val TAG = "JokesViewModel"
    }
    private var currentPage = 1

    private val queryFlow = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1,
        BufferOverflow.DROP_OLDEST
    )

    init {
        queryFlow.debounce(500)
            .distinctUntilChanged()
            .mapLatest {
                currentPage = 1
                if (it.isEmpty() || it.isBlank())
                {
                    return@mapLatest flowOf(emptyList<Joke>())
                }
                return@mapLatest jokesContentUsecase.searchJokes(1, 20, it)
            }
            .onEach {
                it.collect { result ->
                    _jokesSearchResult.value = result
                }
            }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun searchJokes(query: String) {
        _query.value = query
        queryFlow.tryEmit(query)
    }
}
