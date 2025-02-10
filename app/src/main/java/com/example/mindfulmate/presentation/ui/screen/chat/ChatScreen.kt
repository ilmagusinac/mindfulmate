package com.example.mindfulmate.presentation.ui.screen.chat

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.domain.model.chat.Message
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.chat.component.chat.ChatHeaderSection
import com.example.mindfulmate.presentation.ui.screen.chat.component.chat.ChatMessageInputField
import com.example.mindfulmate.presentation.ui.screen.chat.component.chat.ChatMessageList
import com.example.mindfulmate.presentation.ui.screen.chat.util.MessageActionBottomSheet
import com.example.mindfulmate.presentation.ui.screen.community.DeleteContentPopUp
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
    val profileImage by viewModel.profileImage.collectAsStateWithLifecycle()
    val currentUserId by viewModel.currentUserId.collectAsStateWithLifecycle()
    val editingMessageId by viewModel.editingMessageId.collectAsStateWithLifecycle()
    val messageInput by viewModel.messageInput.collectAsStateWithLifecycle()
    val isDeleteChatPopupVisible by viewModel.isDeleteChatPopupVisible.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

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
        viewModel.getProfileImage(chatId)
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
                profileImageUrl = profileImage,
                messageList = messages,
                currentUser = currentUserId,
                messageInput = messageInput,
                isEditing = editingMessageId != null,
                onMessageTextChange = { viewModel.updateMessageInput(it) },
                onMessageSend = {
                    if (editingMessageId != null) {
                        viewModel.updateMessage(chatId, editingMessageId!!, messageInput)
                    } else {
                        viewModel.sendMessage(chatId, messageInput)
                    }
                },
                onCancelEdit = { viewModel.cancelEditing() },
                onGoBackClick = onGoBackClick,
                onDeleteChatClick = { viewModel.showDeleteChatPopup() },
                onEditMessage = { message ->
                    viewModel.startEditingMessage(message)
                },
                onDeleteMessage = { message ->
                    viewModel.deleteMessage(chatId, message.id)
                },
                modifier = modifier
            )
        }

        else -> {
            // Optional: handle Init state if necessary
        }
    }

    if (isDeleteChatPopupVisible) {
        DeleteContentPopUp(
            deleteTitle = "Delete Conversation",
            deleteDialog = "If you want to delete this conversation you can confirm it here",
            onCancelClick = { viewModel.hideDeleteChatPopup() },
            onDeleteClick = { viewModel.deleteChat(chatId) }
        )
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
    username: String,
    profileImageUrl: String,
    messageList: List<Message>,
    currentUser: String?,
    messageInput: String,
    isEditing: Boolean,
    onMessageTextChange: (String) -> Unit,
    onMessageSend: () -> Unit,
    onCancelEdit: () -> Unit,
    onGoBackClick: () -> Unit,
    onDeleteChatClick: () -> Unit,
    onEditMessage: (Message) -> Unit,
    onDeleteMessage: (Message) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMessage by remember { mutableStateOf<Message?>(null) }
    var showOptionsDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        ChatHeaderSection(
            username = username,
            imageUrl = profileImageUrl,
            onBackButtonClick = onGoBackClick,
            onDeleteChatClick = onDeleteChatClick
        )
        ChatMessageList(
            modifier = Modifier.weight(1f),
            messageList = messageList,
            currentUser = currentUser,
            onDeleteMessage = { message ->
                selectedMessage = message
                showOptionsDialog = true
            },
            onEditMessage = { message ->
                selectedMessage = message
                showOptionsDialog = true
            }
        )
        if (showOptionsDialog && selectedMessage != null) {
            MessageActionBottomSheet(
                message = selectedMessage!!,
                currentUser = currentUser,
                onDismiss = { showOptionsDialog = false },
                onEdit = {
                    onEditMessage(selectedMessage!!)
                    showOptionsDialog = false
                },
                onDelete = {
                    onDeleteMessage(selectedMessage!!)
                    showOptionsDialog = false
                }
            )
        }

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
        ChatMessageInputField(
            messageText = messageInput,
            onMessageTextChange = onMessageTextChange,
            onSendMessage = onMessageSend,
            isEditing = isEditing,
            onCancelEdit = onCancelEdit
        )
    }
}

@DevicesPreview
@Composable
fun ChatPagePreview() {
    MindfulMateTheme {

    }
}
