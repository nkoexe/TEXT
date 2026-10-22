package com.nkoexe.text

import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNode
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeShowsShowButton() {
        composeRule.onNodeWithText("SHOW").assertExists()
    }

    @Test
    fun showRequiresNonBlankText() {
        val showButton = composeRule.onNodeWithText("SHOW")
        showButton.assertIsNotEnabled()

        composeRule.onNode(hasSetTextAction()).performTextInput("hello")
        showButton.assertIsEnabled()
    }
}
