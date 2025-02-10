package com.example.mindfulmate.presentation.view_model.community.community

sealed class DeletePostUiState {
    data object Init : DeletePostUiState()
    data object Loading : DeletePostUiState()
    data object Success : DeletePostUiState()
    data class Failure(val errorMessage: String) : DeletePostUiState()
}

