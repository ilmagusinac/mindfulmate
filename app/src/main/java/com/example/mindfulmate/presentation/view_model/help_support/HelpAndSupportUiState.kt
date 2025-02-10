package com.example.mindfulmate.presentation.view_model.help_support

import com.example.mindfulmate.presentation.ui.screen.help_support.util.HelpAndSupportParams

sealed interface HelpAndSupportUiState {
    data object Init : HelpAndSupportUiState
    data object Loading : HelpAndSupportUiState
    data class Success(val faq: List<HelpAndSupportParams> = emptyList()) : HelpAndSupportUiState
    data class Failure(val message: String) : HelpAndSupportUiState
}
