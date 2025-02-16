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
class MemeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = TestRule()

    private val imageContentUseCase: ImageContentUseCase = mockk()

    private lateinit var viewModel: MemeViewModel

    @Before
    fun setUp() {
        viewModel = MemeViewModel(
            imageContentUseCase,
        )
    }

    @Test
    fun `fetchRandomMeme should call getRandomMemeContent from imageContentUsecase`(): Unit = runTest {
        val returnContent = ImageContent.RandomImageContent("id", "author", 10, 10, "url", "downloadUrl", true)

        coEvery { imageContentUseCase.getRandomMemeContent() } returns flow { emit(returnContent) }

        // Act
        viewModel.fetchRandomMeme()

        // Assert
        coVerify() { imageContentUseCase.getRandomMemeContent() }
        assertEquals(listOf(returnContent), viewModel.randomMemes)
    }

    @Test
    fun `fetchRandomMeme should do nothing if getRandomMemeContent return exception`(): Unit = runTest {
        val exception = Exception("Error occurred")
        coEvery { imageContentUseCase.getRandomMemeContent() } throws exception

        // Act
        viewModel.fetchRandomMeme()

        // Assert
        coVerify { Log.e(any(), exception.message.orEmpty()) }
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
    }


}
