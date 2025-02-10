package com.example.mindfulmate.presentation.view_model.chat.chat

sealed interface ChatUiState {
    data object Init : ChatUiState
    data object Loading : ChatUiState
    data object Success : ChatUiState
    data class Failure(val message: String) : ChatUiState
}
