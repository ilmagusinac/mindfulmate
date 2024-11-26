package com.example.mindfulmate.presentation.view_model.openai

import com.example.mindfulmate.presentation.util.MessageModel

sealed interface ChatUiState {
    data object Init : ChatUiState
    data object Loading : ChatUiState
    data class Success(val messages: List<MessageModel>) : ChatUiState
    data class Failure(val errorMessage: String) : ChatUiState
}

