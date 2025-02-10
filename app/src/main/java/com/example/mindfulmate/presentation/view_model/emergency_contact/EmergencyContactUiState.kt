package com.example.mindfulmate.presentation.view_model.emergency_contact

import com.example.mindfulmate.presentation.ui.screen.emergency_contact.util.EmergencyInformation

sealed interface EmergencyContactUiState {
    data object Init : EmergencyContactUiState
    data object Loading : EmergencyContactUiState
    data class Success(
        val globalContacts: List<EmergencyInformation>,
        val userContacts: List<EmergencyInformation>
    ) : EmergencyContactUiState
    data class Failure(val message: String) : EmergencyContactUiState
}
