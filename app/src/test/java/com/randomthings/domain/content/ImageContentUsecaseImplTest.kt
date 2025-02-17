package com.randomthings.domain.content

import com.randomthings.TestRule
import com.randomthings.data.model.Image
import com.randomthings.data.repository.FavouriteRepository
import com.randomthings.data.repository.ImageRepository
import com.randomthings.data.repository.MemeRepository
import com.randomthings.domain.entity.ImageContent
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
class ImageContentUsecaseImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = TestRule()

    private val imageRepository: ImageRepository = mockk()

    private val memeRepository: MemeRepository = mockk()

    private val favouriteRepository: FavouriteRepository = mockk()

    private lateinit var viewModel: ImageContentUseCase

    @Before
    fun setUp() {
        viewModel = ImageContentUseCaseImpl(
            imageRepository, memeRepository, favouriteRepository
        )
    }

    @Test
    fun `getRandomImageContent should return mapped data as RandomImageContent`(): Unit = runTest {
        val repoImage = Image(
            id = "id",
            width = 100,
            height = 100,
            author = "author",
            url = "url",
            downloadUrl = "downloadUrl"
        )

        val expectedResult = ImageContent.RandomImageContent(
            id = repoImage.id,
            width = repoImage.width,
            height = repoImage.height,
            author = repoImage.author,
            url = repoImage.url,
            downloadUrl = repoImage.downloadUrl,
            favourite = true,
        )

        coEvery { imageRepository.getImageInfo(any()) } returns flow { emit(repoImage) }
        coEvery { favouriteRepository.isFavourite(any(), any()) } returns flow { emit(true) }

        // Act
        val result = viewModel.getRandomImageContent()

        // Assert
        coVerify { imageRepository.getImageInfo(any()) }
        assertEquals(expectedResult, result.single())
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
    }
}
