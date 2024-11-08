package com.example.mindfulmate.presentation.view_model.signin

sealed interface SignInUiState {
    data object Init : SignInUiState
    data object Loading : SignInUiState
    data class Success(val isLoggedIn: Boolean) : SignInUiState
    data class Failure(val message: String) : SignInUiState
}