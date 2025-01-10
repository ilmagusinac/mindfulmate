package com.example.mindfulmate.presentation.view_model.chat.chat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.model.chat.Message
import com.example.mindfulmate.domain.usecase.chat.DeleteChatUseCase
import com.example.mindfulmate.domain.usecase.chat.FetchChatsUseCase
import com.example.mindfulmate.domain.usecase.chat.FetchUsernameUseCase
import com.example.mindfulmate.domain.usecase.chat.GetMessageChatUseCase
import com.example.mindfulmate.domain.usecase.chat.SendMessageChatUseCase
import com.example.mindfulmate.domain.usecase.user.GetUserUseCase
import com.example.mindfulmate.presentation.view_model.daily_checkin.DailyCheckInNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessageChatUseCase: GetMessageChatUseCase,
    private val sendMessageChatUseCase: SendMessageChatUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val fetchChatsUseCase: FetchChatsUseCase,
    private val deleteChatUseCase: DeleteChatUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ChatUiState> = MutableStateFlow(ChatUiState.Init)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _username: MutableStateFlow<String> = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _navigationEvent: Channel<ChatNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private var currentUserId: String? = null

    init {
        viewModelScope.launch {
            try {
                val user = getUserUseCase()
                println(user.id)
                currentUserId = user.id
            } catch (e: Exception) {
                _uiState.update { ChatUiState.Failure(e.localizedMessage ?: "Error fetching user") }
            }
        }
    }

    fun getCurrentUserId(): String? = currentUserId

    fun fetchMessages(chatId: String) {
        viewModelScope.launch {
            _uiState.update { ChatUiState.Loading }
            try {
                val messages = getMessageChatUseCase(chatId)
                _messages.value = messages
                _uiState.update { ChatUiState.Success }
            } catch (e: Exception) {
                _uiState.update { ChatUiState.Failure(e.localizedMessage ?: "Failed to load messages") }
            }
        }
    }

    fun sendMessage(chatId: String, message: String) {
        viewModelScope.launch {
            try {
                val isSuccessful = sendMessageChatUseCase(chatId, message)
                if (isSuccessful) {
                    fetchMessages(chatId)
                } else {
                    _uiState.update { ChatUiState.Failure("Failed to send message") }
                }
            } catch (e: Exception) {
                _uiState.update { ChatUiState.Failure(e.localizedMessage ?: "Failed to send message") }
            }
        }
    }

    fun listenForMessages(chatId: String, onUpdate: (List<Message>) -> Unit) {
        viewModelScope.launch {
            try {
                getMessageChatUseCase.listenToMessages(chatId) { messages ->
                    _messages.value = messages
                    onUpdate(messages)
                }
            } catch (e: Exception) {
                _uiState.update { ChatUiState.Failure(e.localizedMessage ?: "Failed to listen for messages") }
            }
        }
    }

    fun getUsername(chatId: String) {
        viewModelScope.launch {
            try {
                val chats = fetchChatsUseCase.invoke()
                val chat = chats.find { it.chatId == chatId }
                val otherParticipant = chat?.participants?.firstOrNull { it.userId != currentUserId }
                _username.value = otherParticipant?.username ?: "Unknown"
            } catch (e: Exception) {
                _uiState.value = ChatUiState.Failure(e.message ?: "Failed to fetch username.")
            }
        }
    }

    fun deleteChat(chatId: String) {
        viewModelScope.launch {
            try {
                deleteChatUseCase.invoke(chatId)
                _uiState.value = ChatUiState.Success
                triggerNavigation(ChatNavigationEvent.Navigate)
            } catch (e: Exception) {
                _uiState.value = ChatUiState.Failure("Failed to delete chat: ${e.localizedMessage}")
            }
        }
    }

    private fun triggerNavigation(event: ChatNavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }

}
