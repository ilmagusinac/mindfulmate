package com.example.mindfulmate.presentation.ui.screen.chat.component.chat_mate

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.util.MessageModel

@Composable
fun MessageList(
    messageList: List<MessageModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true
    ) {
        items(messageList.reversed()) { message ->
            MessageRow(messageModel = message)
        }
    }
}

@Preview
@Composable
private fun MessageListPreview() {
    MindfulMateTheme {
        MessageList(messageList = listOf())
    }
}
