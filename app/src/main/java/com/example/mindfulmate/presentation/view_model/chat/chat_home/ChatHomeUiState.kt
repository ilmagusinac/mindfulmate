package com.example.mindfulmate.presentation.view_model.chat.chat_home

sealed interface ChatHomeUiState {
    data object Init : ChatHomeUiState
    data object Loading : ChatHomeUiState
    data object Success : ChatHomeUiState
    data class Failure(val message: String) : ChatHomeUiState
}
