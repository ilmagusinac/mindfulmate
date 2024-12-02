package com.example.mindfulmate.presentation.view_model.profile.edit_credential

sealed interface EditCredentialUiState {
    data object Init : EditCredentialUiState
    data object Loading : EditCredentialUiState
    data object Success : EditCredentialUiState
    data class Failure(val message: String) : EditCredentialUiState
}
