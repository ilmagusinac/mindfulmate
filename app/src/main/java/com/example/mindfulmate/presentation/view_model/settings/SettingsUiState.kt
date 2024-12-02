package com.example.mindfulmate.presentation.view_model.settings

import com.example.mindfulmate.presentation.ui.screen.settings.util.SettingsParams

sealed interface SettingsUiState {
    data object Init : SettingsUiState
    data object Loading : SettingsUiState
    data class Success(
        val isLoggedIn: Boolean = true,
        val settingsParams: SettingsParams = SettingsParams()
    ) : SettingsUiState

    data class Failure(val message: String) : SettingsUiState
}
