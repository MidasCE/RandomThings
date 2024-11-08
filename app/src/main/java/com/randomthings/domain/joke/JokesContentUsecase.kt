package com.randomthings.domain.joke

import com.randomthings.domain.entity.JokeContent
import kotlinx.coroutines.flow.Flow

interface JokesContentUsecase {

    suspend fun searchJokes(page: Int, limit : Int, searchTerm : String) : Flow<JokeContent>
}
