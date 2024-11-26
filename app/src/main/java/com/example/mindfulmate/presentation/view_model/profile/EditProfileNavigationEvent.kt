package com.example.mindfulmate.presentation.view_model.profile

sealed interface EditProfileNavigationEvent {
    data object Navigate : EditProfileNavigationEvent
}