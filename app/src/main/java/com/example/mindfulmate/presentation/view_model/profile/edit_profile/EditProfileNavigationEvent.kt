package com.example.mindfulmate.presentation.view_model.profile.edit_profile

sealed interface EditProfileNavigationEvent {
    data object Navigate : EditProfileNavigationEvent
}
