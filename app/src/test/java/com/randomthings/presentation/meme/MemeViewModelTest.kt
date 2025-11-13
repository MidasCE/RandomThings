package com.randomthings.presentation.meme

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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class MemeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = TestRule()

    private val imageContentUseCase: ImageContentUseCase = mockk()

    private lateinit var viewModel: MemeViewModel

    private val testMeme = ImageContent.MemeImageContent(
        postLink = "link1",
        author = "author",
        title = "title",
        url = "url",
        nsfw = false,
        favourite = false
    )

    @Before
    fun setUp() {
        // Mock the 'init' block's call before  the ViewModel is created.
        // We'll mock a successful response for most tests.
        coEvery { imageContentUseCase.getRandomMemeContent() } returns flowOf(testMeme)
    }

    @Test
    fun `init - should fetch random meme and update state on successful creation`(): Unit =
        runTest {
            coEvery { imageContentUseCase.getRandomMemeContent() } returns flowOf(testMeme)

            // Act
            viewModel = MemeViewModel(imageContentUseCase)

            // Assert
            coVerify(exactly = 1) { imageContentUseCase.getRandomMemeContent() }

            assertEquals(testMeme, viewModel.randomMeme.value)
            assertFalse(viewModel.isRefreshing.value)
        }

    @Test
    fun `init - should set state to null on fetch error`() = runTest {
        // Arrange
        val error = Exception("Network failed")
        coEvery { imageContentUseCase.getRandomMemeContent() } returns flow { throw error }

        // Act
        viewModel = MemeViewModel(imageContentUseCase)

        // Assert
        coVerify(exactly = 1) { imageContentUseCase.getRandomMemeContent() }

        // Check that the state is null (error) and not refreshing
        assertNull(viewModel.randomMeme.value)
        assertFalse(viewModel.isRefreshing.value)
    }

    @Test
    fun `fetchRandomMeme - should fetch new meme and update state`() = runTest {
        // Arrange
        val newMeme = testMeme.copy(postLink = "link2")
        coEvery { imageContentUseCase.getRandomMemeContent() } returns flowOf(testMeme)

        viewModel = MemeViewModel(imageContentUseCase)

        coEvery { imageContentUseCase.getRandomMemeContent() } returns flowOf(newMeme)

        // Act
        viewModel.fetchRandomMeme()

        // Assert
        coVerify(exactly = 2) { imageContentUseCase.getRandomMemeContent() }

        // Assert the state holds the NEW meme
        assertEquals(newMeme, viewModel.randomMeme.value)
        assertFalse(viewModel.isRefreshing.value)
    }

    @Test
    fun `toggleContentFavourite - should handler error and log it when item is not favourited`() =
        runTest {
            // Arrange
            val exception = Exception("Error occurred")
            coEvery { imageContentUseCase.favoriteContent(any()) } throws exception

            // Act
            viewModel = MemeViewModel(imageContentUseCase)
            viewModel.toggleContentFavourite()

            // Assert
            coVerify { Log.e(any(), exception.message.orEmpty()) }
        }

    @Test
    fun `toggleContentFavourite - should handler error and log it when item is favourited`() =
        runTest {
            // Arrange
            val favouritedMeme = testMeme.copy(favourite = true)
            coEvery { imageContentUseCase.getRandomMemeContent() } returns flowOf(favouritedMeme)
            val exception = Exception("Error occurred")
            coEvery { imageContentUseCase.unFavoriteContent(any()) } throws exception

            // Act
            viewModel = MemeViewModel(imageContentUseCase)
            viewModel.toggleContentFavourite()

            // Assert
            coVerify { Log.e(any(), exception.message.orEmpty()) }
        }

    @Test
    fun `toggleContentFavourite - should call favoriteContent when item is not favourited`() =
        runTest {
            // Arrange
            coEvery { imageContentUseCase.favoriteContent(testMeme) } returns flowOf(1L)

            // Act
            viewModel = MemeViewModel(imageContentUseCase)
            viewModel.toggleContentFavourite()

            // Assert
            coVerify(exactly = 1) { imageContentUseCase.favoriteContent(testMeme) }
            coVerify(exactly = 0) { imageContentUseCase.unFavoriteContent(any()) }
        }

    @Test
    fun `toggleContentFavourite - should call unFavoriteContent when item is favourited`() =
        runTest {
            // Arrange
            val favouritedMeme = testMeme.copy(favourite = true)
            coEvery { imageContentUseCase.getRandomMemeContent() } returns flowOf(favouritedMeme)
            coEvery { imageContentUseCase.unFavoriteContent(favouritedMeme) } returns flowOf(1)

            // Act
            viewModel = MemeViewModel(imageContentUseCase) // init sets state to favouritedMeme
            viewModel.toggleContentFavourite()

            // Assert
            // Verify the correct use case was called
            coVerify(exactly = 1) { imageContentUseCase.unFavoriteContent(favouritedMeme) }
            coVerify(exactly = 0) { imageContentUseCase.favoriteContent(any()) }
        }

    @After
    fun tearDown() {
        unmockkAll() // Unmock Log.e and the use case
    }


}
