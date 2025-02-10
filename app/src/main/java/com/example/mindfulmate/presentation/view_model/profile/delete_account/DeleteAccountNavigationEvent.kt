package com.example.mindfulmate.presentation.view_model.profile.delete_account

sealed interface DeleteAccountNavigationEvent {
    data object Navigate : DeleteAccountNavigationEvent
}
