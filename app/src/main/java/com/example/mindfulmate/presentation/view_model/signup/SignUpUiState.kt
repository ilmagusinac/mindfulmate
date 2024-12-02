package com.example.mindfulmate.presentation.view_model.signup

sealed interface SignUpUiState {
    data object Init : SignUpUiState
    data object Loading : SignUpUiState
    data class Success(val isLoggedIn: Boolean) : SignUpUiState
    data class Failure(val message: String) : SignUpUiState
}
