package com.randomthings.presentation.base

import com.randomthings.TestRule
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class BaseViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = TestRule()

    class TestViewModel :
        BaseViewModel() {
        fun testLaunchNetwork(
            error: (Throwable) -> Unit = {},
            block: suspend CoroutineScope.() -> Unit
        ) {
            launchNetwork(error, block)
        }
    }

    private val viewModel = spyk(TestViewModel())

    @Test
    fun `launchNetwork should deliver error if got throwable inside block`() = runTest {

        // Data
        val exception = Exception("Error occurred")
        val errorHandler: (Throwable) -> Unit = mockk()
        every { errorHandler(exception) } just Runs

        // Act
        viewModel.testLaunchNetwork(error = errorHandler) { throw exception }

        // Assert
        verify { errorHandler(exception) }
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }
}
