package com.randomthings.domain.joke

import com.randomthings.domain.entity.Joke
import kotlinx.coroutines.flow.Flow

interface JokesContentUsecase {

    suspend fun searchJokes(page: Int, limit : Int, searchTerm : String) : Flow<List<Joke>>
}
