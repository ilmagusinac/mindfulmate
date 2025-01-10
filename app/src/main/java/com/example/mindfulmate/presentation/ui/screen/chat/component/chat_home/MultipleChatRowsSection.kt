package com.example.mindfulmate.presentation.ui.screen.chat.component.chat_home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.screen.chat.util.ChatRow

@Composable
fun MultipleChatRowsSection(
    rows: List<ChatRow>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (rows.isEmpty()) {
            Text(
                text = stringResource(id = R.string.no_conversations_yet),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Grey,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            rows.forEach { row ->
                ChatHomeRow(
                    username = row.username,
                    lastMessage = row.lastMessage,
                    date = row.date,
                    time = row.time,
                    newMessage = row.newMessage,
                    isChatClicked = { row.isChatClicked(row.chatId) },
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_xxmedium))
                )
            }
        }
    }
}

@Preview
@Composable
private fun MultipleChatRowsSectionPreview() {
    MindfulMateTheme {
        MultipleChatRowsSection(
            rows = listOf(
                ChatRow(
                    chatId = "1",
                    username = "username",
                    lastMessage = "This is our last message",
                    date = "12/7/2024",
                    time = "12:33",
                    newMessage = true,
                    isChatClicked = {}
                ),
                ChatRow(
                    chatId = "2",
                    username = "username",
                    lastMessage = "This is our last message",
                    date = "12/7/2024",
                    time = "12:33",
                    newMessage = true,
                    isChatClicked = {}
                )
            )
        )
    }
}

@Preview
@Composable
private fun NoMultipleChatRowsSectionPreview() {
    MindfulMateTheme {
        MultipleChatRowsSection(rows = emptyList())
    }
}
