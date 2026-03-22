package com.randomthings.domain.joke

import com.randomthings.TestRule
import com.randomthings.data.model.DadJoke
import com.randomthings.data.model.DadJokes
import com.randomthings.data.repository.DadJokesRepository
import com.randomthings.domain.entity.Joke
import com.randomthings.domain.entity.JokeContent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class JokesContentUsecaseImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = TestRule()

    private val dadJokesRepository: DadJokesRepository = mockk()

    private lateinit var usecase: JokesContentUsecase

    @Before
    fun setUp() {
        usecase = JokesContentUsecaseImpl(dadJokesRepository)
    }

    @Test
    fun `searchJokes should map DadJokes response to JokeContent domain entity`(): Unit = runTest {
        val dadJoke1 = DadJoke(id = "abc", joke = "Why did the chicken cross the road?")
        val dadJoke2 = DadJoke(id = "def", joke = "To get to the other side!")
        val dadJokesResponse = DadJokes(
            results = listOf(dadJoke1, dadJoke2),
            currentPage = 1,
            limit = 20,
            nextPage = 2,
            previousPage = 0,
            totalPages = 5,
        )

        val expectedResult = JokeContent(
            currentPage = 1,
            nextPage = 2,
            totalPages = 5,
            jokes = listOf(
                Joke(id = "abc", joke = "Why did the chicken cross the road?"),
                Joke(id = "def", joke = "To get to the other side!"),
            ),
        )

        coEvery { dadJokesRepository.searchJokes(any(), any(), any()) } returns flowOf(dadJokesResponse)

        val result = usecase.searchJokes(page = 1, limit = 20, searchTerm = "chicken")

        coVerify { dadJokesRepository.searchJokes(1, 20, "chicken") }
        assertEquals(expectedResult, result.single())
    }

    @Test
    fun `searchJokes should map pagination fields correctly`(): Unit = runTest {
        val dadJokesResponse = DadJokes(
            results = listOf(DadJoke(id = "xyz", joke = "A joke")),
            currentPage = 3,
            limit = 10,
            nextPage = 4,
            previousPage = 2,
            totalPages = 10,
        )

        coEvery { dadJokesRepository.searchJokes(any(), any(), any()) } returns flowOf(dadJokesResponse)

        val result = usecase.searchJokes(page = 3, limit = 10, searchTerm = "test")

        val jokeContent = result.single()
        assertEquals(3, jokeContent.currentPage)
        assertEquals(4, jokeContent.nextPage)
        assertEquals(10, jokeContent.totalPages)
    }

    @Test
    fun `searchJokes should return empty jokes list when results are empty`(): Unit = runTest {
        val dadJokesResponse = DadJokes(
            results = emptyList(),
            currentPage = 1,
            limit = 20,
            nextPage = 1,
            previousPage = 0,
            totalPages = 0,
        )

        val expectedResult = JokeContent(
            currentPage = 1,
            nextPage = 1,
            totalPages = 0,
            jokes = emptyList(),
        )

        coEvery { dadJokesRepository.searchJokes(any(), any(), any()) } returns flowOf(dadJokesResponse)

        val result = usecase.searchJokes(page = 1, limit = 20, searchTerm = "noresults")

        assertEquals(expectedResult, result.single())
    }

    @Test
    fun `searchJokes should pass correct parameters to repository`(): Unit = runTest {
        val dadJokesResponse = DadJokes(
            results = emptyList(),
            currentPage = 2,
            limit = 5,
            nextPage = 3,
            previousPage = 1,
            totalPages = 8,
        )

        coEvery { dadJokesRepository.searchJokes(any(), any(), any()) } returns flowOf(dadJokesResponse)

        usecase.searchJokes(page = 2, limit = 5, searchTerm = "pizza").single()

        coVerify(exactly = 1) { dadJokesRepository.searchJokes(2, 5, "pizza") }
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
    }
}
