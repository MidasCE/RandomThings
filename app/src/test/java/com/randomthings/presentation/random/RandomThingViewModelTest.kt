package com.randomthings.presentation.random

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
import org.junit.Assert.assertEquals
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
    val testRule = TestRule()

    private val imageContentUseCase: ImageContentUseCase = mockk()

    private lateinit var viewModel: RandomThingViewModel

    @Before
    fun setUp() {
        viewModel = RandomThingViewModel(
            imageContentUseCase,
        )
    }

    @Test
    fun `fetchRandomContent shouldn't do anything if page set wasn't ready`(): Unit = runTest {
        // Act
        viewModel.fetchRandomContent()

        // Assert
        coVerify(inverse = true) { imageContentUseCase.getRandomImageContents(any(), any()) }
    }

    @Test
    fun `initialize should fetchRandomContent`(): Unit = runTest {
        val returnContent = ImageContent.RandomImageContent("id", "author", 10, 10, "url", "downloadUrl", true)

        coEvery { imageContentUseCase.getRandomImageContents(any(), any()) } returns flow { emit(listOf(returnContent)) }

        // Act
        viewModel.initialize()

        // Assert
        coVerify { imageContentUseCase.getRandomImageContents(any(), any()) }
        assertEquals(listOf(returnContent), viewModel.randomImages)
    }

    @Test
    fun `toggleContentFavourite should favoriteContent if actual content is not mark as favourite`(): Unit = runTest {
        val returnContent = ImageContent.RandomImageContent("id", "author", 10, 10, "url", "downloadUrl", false)
        val expectedResultContent = ImageContent.RandomImageContent("id", "author", 10, 10, "url", "downloadUrl", true)

        coEvery { imageContentUseCase.favoriteContent(any()) } returns flow { emit(1) }
        coEvery { imageContentUseCase.getRandomImageContents(any(), any()) } returns flow { emit(listOf(returnContent)) }


        // Act
        viewModel.initialize()
        viewModel.toggleContentFavourite(returnContent)

        // Assert
        coVerify { imageContentUseCase.favoriteContent(any()) }
        assertEquals(listOf(expectedResultContent), viewModel.randomImages)
    }

    @Test
    fun `toggleContentFavourite should unFavoriteContent if actual content is mark as favourite`(): Unit = runTest {
        val returnContent = ImageContent.RandomImageContent("id", "author", 10, 10, "url", "downloadUrl", true)
        val expectedResultContent = ImageContent.RandomImageContent("id", "author", 10, 10, "url", "downloadUrl", false)

        coEvery { imageContentUseCase.unFavoriteContent(any()) } returns flow { emit(1) }
        coEvery { imageContentUseCase.getRandomImageContents(any(), any()) } returns flow { emit(listOf(returnContent)) }


        // Act
        viewModel.initialize()
        viewModel.toggleContentFavourite(returnContent)

        // Assert
        coVerify { imageContentUseCase.unFavoriteContent(any()) }
        assertEquals(listOf(expectedResultContent), viewModel.randomImages)
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
    }
}
