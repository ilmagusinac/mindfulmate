package com.example.mindfulmate.presentation.view_model.chat.chat_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.R
import com.example.mindfulmate.domain.usecase.chat.CreateOrGetChatUseCase
import com.example.mindfulmate.domain.usecase.chat.FetchChatsUseCase
import com.example.mindfulmate.domain.usecase.chat.FetchUsernameUseCase
import com.example.mindfulmate.domain.usecase.user.GetUsersUseCase
import com.example.mindfulmate.presentation.ui.component.util.SearchItem
import com.example.mindfulmate.presentation.ui.screen.chat.util.ChatRow
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatHomeViewModel @Inject constructor(
    private val fetchChatsUseCase: FetchChatsUseCase,
    private val fetchUsernameUseCase: FetchUsernameUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val createOrGetChatUseCase: CreateOrGetChatUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ChatHomeUiState> = MutableStateFlow(ChatHomeUiState.Init)
    val uiState: StateFlow<ChatHomeUiState> = _uiState.asStateFlow()

    private val _chatRows: MutableStateFlow<List<ChatRow>> = MutableStateFlow(emptyList())
    val chatRows: StateFlow<List<ChatRow>> = _chatRows.asStateFlow()

    private val _users = MutableStateFlow<List<SearchItem>>(emptyList())
    val users: StateFlow<List<SearchItem>> = _users.asStateFlow()

    fun fetchChats() {
        _uiState.value = ChatHomeUiState.Loading
        viewModelScope.launch {
            try {
                val chats = fetchChatsUseCase.invoke()
                println("chats:$chats")
                val chatRows = chats.map { chat ->
                    println("username: " + fetchUsernameUseCase.invoke(chat))
                    ChatRow(
                        chatId = chat.chatId,
                        username = fetchUsernameUseCase.invoke(chat),
                        lastMessage = chat.lastMessage,
                        date = formatDate(chat.lastMessageTimestamp),
                        time = formatTime(chat.lastMessageTimestamp),
                        newMessage = chat.hasUnreadMessages,
                        isChatClicked = { chatId -> /* Handle navigation */ }
                    )
                }
                _chatRows.value = chatRows
                _uiState.value = ChatHomeUiState.Success
            } catch (e: Exception) {
                _uiState.value = ChatHomeUiState.Failure(e.message ?: "Unknown error")
            }
        }
    }

    private fun formatDate(timestamp: Timestamp?): String {
        val formatter = java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.getDefault())
        return formatter.format(timestamp?.toDate())
    }

    private fun formatTime(timestamp: Timestamp?): String {
        val formatter = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
        return formatter.format(timestamp?.toDate())
    }

    fun fetchUsernames() {
        viewModelScope.launch {
            try {
                val fetchedUsers: List<Pair<String, String>> = getUsersUseCase.invoke()
                _users.value = fetchedUsers.map { pair ->
                    SearchItem(
                        id = pair.first,
                        label = pair.second,
                        placeholderRes = R.drawable.ic_heart
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun startChatWithUser(otherUserId: String, onChatCreated: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val chatId = createOrGetChatUseCase(otherUserId)
                onChatCreated(chatId)
            } catch (e: Exception) {
                _uiState.value = ChatHomeUiState.Failure("Failed to start chat: ${e.localizedMessage}")
            }
        }
    }

}
