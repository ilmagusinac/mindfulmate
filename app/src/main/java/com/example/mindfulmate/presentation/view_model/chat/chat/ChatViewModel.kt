package com.example.mindfulmate.presentation.view_model.chat.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.model.chat.Message
import com.example.mindfulmate.domain.usecase.chat.DeleteChatUseCase
import com.example.mindfulmate.domain.usecase.chat.DeleteMessageUseCase
import com.example.mindfulmate.domain.usecase.chat.EditMessageUseCase
import com.example.mindfulmate.domain.usecase.chat.FetchChatsUseCase
import com.example.mindfulmate.domain.usecase.chat.GetMessageChatUseCase
import com.example.mindfulmate.domain.usecase.chat.SendMessageChatUseCase
import com.example.mindfulmate.domain.usecase.chat.GetCurrentUserIdUseCase
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
    private val fetchChatsUseCase: FetchChatsUseCase,
    private val deleteChatUseCase: DeleteChatUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val editMessageUseCase: EditMessageUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ChatUiState> = MutableStateFlow(ChatUiState.Init)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _username: MutableStateFlow<String> = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _profileImage: MutableStateFlow<String> = MutableStateFlow("")
    val profileImage: StateFlow<String> = _profileImage.asStateFlow()

    private val _navigationEvent: Channel<ChatNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId

    private val _editingMessageId = MutableStateFlow<String?>(null)
    val editingMessageId: StateFlow<String?> = _editingMessageId

    private val _messageInput = MutableStateFlow("")
    val messageInput: StateFlow<String> = _messageInput

    private val _isDeleteChatPopupVisible = MutableStateFlow(false)
    val isDeleteChatPopupVisible: StateFlow<Boolean> = _isDeleteChatPopupVisible.asStateFlow()

    private val _toastMessage = Channel<String>(Channel.CONFLATED)
    val toastMessage = _toastMessage.receiveAsFlow()

    init {
        viewModelScope.launch {
            try {
                _currentUserId.value = getCurrentUserIdUseCase.invoke()
            } catch (e: Exception) {
                _uiState.update { ChatUiState.Failure(e.localizedMessage ?: "Error fetching user") }
            }
        }
    }

    suspend fun getCurrentUserId(): String {
        val userId = getCurrentUserIdUseCase.invoke()
        return userId
    }

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

    fun sendMessage(chatId: String, messageText: String) {
        viewModelScope.launch {
            try {
                val messageId = sendMessageChatUseCase(chatId, messageText)

                if (messageId != null) {
                    _messageInput.value = ""
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
                val otherParticipant = chat?.participants?.firstOrNull { it.userId != currentUserId.value }
                _username.value = otherParticipant?.username ?: "Unknown"
            } catch (e: Exception) {
                _uiState.value = ChatUiState.Failure(e.message ?: "Failed to fetch username.")
            }
        }
    }

    fun getProfileImage(chatId: String) {
        viewModelScope.launch {
            try {
                val chats = fetchChatsUseCase.invoke()
                val chat = chats.find { it.chatId == chatId }
                val otherParticipant = chat?.participants?.firstOrNull { it.userId != currentUserId.value }
                _profileImage.value = otherParticipant?.profilePicture ?: "Unknown"
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
                hideDeleteChatPopup()
                _toastMessage.send("Chat Deleted")
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

    fun updateMessageInput(newInput: String) {
        _messageInput.value = newInput
    }

    fun startEditingMessage(message: Message) {
        _editingMessageId.value = message.id
        _messageInput.value = message.text
    }

    fun cancelEditing() {
        _editingMessageId.value = null
        _messageInput.value = ""
    }

    fun deleteMessage(chatId: String, messageId: String) {
        viewModelScope.launch {
            val result = deleteMessageUseCase.invoke(chatId, messageId)
            if (result) {
                _messages.update { currentMessages ->
                    currentMessages.filterNot { it.id == messageId }
                }
                _toastMessage.send("Message deleted")
            } else {
                _uiState.update { ChatUiState.Failure("Failed to delete the message.") }
            }
        }
    }

    fun updateMessage(chatId: String, messageId: String, newText: String) {
        viewModelScope.launch {
            val result = editMessageUseCase.invoke(chatId, messageId, newText)
            if (result) {
                _messages.update { currentMessages ->
                    currentMessages.map {
                        if (it.id == messageId) it.copy(text = newText) else it
                    }
                }
                _messageInput.value = ""
            } else {
                _uiState.update { ChatUiState.Failure("Failed to edit the message.") }
            }
        }
    }

    fun showDeleteChatPopup() {
        _isDeleteChatPopupVisible.value = true
    }

    fun hideDeleteChatPopup() {
        _isDeleteChatPopupVisible.value = false
    }
}
