package com.randomthings.presentation.random

import com.randomthings.TestRule
import com.randomthings.domain.content.ImageContentUseCase
import com.randomthings.domain.entity.ImageContent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class RandomThingViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = TestRule(StandardTestDispatcher())

    private val imageContentUseCase: ImageContentUseCase = mockk()

    private lateinit var favoriteIdsFlow: MutableStateFlow<List<String>>

    private lateinit var viewModel: RandomThingViewModel

    // --- Test Data ---
    private val testImage1 =
        ImageContent.RandomImageContent("id1", "author1", 10, 10, "url1", "downloadUrl1", false)
    private val testImage2 =
        ImageContent.RandomImageContent("id2", "author2", 10, 10, "url2", "downloadUrl2", false)

    @Before
    fun setUp() {
        favoriteIdsFlow = MutableStateFlow(emptyList())
        coEvery { imageContentUseCase.observeFavouriteImageIds() } returns favoriteIdsFlow

        coEvery { imageContentUseCase.getRandomImageContents(any(), any()) } returns flowOf(
            listOf(
                testImage1
            )
        )

        coEvery { imageContentUseCase.favoriteContent(any()) } returns flowOf(1L)
        coEvery { imageContentUseCase.unFavoriteContent(any()) } returns flowOf(1)

        viewModel = RandomThingViewModel(
            imageContentUseCase,
        )

        testRule.dispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `init - should fetch initial content AND observe favorites`() = runTest {
        // Assert
        coVerify(exactly = 1) { imageContentUseCase.getRandomImageContents(any(), any()) }
        coVerify(exactly = 1) { imageContentUseCase.observeFavouriteImageIds() }

        assertEquals(listOf(testImage1), viewModel.randomImages)
        assertFalse(viewModel.isRefreshing.value)
    }

    @Test
    fun `refreshData - should clear list and fetch new content`() = runTest {
        // Arrange
        assertEquals(listOf(testImage1), viewModel.randomImages)

        coEvery { imageContentUseCase.getRandomImageContents(any(), any()) } returns flowOf(
            listOf(
                testImage2
            )
        )

        // Act
        viewModel.refreshData()
        testRule.dispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify(exactly = 2) { imageContentUseCase.getRandomImageContents(any(), any()) }

        assertEquals(listOf(testImage2), viewModel.randomImages)
        assertFalse(viewModel.isRefreshing.value)
    }

    @Test
    fun `fetchRandomContent - should add new content to existing list`() = runTest {
        // Arrange
        assertEquals(listOf(testImage1), viewModel.randomImages)

        // Mock the *next* call for 'fetchRandomContent' (pagination)
        coEvery { imageContentUseCase.getRandomImageContents(any(), any()) } returns flowOf(
            listOf(
                testImage2
            )
        )

        // Act
        viewModel.fetchRandomContent() // This is the pagination call, not refresh
        testRule.dispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify(exactly = 2) { imageContentUseCase.getRandomImageContents(any(), any()) }

        assertEquals(listOf(testImage1, testImage2), viewModel.randomImages)
        assertFalse(viewModel.isLoadingMore.value)
    }

    @Test
    fun `toggleContentFavourite - should call favoriteContent when item is not favourited`() =
        runTest {
            // Act
            viewModel.toggleContentFavourite(testImage1)
            testRule.dispatcher.scheduler.advanceUntilIdle()

            // Verify the correct command was sent
            coVerify(exactly = 1) { imageContentUseCase.favoriteContent(testImage1) }
            coVerify(exactly = 0) { imageContentUseCase.unFavoriteContent(any()) }
        }

    @Test
    fun `toggleContentFavourite - should call unFavoriteContent when item is favourited`() =
        runTest {
            // Arrange
            val favouritedImage = testImage1.copy(favourite = true)

            // Act
            viewModel.toggleContentFavourite(favouritedImage)
            testRule.dispatcher.scheduler.advanceUntilIdle()

            // Verify the correct command was sent
            coVerify(exactly = 1) { imageContentUseCase.unFavoriteContent(favouritedImage) }
            coVerify(exactly = 0) { imageContentUseCase.favoriteContent(any()) }
        }

    @Test
    fun `observeFavoriteChanges - should update list reactively when favorites change`() = runTest {
        // Arrange
        assertFalse(viewModel.randomImages.first { it.url == testImage1.url }.favourite)

        // Act 1: Simulate a "favorite" event from the database
        favoriteIdsFlow.value = listOf(testImage1.id)
        testRule.dispatcher.scheduler.advanceUntilIdle() // Let the collector run

        // Assert 1: The item in the list is now marked as favourite
        assertTrue(viewModel.randomImages.first { it.url == testImage1.url }.favourite)

        // Act 2: Simulate an "un-favorite" event
        favoriteIdsFlow.value = emptyList()
        testRule.dispatcher.scheduler.advanceUntilIdle() // Let the collector run again

        // Assert 2: The item is marked as un-favourited
        assertFalse(viewModel.randomImages.first { it.url == testImage1.url }.favourite)
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
    }
}
