package com.example.mindfulmate.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mindfulmate.presentation.theme.DuskyWhite
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.chat.component.MessageInputField
import com.example.mindfulmate.presentation.ui.screen.chat.component.MessageList
import com.example.mindfulmate.presentation.util.MessageModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindfulMatePartialBottomSheet(
    showBottomSheet: Boolean,
    onDismissRequest: () -> Unit,
    messageList: List<MessageModel>,
    onMessageSend: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = modifier.fillMaxWidth(),
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            containerColor = DuskyWhite
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    MessageList(
                        messageList = messageList,
                        modifier = Modifier.fillMaxSize(),
                        showBackButton = false
                    )
                }

                MessageInputField(
                    onMessageSend = { message -> onMessageSend(message) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 600)
@Composable
private fun MindfulMatePartialBottomSheetPreview() {
    MindfulMateTheme {
        val sampleMessages = listOf(
            MessageModel(message = "Hello! How can I assist you?", role = "model"),
            MessageModel(message = "I'm working on an AI project.", role = "user"),
            MessageModel(message = "That's amazing! How can I help?", role = "model")
        )

        var showBottomSheet by remember { mutableStateOf(true) }

        MindfulMatePartialBottomSheet(
            showBottomSheet = showBottomSheet,
            onDismissRequest = { showBottomSheet = false },
            messageList = sampleMessages,
            onMessageSend = { message -> println("Message sent: $message") }
        )
    }
}
