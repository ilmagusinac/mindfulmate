package com.example.mindfulmate.presentation.ui.component

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.util.MessageModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MindfulMatePartialBottomSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomSheet_displaysMessageListAndInputField() {
        val sampleMessages = listOf(
            MessageModel(message = "Hello! How can I assist you?", role = "model"),
            MessageModel(message = "I'm working on an AI project.", role = "user")
        )

        composeTestRule.setContent {
            MindfulMateTheme {
                MindfulMatePartialBottomSheet(
                    showBottomSheet = true,
                    onDismissRequest = {},
                    messageList = sampleMessages,
                    onMessageSend = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Hello! How can I assist you?").assertIsDisplayed()
        composeTestRule.onNodeWithText("I'm working on an AI project.").assertIsDisplayed()

        composeTestRule.onNodeWithText("Type a message...").assertIsDisplayed()
    }

    @Test
    fun bottomSheet_closesOnDismissRequest() {
        var showBottomSheet = true

        composeTestRule.setContent {
            MindfulMateTheme {
                MindfulMatePartialBottomSheet(
                    showBottomSheet = showBottomSheet,
                    onDismissRequest = { showBottomSheet = false },
                    messageList = emptyList(),
                    onMessageSend = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Type a message...").assertIsDisplayed()

        composeTestRule.setContent {
            showBottomSheet = false
        }

        composeTestRule.onNodeWithText("Type a message...").assertDoesNotExist()
    }
}
