package com.example.mindfulmate.presentation.view_model.profile.delete_account

sealed interface DeleteAccountUiState {
    data object Init : DeleteAccountUiState
    data object Loading : DeleteAccountUiState
    data object Success : DeleteAccountUiState
    data class Failure(val message: String) : DeleteAccountUiState
}
