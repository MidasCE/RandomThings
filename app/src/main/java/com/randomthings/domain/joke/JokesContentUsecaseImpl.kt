package com.randomthings.domain.joke

import com.randomthings.data.repository.DadJokesRepository
import com.randomthings.domain.entity.Joke
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JokesContentUsecaseImpl(
    private val dadJokesRepository: DadJokesRepository
) : JokesContentUsecase {

    override suspend fun searchJokes(page: Int, limit: Int, searchTerm: String): Flow<List<Joke>> {
        return dadJokesRepository.searchJokes(page, limit, searchTerm)
            .map { jokeEntity ->
                jokeEntity.results.map {
                    Joke(
                        id = it.id,
                        joke = it.joke
                    )
                }
            }
    }

}
