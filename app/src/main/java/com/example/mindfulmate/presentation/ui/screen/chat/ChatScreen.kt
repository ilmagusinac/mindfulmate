package com.example.mindfulmate.presentation.ui.screen.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.domain.model.chat.Message
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMateMessageInputField
import com.example.mindfulmate.presentation.ui.screen.chat.component.chat.ChatHeaderSection
import com.example.mindfulmate.presentation.ui.screen.chat.component.chat.ChatMessageList
import com.example.mindfulmate.presentation.util.DevicesPreview
import com.example.mindfulmate.presentation.view_model.chat.chat.ChatNavigationEvent
import com.example.mindfulmate.presentation.view_model.chat.chat.ChatUiState
import com.example.mindfulmate.presentation.view_model.chat.chat.ChatViewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    chatId: String,
    navigate: () -> Unit,
    modifier: Modifier = Modifier,
    onGoBackClick: () -> Unit
) {
    val uiState: ChatUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val username by viewModel.username.collectAsStateWithLifecycle()

    NavigationEventHandler(
        viewModel = viewModel,
        navigate = navigate
    )
    LaunchedEffect(chatId) {
        viewModel.fetchMessages(chatId)
        viewModel.listenForMessages(chatId) { updatedMessages ->
            // The messages StateFlow in the ViewModel will be updated automatically
        }
        viewModel.getUsername(chatId)
    }

    when (uiState) {
        is ChatUiState.Loading -> {
            LoadingPlaceholder()
        }

        is ChatUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = { viewModel.fetchMessages(chatId) })
        }

        is ChatUiState.Success -> {
            ChatScreen(
                username = username,
                messageList = messages,
                onMessageSend = { message -> viewModel.sendMessage(chatId, message) },
                currentUser = viewModel.getCurrentUserId(),
                onGoBackClick = onGoBackClick,
                onDeleteChatClick = { viewModel.deleteChat(chatId)},
                modifier = modifier
            )
        }

        else -> {
            // Optional: handle Init state if necessary
        }
    }
}

@Composable
private fun NavigationEventHandler(
    viewModel: ChatViewModel,
    navigate: () -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                is ChatNavigationEvent.Navigate -> {
                    navigate()
                }
            }
        }
    }
}

@Composable
private fun ChatScreen(
    currentUser: String?,
    username: String,
    onGoBackClick: () -> Unit,
    onDeleteChatClick: () -> Unit,
    modifier: Modifier = Modifier,
    messageList: List<Message> = listOf(),
    onMessageSend: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        ChatHeaderSection(
            username = username,
            onBackButtonClick = onGoBackClick,
            onDeleteChatClick = onDeleteChatClick
        )
        ChatMessageList(
            modifier = Modifier.weight(1f),
            messageList = messageList,
            currentUser = currentUser
        )
        if (messageList.isEmpty()) {
            Column(modifier = modifier) {
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
            }
        }
        MindfulMateMessageInputField(onMessageSend = onMessageSend)
    }
}

@DevicesPreview
@Composable
fun ChatPagePreview() {
    ChatScreen(
        onMessageSend = {},
        currentUser = "current",
        username = "username",
        onGoBackClick = {},
        onDeleteChatClick = {}
    )
}
