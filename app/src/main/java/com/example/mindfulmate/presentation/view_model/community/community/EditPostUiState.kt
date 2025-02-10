package com.example.mindfulmate.presentation.view_model.community.community

sealed class EditPostUiState {
    data object Loading : EditPostUiState()
    data class Success(val title: String, val body: String) : EditPostUiState()
    data class Failure(val errorMessage: String) : EditPostUiState()
    data object Init : EditPostUiState()
}
