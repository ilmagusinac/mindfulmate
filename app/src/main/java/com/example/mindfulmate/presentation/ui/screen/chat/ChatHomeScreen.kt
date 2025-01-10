package com.example.mindfulmate.presentation.ui.screen.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMateMainHeaderSection
import com.example.mindfulmate.presentation.ui.component.MindfulMateSearchField
import com.example.mindfulmate.presentation.ui.component.util.SearchItem
import com.example.mindfulmate.presentation.ui.screen.chat.component.chat_home.MultipleChatRowsSection
import com.example.mindfulmate.presentation.ui.screen.chat.util.ChatRow
import com.example.mindfulmate.presentation.view_model.chat.chat_home.ChatHomeUiState
import com.example.mindfulmate.presentation.view_model.chat.chat_home.ChatHomeViewModel

@Composable
fun ChatHomeScreen(
    viewModel: ChatHomeViewModel,
    onChatClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: ChatHomeUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val chatRows by viewModel.chatRows.collectAsStateWithLifecycle()
    val users by viewModel.users.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchChats()
        viewModel.fetchUsernames()
    }

    when (uiState) {
        is ChatHomeUiState.Loading -> {
            LoadingPlaceholder()
        }

        is ChatHomeUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = {})
        }

        is ChatHomeUiState.Success -> {
            var textState by remember { mutableStateOf(TextFieldValue("")) }
            ChatHomeScreen(
                chatRows = chatRows,
                users = textState,
                onUsersChange = { textState = it },
                allSearchItems = users,
                onChatClicked = onChatClicked,
                onSearchItemClick = { searchItem ->
                    println("searchitemid: ${searchItem.id}")
                    viewModel.startChatWithUser(searchItem.id) { chatId ->
                        onChatClicked(chatId)
                    }
                },
                modifier = modifier
            )
        }

        else -> {
            // no-op
        }
    }
}

@Composable
private fun ChatHomeScreen(
    chatRows: List<ChatRow>,
    users: TextFieldValue,
    allSearchItems: List<SearchItem>,
    onUsersChange: (TextFieldValue) -> Unit,
    onChatClicked: (String) -> Unit,
    onSearchItemClick: (SearchItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                horizontal = dimensionResource(id = R.dimen.padding_medium)
            )
    ) {
        MindfulMateMainHeaderSection(
            iconRes = R.drawable.ic_chat,
            title = stringResource(id = R.string.chat_page_title)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxmedium)))
        MindfulMateSearchField(
            text = users,
            placeholder = "Search users...",
            allSearchItems = allSearchItems,
            onTextValueChange = onUsersChange,
            onSearchItemClick = onSearchItemClick
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxxmedium)))
        MultipleChatRowsSection(
            rows = chatRows.map { chat ->
                ChatRow(
                    chatId = chat.chatId,
                    username = chat.username,
                    lastMessage = chat.lastMessage,
                    date = chat.date,
                    time = chat.time,
                    newMessage = chat.newMessage,
                    isChatClicked = onChatClicked
                )
            }
        )
    }
}

@Preview
@Composable
private fun ChatHomeScreenPreview() {
    MindfulMateTheme {
        var textState by remember { mutableStateOf(TextFieldValue("")) }

        ChatHomeScreen(
            chatRows = listOf(
                ChatRow(
                    chatId = "ff",
                    username = "username",
                    lastMessage = "last message",
                    date = "12/3/2024",
                    time = "12:33",
                    newMessage = true,
                    isChatClicked = {}
                )
            ),
            users = textState,
            onUsersChange = { textState = it },
            onChatClicked = {},
            onSearchItemClick = {},
            allSearchItems = emptyList()
        )
    }
}
