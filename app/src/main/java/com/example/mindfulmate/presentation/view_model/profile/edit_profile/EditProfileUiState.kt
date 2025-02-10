package com.example.mindfulmate.presentation.view_model.profile.edit_profile

import com.example.mindfulmate.presentation.ui.screen.profile.util.EditProfileParams

sealed interface EditProfileUiState {
    data object Init : EditProfileUiState
    data object Loading : EditProfileUiState
    data class Success(
        val editProfileParams: EditProfileParams = EditProfileParams()
    ) : EditProfileUiState

    data class Failure(val message: String) : EditProfileUiState
}
