package com.randomthings

import com.randomthings.domain.joke.JokesContentUsecase
import com.randomthings.presentation.jokes.JokesViewModel
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
    }
}