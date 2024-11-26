package com.example.mindfulmate.presentation.ui.screen.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mindfulmate.presentation.ui.screen.chat.component.MessageInputField
import com.example.mindfulmate.presentation.ui.screen.chat.component.MessageList
import com.example.mindfulmate.presentation.util.DevicesPreview
import com.example.mindfulmate.presentation.util.MessageModel

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    messageList: List<MessageModel> = listOf(),
    onMessageSend: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = messageList,
            onBackButtonClick = { println("Back button clicked") }
        )
        MessageInputField(onMessageSend = onMessageSend)
    }
}

@DevicesPreview
@Composable
fun ChatPagePreview() {
    val sampleMessages = listOf(
        MessageModel(message = "Hello! How can I help you today?", role = "model"),
        MessageModel(message = "I am working on a school project.", role = "user"),
        MessageModel(message = "That's great! What is it about?", role = "model"),
        MessageModel(
            message = "It's about integrating AI in an Android app.nejncenckfecdkdrefbhdhucijkmfrn",
            role = "user"
        )
    )

    ChatScreen(
        messageList = sampleMessages,
        onMessageSend = { message ->
            println("Message sent: $message")
        }
    )
}
