package com.example.mindfulmate.presentation.view_model.openai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.presentation.util.MessageModel
import com.example.mindfulmate.domain.usecase.openai.ProcessMessageUseCase
import com.example.mindfulmate.domain.usecase.user.GetUserUseCase
import com.example.mindfulmate.presentation.ui.screen.profile.util.ProfileParams
import com.example.mindfulmate.presentation.view_model.profile.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val processMessageUseCase: ProcessMessageUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages: StateFlow<List<MessageModel>> = _messages.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username.asStateFlow()
/*
    init {
        loadUser()
    }*/

    fun sendMessage(userMessage: String) {
        if (userMessage.isBlank()) return

        _messages.value += MessageModel(userMessage, "user")

        viewModelScope.launch {
            try {
                val responses = processMessageUseCase.execute(userMessage)

                _messages.value += responses
            } catch (e: Exception) {
                _messages.value += MessageModel(
                    "An error occurred: ${e.localizedMessage}", "assistant"
                )
            }
        }
    }

    fun loadUser() {
        viewModelScope.launch {
            try {
                val currentUser = getUserUseCase()
                _username.value = currentUser.username
            } catch (e: Exception) {
                _username.value = "Unknown User"
            }
        }
    }
}
