package com.randomthings.di.content

import com.randomthings.domain.entity.JokeContent
import com.randomthings.domain.joke.JokesContentUsecase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestJokesContentUsecase : JokesContentUsecase
{
    override suspend fun searchJokes(page: Int, limit: Int, searchTerm: String): Flow<JokeContent> {
        return flowOf(JokeContent(
            currentPage = 1,
            nextPage = 2,
            totalPages = 10,
            jokes = emptyList()
        ))
    }
}
