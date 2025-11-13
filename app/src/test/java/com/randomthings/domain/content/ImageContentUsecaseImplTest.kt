package com.randomthings.domain.content

import com.randomthings.TestRule
import com.randomthings.data.local.db.entity.FavouriteDataType
import com.randomthings.data.local.db.entity.FavouriteEntity
import com.randomthings.data.local.db.entity.ImageEntity
import com.randomthings.data.local.db.entity.MemeEntity
import com.randomthings.data.model.Image
import com.randomthings.data.model.Meme
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


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

    @Test
    fun `getRandomImageContents should return mapped data as list of RandomImageContent`(): Unit = runTest {
        val repoImage1 = Image(
            id = "id1",
            width = 100,
            height = 100,
            author = "author",
            url = "url",
            downloadUrl = "downloadUrl"
        )
        val repoImage2 = Image(
            id = "id2",
            width = 100,
            height = 100,
            author = "author",
            url = "url",
            downloadUrl = "downloadUrl"
        )

        val expectedResult = listOf(
            ImageContent.RandomImageContent(
                id = repoImage1.id,
                width = repoImage1.width,
                height = repoImage1.height,
                author = repoImage1.author,
                url = repoImage1.url,
                downloadUrl = repoImage1.downloadUrl,
                favourite = true,
            ),
            ImageContent.RandomImageContent(
                id = repoImage2.id,
                width = repoImage2.width,
                height = repoImage2.height,
                author = repoImage2.author,
                url = repoImage2.url,
                downloadUrl = repoImage2.downloadUrl,
                favourite = false,
            ),
        )

        coEvery { favouriteRepository.getFavouriteIdsByType(FavouriteDataType.Image) } returns flowOf(listOf("id1"))

        coEvery { imageRepository.getImageInfoList(any(), any()) } returns flow { emit(listOf(repoImage1, repoImage2)) }

        // Act
        val result = viewModel.getRandomImageContents(1, 10)

        // Assert
        coVerify { imageRepository.getImageInfoList(any(), any()) }
        coVerify { favouriteRepository.getFavouriteIdsByType(FavouriteDataType.Image) }
        assertEquals(expectedResult, result.single())
    }

    @Test
    fun `getRandomMemeContent should return mapped data as RandomImageContent`(): Unit = runTest {
        val repoMeme = Meme(
            postLink = "postLink",
            title = "title",
            url = "url",
            nsfw = false,
            author = "author",
        )

        val expectedResult = ImageContent.MemeImageContent(
            postLink = repoMeme.postLink,
            title = repoMeme.title,
            url = repoMeme.url,
            nsfw = repoMeme.nsfw,
            author = repoMeme.author,
            favourite = true,
        )

        coEvery { memeRepository.getRandomMeme() } returns flow { emit(repoMeme) }
        coEvery { favouriteRepository.isFavourite(any(), any()) } returns flow { emit(true) }

        // Act
        val result = viewModel.getRandomMemeContent()

        // Assert
        coVerify { memeRepository.getRandomMeme() }
        assertEquals(expectedResult, result.single())
    }

    @Test
    fun `favoriteContent should saveImageToDB and saveAsFavourite if data is RandomImageContent`(): Unit = runTest {
        val randomImageContent = ImageContent.RandomImageContent(
            id = "id",
            width = 100,
            height = 100,
            author = "author",
            url = "url",
            downloadUrl = "downloadUrl",
            favourite = true,
        )

        coEvery { imageRepository.saveImageToDB(any()) } returns flowOf(1)
        coEvery { favouriteRepository.saveAsFavourite(any(), any()) } returns flowOf(1)

        // Act
        val result = viewModel.favoriteContent(randomImageContent)

        // Collect the flow to trigger execution
        result.collect{ }

        // Assert
        coVerify { imageRepository.saveImageToDB(match { it.id == randomImageContent.id }) }
        coVerify { favouriteRepository.saveAsFavourite(FavouriteDataType.Image, randomImageContent.id) }
    }

    @Test
    fun `favoriteContent should saveMemeToDB and saveAsFavourite if data is MemeImageContent`(): Unit = runTest {
        val memeContent = ImageContent.MemeImageContent(
            postLink = "postLink",
            title = "title",
            url = "url",
            nsfw = false,
            author = "author",
            favourite = true,
        )

        coEvery { memeRepository.saveMemeToDB(any()) } returns flowOf(1)
        coEvery { favouriteRepository.saveAsFavourite(any(), any()) } returns flowOf(1)

        // Act
        val result = viewModel.favoriteContent(memeContent)

        // Collect the flow to trigger execution
        result.collect{ }

        // Assert
        coVerify { memeRepository.saveMemeToDB(match { it.postLink == memeContent.postLink }) }
        coVerify { favouriteRepository.saveAsFavourite(FavouriteDataType.Meme, memeContent.postLink) }
    }

    @Test
    fun `unFavoriteContent should removeImageFromDB and removeFavourite if data is RandomImageContent`(): Unit = runTest {
        val randomImageContent = ImageContent.RandomImageContent(
            id = "id",
            width = 100,
            height = 100,
            author = "author",
            url = "url",
            downloadUrl = "downloadUrl",
            favourite = true,
        )

        coEvery { imageRepository.removeImageFromDB(any()) } returns flowOf(1)
        coEvery { favouriteRepository.removeFavourite(any(), any()) } returns flowOf(1)

        // Act
        val result = viewModel.unFavoriteContent(randomImageContent)

        // Collect the flow to trigger execution
        result.collect{ }

        // Assert
        coVerify { imageRepository.removeImageFromDB(randomImageContent.id) }
        coVerify { favouriteRepository.removeFavourite(FavouriteDataType.Image, randomImageContent.id) }
    }

    @Test
    fun `unFavoriteContent should removeImageFromDB and removeFavourite if data is MemeImageContent`(): Unit = runTest {
        val memeContent = ImageContent.MemeImageContent(
            postLink = "postLink",
            title = "title",
            url = "url",
            nsfw = false,
            author = "author",
            favourite = true,
        )


        coEvery { memeRepository.removeImageFromDB(any()) } returns flowOf(1)
        coEvery { favouriteRepository.removeFavourite(any(), any()) } returns flowOf(1)

        // Act
        val result = viewModel.unFavoriteContent(memeContent)

        // Collect the flow to trigger execution
        result.collect{ }

        // Assert
        coVerify { memeRepository.removeImageFromDB(memeContent.postLink) }
        coVerify { favouriteRepository.removeFavourite(FavouriteDataType.Meme, memeContent.postLink) }
    }

    @Test
    fun `getAllFavouriteContents should merge the data and sort by creation date`(): Unit = runTest {
        val meme1 = FavouriteEntity(
            1, FavouriteDataType.Meme, "data1"
        )
        val meme2 = FavouriteEntity(
            2, FavouriteDataType.Meme, "data2"
        )
        val image1 = FavouriteEntity(
            3, FavouriteDataType.Image, "image1"
        )

        val localDate = LocalDate.now()
        val memeEntity1 = MemeEntity(
            postLink = "postLink1",
            title = "title",
            url = "url",
            nsfw = false,
            author = "author",
            createdAt = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
        )
        val imageEntity = ImageEntity(
            id = "id",
            width = 100,
            height = 100,
            author = "author",
            url = "url",
            downloadUrl = "downloadUrl",
            createdAt = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
        )
        val memeEntity2 = MemeEntity(
            postLink = "postLink2",
            title = "title",
            url = "url",
            nsfw = false,
            author = "author",
            createdAt = Date.from(localDate.plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant()),
        )


        coEvery { favouriteRepository.getAllFavourites() } returns flowOf(listOf(meme1, meme2, image1))
        coEvery { imageRepository.getSavedImagesFromDB(any()) } returns flowOf(listOf(imageEntity))
        coEvery { memeRepository.getSavedImagesFromDB(any()) } returns flowOf(listOf(memeEntity1, memeEntity2))
        coEvery { favouriteRepository.isFavourite(any(), any()) } returns flow { emit(true) }

        val expectedResult = listOf(
            ImageContent.MemeImageContent(
                postLink = "postLink2",
                title = "title",
                url = "url",
                nsfw = false,
                author = "author",
                favourite = true,
            ),
            ImageContent.RandomImageContent(
                id = "id",
                width = 100,
                height = 100,
                author = "author",
                url = "url",
                downloadUrl = "downloadUrl",
                favourite = true,
            ),
            ImageContent.MemeImageContent(
                postLink = "postLink1",
                title = "title",
                url = "url",
                nsfw = false,
                author = "author",
                favourite = true,
            )
        )

        // Act
        viewModel.getAllFavouriteContents().collect {
            assertEquals(expectedResult, it)
        }

        coVerify { imageRepository.getSavedImagesFromDB(any()) }
        coVerify { memeRepository.getSavedImagesFromDB(any()) }
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
    }
}
