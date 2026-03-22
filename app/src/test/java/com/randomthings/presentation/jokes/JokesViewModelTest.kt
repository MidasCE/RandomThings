package com.randomthings.presentation.jokes

import android.util.Log
import com.randomthings.TestRule
import com.randomthings.domain.entity.Joke
import com.randomthings.domain.entity.JokeContent
import com.randomthings.domain.joke.JokesContentUsecase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.collections.firstOrNull

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class JokesViewModelTest {


    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = TestRule()

    private val jokesContentUsecase: JokesContentUsecase = mockk()

    private lateinit var viewModel: JokesViewModel

    @Before
    fun setUp() {
        val returnContent = JokeContent(1, 2, 10, listOf(Joke("1", "jokeString")))
        coEvery { jokesContentUsecase.searchJokes(any(), any(), any()) } returns flow { emit(returnContent) }

        viewModel = JokesViewModel(
            jokesContentUsecase,
        )
    }

    @Test
    fun `should instantiate should random joke with search on first page`(): Unit = runTest {
        // Assert
        coVerify(exactly = 1) { jokesContentUsecase.searchJokes(1, 1, any()) }

        assertEquals(Joke("1", "jokeString"), viewModel.jokeOfTheDay.firstOrNull())
        assertEquals(false, viewModel.isLoadingJokeOfTheDay.value)
    }

    @Test
    fun `should do nothing if query is empty`(): Unit = runTest {
        // Act
        viewModel.searchJokes("")

        // Assert
        coVerify(exactly = 1) { jokesContentUsecase.searchJokes(any(), any(), any()) }
    }

    @Test
    fun `should do nothing if query is blank`(): Unit = runTest {

        // Act
        viewModel.searchJokes("   ")

        // Assert
        coVerify(exactly = 1) { jokesContentUsecase.searchJokes(any(), any(), any()) }
    }

    @Test
    fun `should do nothing if searchJokes return exception`(): Unit = runTest {
        val searchTerm = "test"
        val exception = Exception("Error occurred")
        coEvery { jokesContentUsecase.searchJokes(any(), any(), any()) } throws exception

        // Act
        viewModel.searchJokes(searchTerm)
        advanceTimeBy(501)

        // Assert
        coVerify { Log.e(any(), exception.message.orEmpty()) }
    }

    @Test
    fun `should searchJokes if query is not empty and start with first page`(): Unit = runTest {
        val searchTerm = "test"
        // Act
        viewModel.searchJokes(searchTerm)
        advanceTimeBy(501)

        // Assert
        coVerify(exactly = 1) { jokesContentUsecase.searchJokes(1, 20, "test") }
    }

    @Test
    fun `fetchNextPage shouldn't do anything if no available pages`(): Unit = runTest {
        // Act
        viewModel.fetchNextPage()

        // Assert
        coVerify(exactly = 1) { jokesContentUsecase.searchJokes(any(), any(), any()) }
    }

    @Test
    fun `fetchNextPage should search next page if there is available pages`(): Unit = runTest {

        val searchTerm = "test"

        val returnContent = JokeContent(1, 2, 10, emptyList())

        coEvery { jokesContentUsecase.searchJokes(any(), any(), any()) } returns flow { emit(returnContent) }

        viewModel.searchJokes(searchTerm)
        advanceTimeBy(501)

        // Act
        viewModel.fetchNextPage()

        // Assert
        coVerify() { jokesContentUsecase.searchJokes(returnContent.nextPage, any(), any()) }
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
    }

}