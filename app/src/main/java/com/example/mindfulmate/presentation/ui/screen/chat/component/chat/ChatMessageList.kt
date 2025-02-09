package com.example.mindfulmate.presentation.ui.screen.chat.component.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.domain.model.chat.Message
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateMessageInputField

@Composable
fun ChatMessageList(
    messageList: List<Message>,
    currentUser: String?,
    onEditMessage: (Message) -> Unit,
    onDeleteMessage: (Message) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        items(messageList.reversed()) { message ->
            ChatMessageRow(
                messageModel = message,
                currentUser = currentUser,
                onEditMessage = onEditMessage,
                onDeleteMessage = onDeleteMessage
            )
        }
    }
}

@Preview
@Composable
private fun MessageListPreview() {
    MindfulMateTheme {
        val sampleMessages = listOf(
            Message(
                id = "1",
                senderId = "user1",
                text = "Hello!",
                timestamp = null,
                isRead = true
            ),
            Message(
                id = "2",
                senderId = "yourCurrentUserId",
                text = "Hi there!",
                timestamp = null,
                isRead = true
            )
        )

        ChatMessageList(
            messageList = sampleMessages,
            currentUser = "yourCurrentUserId",
            onDeleteMessage = {},
            onEditMessage = {}
        )
    }
}

@Preview
@Composable
private fun NoMessageListPreview() {
    MindfulMateTheme {
        val sampleMessages = emptyList<Message>()

        Column {
            ChatMessageList(
                messageList = sampleMessages,
                currentUser = "yourCurrentUserId",
                onDeleteMessage = {},
                onEditMessage = {}
            )
            MindfulMateMessageInputField(onMessageSend = {})
        }
    }
}