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

    // --- Test Data ---
    private val testImage1 =
        ImageContent.RandomImageContent("id1", "author1", 10, 10, "url1", "downloadUrl1", false)

    @Before
    fun setUp() {
        viewModel = FavouriteViewModel(
            imageContentUseCase,
        )

        coEvery { imageContentUseCase.favoriteContent(any()) } returns flow { emit(1L) }
        coEvery { imageContentUseCase.unFavoriteContent(any()) } returns flow { emit(1) }
    }

    @Test
    fun `should call getAllFavouriteContents from imageContentUsecase when fetchFavouriteContents`(): Unit = runTest {
        coEvery { imageContentUseCase.getAllFavouriteContents() } returns flow { emit(listOf(testImage1)) }

        // Act
        viewModel.fetchFavouriteContents()

        // Assert
        coVerify() { imageContentUseCase.getAllFavouriteContents() }
        assertEquals(listOf(testImage1), viewModel.favouriteContents)
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

    @Test
    fun `toggleContentFavourite should call imageContentUseCase-favoriteContent when item is not favourite`() =
        runTest {
            // Arrange
            val unfavouriteImage = testImage1

            coEvery { imageContentUseCase.getAllFavouriteContents() } returns flow { emit(listOf(unfavouriteImage)) }

            // Act
            viewModel.fetchFavouriteContents()

            // Act
            viewModel.toggleContentFavourite(unfavouriteImage)

            // Verify the correct command was sent
            coVerify(exactly = 1) { imageContentUseCase.favoriteContent(unfavouriteImage) }
            coVerify(exactly = 0) { imageContentUseCase.unFavoriteContent(any()) }

            testRule.dispatcher.scheduler.advanceUntilIdle()


            assertEquals(testImage1.copy(favourite = true), viewModel.favouriteContents[0])
        }



    @Test
    fun `toggleContentFavourite should call imageContentUseCase-unfavoriteContent when item is favourite`() =
        runTest {
            // Arrange
            val favouriteImage = testImage1.copy(favourite = true)

            coEvery { imageContentUseCase.getAllFavouriteContents() } returns flow { emit(listOf(favouriteImage)) }

            // Act
            viewModel.fetchFavouriteContents()

            // Act
            viewModel.toggleContentFavourite(favouriteImage)

            // Verify the correct command was sent
            coVerify(exactly = 1) { imageContentUseCase.unFavoriteContent(favouriteImage) }
            coVerify(exactly = 0) { imageContentUseCase.favoriteContent(any()) }

            testRule.dispatcher.scheduler.advanceUntilIdle()

            assertEquals(testImage1.copy(favourite = false), viewModel.favouriteContents[0])
        }
    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
    }

}