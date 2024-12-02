package com.example.mindfulmate.presentation.view_model.profile

import com.example.mindfulmate.presentation.ui.screen.profile.util.ProfileParams

sealed interface ProfileUiState {
    data object Init : ProfileUiState
    data object Loading : ProfileUiState
    data class Success(
        val profileParams: ProfileParams = ProfileParams()
    ) : ProfileUiState

    data class Failure(val message: String) : ProfileUiState
}
