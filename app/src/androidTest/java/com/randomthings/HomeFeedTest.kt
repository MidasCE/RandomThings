package com.randomthings

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import com.randomthings.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeFeedTest {
    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    @Test
    fun launchScreenTest() {

        val context = composeTestRule.activity
        val titleString = context.getString(R.string.top_bar_title_home)

        composeTestRule
            .waitUntil (
                timeoutMillis = 2000
            ) {
                composeTestRule.onNodeWithText(titleString).isDisplayed()
            }

        composeTestRule.onNode(hasTestTag("RandomThingsColumn"))
            .onChildAt(1)
            .assert(hasAnyDescendant(hasText("author1")))

        composeTestRule.onNode(hasTestTag("RandomThingsColumn"))
            .onChildAt(2)
            .assert(hasAnyDescendant(hasText("author2")))
    }
}