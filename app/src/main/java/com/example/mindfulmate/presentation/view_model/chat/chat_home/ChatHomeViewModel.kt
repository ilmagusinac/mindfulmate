package com.example.mindfulmate.presentation.view_model.chat.chat_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.usecase.chat.CreateOrGetChatUseCase
import com.example.mindfulmate.domain.usecase.chat.FetchChatsUseCase
import com.example.mindfulmate.domain.usecase.chat.FetchUnreadChatsCountUseCase
import com.example.mindfulmate.domain.usecase.chat.GetCurrentUserIdUseCase
import com.example.mindfulmate.domain.usecase.chat.ListenForUnreadMessagesUseCase
import com.example.mindfulmate.domain.usecase.chat.MarkMessageAsReadUseCase
import com.example.mindfulmate.domain.usecase.user.GetAllUsersUseCase
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
    private val getUsersUseCase: GetUsersUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val createOrGetChatUseCase: CreateOrGetChatUseCase,
    private val markMessageAsReadUseCase: MarkMessageAsReadUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val listenForUnreadMessagesUseCase: ListenForUnreadMessagesUseCase,
    private val fetchUnreadChatsCountUseCase: FetchUnreadChatsCountUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ChatHomeUiState> = MutableStateFlow(ChatHomeUiState.Init)
    val uiState: StateFlow<ChatHomeUiState> = _uiState.asStateFlow()

    private val _chatRows: MutableStateFlow<List<ChatRow>> = MutableStateFlow(emptyList())
    val chatRows: StateFlow<List<ChatRow>> = _chatRows.asStateFlow()

    private val _usersSearch = MutableStateFlow<List<SearchItem>>(emptyList())
    val usersSearch: StateFlow<List<SearchItem>> = _usersSearch.asStateFlow()

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId

    private val _unreadMessagesCount = MutableStateFlow(0)
    val unreadMessagesCount: StateFlow<Int> = _unreadMessagesCount

    fun updateUnreadMessagesCount(count: Int) {
        _unreadMessagesCount.value = count
    }

    fun fetchUnreadMessagesCount() {
        viewModelScope.launch {
            listenForUnreadMessagesUseCase.invoke { unreadCount ->
                _unreadMessagesCount.value = unreadCount
            }
        }
    }
/*
    fun fetchChats() {
        _uiState.value = ChatHomeUiState.Loading
        viewModelScope.launch {
            try {
                val currentUserId = getCurrentUserId()
                println("CURRENTUSERID: $currentUserId")
                val chats = fetchChatsUseCase.invoke()
                val chatRows = chats.map { chat ->
                    val otherParticipant =
                        chat.participants.firstOrNull { it.userId != currentUserId }
                    ChatRow(
                        chatId = chat.chatId,
                        currentUserId = currentUserId,
                        username = otherParticipant?.username
                            ?: "",//fetchUsernameUseCase.invoke(chat),
                        lastMessage = chat.lastMessage,
                        date = formatDate(chat.lastMessageTimestamp),
                        time = formatTime(chat.lastMessageTimestamp),
                        newMessage = chat.hasUnreadMessages,
                        profilePicture = otherParticipant?.profilePicture ?: "",
                        isChatClicked = { chatId -> /* Handle navigation */ }
                    )
                }
                _chatRows.value = chatRows
                _uiState.value = ChatHomeUiState.Success
            } catch (e: Exception) {
                _uiState.value = ChatHomeUiState.Failure(e.message ?: "Unknown error")
            }
        }
    }*/
fun fetchChats() {
    _uiState.value = ChatHomeUiState.Loading
    viewModelScope.launch {
        try {
            val currentUserId = getCurrentUserId()
            val chats = fetchChatsUseCase.invoke()
            val chatRows = chats.map { chat ->
                val otherParticipant =
                    chat.participants.firstOrNull { it.userId != currentUserId }

                ChatRow(
                    chatId = chat.chatId,
                    currentUserId = currentUserId,
                    username = otherParticipant?.username ?: "",
                    lastMessage = chat.lastMessage,
                    date = formatDate(chat.lastMessageTimestamp),
                    time = formatTime(chat.lastMessageTimestamp),
                    newMessage = chat.unreadBy.contains(currentUserId), // ✅ Only show dot if user has unread messages
                    profilePicture = otherParticipant?.profilePicture ?: "",
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
                val fetchedUsers = getAllUsersUseCase.invoke()
                _usersSearch.value = fetchedUsers.map { pair ->
                    SearchItem(
                        id = pair.id,
                        label = pair.username,
                        placeholderRes = pair.profileImageUrl
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
                _uiState.value =
                    ChatHomeUiState.Failure("Failed to start chat: ${e.localizedMessage}")
            }
        }
    }

    fun markMessagesAsRead(chatId: String) {
        viewModelScope.launch {
            try {
                val currentUserId = getCurrentUserId()
                markMessageAsReadUseCase.invoke(chatId, currentUserId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getCurrentUserId(): String {
        return getCurrentUserIdUseCase.invoke()
    }

    fun fetchUnreadChatsCount() {
        viewModelScope.launch {
            val currentUserId = getCurrentUserIdUseCase.invoke()
            fetchUnreadChatsCountUseCase.invoke(currentUserId) { unreadCount ->
                _unreadMessagesCount.value = unreadCount
            }
        }
    }

    fun startListeningForUnreadMessages() {
        viewModelScope.launch {
            val currentUserId = getCurrentUserIdUseCase.invoke()
            fetchUnreadChatsCountUseCase.invoke(currentUserId) { unreadCount ->
                _unreadMessagesCount.value = unreadCount // ✅ Update unread count in real-time
            }
        }
    }


}
