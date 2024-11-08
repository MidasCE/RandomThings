package com.randomthings.domain.joke

import com.randomthings.data.repository.DadJokesRepository
import com.randomthings.domain.entity.Joke
import com.randomthings.domain.entity.JokeContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JokesContentUsecaseImpl(
    private val dadJokesRepository: DadJokesRepository
) : JokesContentUsecase {

    override suspend fun searchJokes(page: Int, limit: Int, searchTerm: String): Flow<JokeContent> {
        return dadJokesRepository.searchJokes(page, limit, searchTerm)
            .map { jokeEntity ->
                val jokeList = jokeEntity.results.map { jokeResult ->
                    Joke(
                        id = jokeResult.id,
                        joke = jokeResult.joke
                    )
                }
                JokeContent(
                    currentPage = jokeEntity.currentPage,
                    nextPage = jokeEntity.nextPage,
                    totalPages = jokeEntity.totalPages,
                    jokes = jokeList
                )
            }
    }

}
