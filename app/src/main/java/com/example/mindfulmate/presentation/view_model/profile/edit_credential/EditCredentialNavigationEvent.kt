package com.example.mindfulmate.presentation.view_model.profile.edit_credential

sealed interface EditCredentialNavigationEvent {
    data object Navigate : EditCredentialNavigationEvent
    data object NavigateDeleted: EditCredentialNavigationEvent

}
