package com.randomthings.presentation.favourite

import android.util.Log
import com.randomthings.TestRule
import com.randomthings.domain.content.ImageContentUseCase
import com.randomthings.domain.entity.ImageContent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class FavouriteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = TestRule()

    private val imageContentUseCase: ImageContentUseCase = mockk()

    private lateinit var viewModel: FavouriteViewModel

    @Before
    fun setUp() {
        viewModel = FavouriteViewModel(
            imageContentUseCase,
        )
    }

    @Test
    fun `should call getAllFavouriteContents from imageContentUsecase when fetchFavouriteContents`(): Unit = runTest {
        val returnContent = ImageContent.RandomImageContent("id", "author", 10, 10, "url", "downloadUrl", true)

        coEvery { imageContentUseCase.getAllFavouriteContents() } returns flow { emit(listOf(returnContent)) }

        // Act
        viewModel.fetchFavouriteContents()

        // Assert
        coVerify() { imageContentUseCase.getAllFavouriteContents() }
        assertEquals(listOf(returnContent), viewModel.favouriteContents)
    }

    @Test
    fun `should do nothing if getAllFavouriteContents return exception`(): Unit = runTest {
        val exception = Exception("Error occurred")
        coEvery { imageContentUseCase.getAllFavouriteContents() } throws exception

        // Act
        viewModel.fetchFavouriteContents()

        // Assert
        coVerify { Log.e(any(), exception.message.orEmpty()) }
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
    }

}