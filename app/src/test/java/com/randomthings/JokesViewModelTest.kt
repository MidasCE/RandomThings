package com.randomthings

import android.util.Log
import com.randomthings.domain.entity.JokeContent
import com.randomthings.domain.joke.JokesContentUsecase
import com.randomthings.presentation.jokes.JokesViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

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
        viewModel = JokesViewModel(
            jokesContentUsecase,
        )
    }

    @Test
    fun `should do nothing if query is empty`(): Unit = runTest {

        // Act
        viewModel.searchJokes("")

        // Assert
        coVerify(inverse = true) { jokesContentUsecase.searchJokes(any(), any(), any()) }
    }

    @Test
    fun `should do nothing if query is blank`(): Unit = runTest {

        // Act
        viewModel.searchJokes("   ")

        // Assert
        coVerify(inverse = true) { jokesContentUsecase.searchJokes(any(), any(), any()) }
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

        val returnContent = JokeContent(1, 2, 10, emptyList())

        coEvery { jokesContentUsecase.searchJokes(any(), any(), any()) } returns flow { emit(returnContent) }

        // Act
        viewModel.searchJokes(searchTerm)
        advanceTimeBy(501)

        // Assert
        coVerify { jokesContentUsecase.searchJokes(any(), any(), any()) }
    }

    @Test
    fun `fetchNextPage shouldn't do anything if no available pages`(): Unit = runTest {
        // Act
        viewModel.fetchNextPage()

        // Assert
        coVerify(inverse = true) { jokesContentUsecase.searchJokes(any(), any(), any()) }
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