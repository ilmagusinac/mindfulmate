package com.example.mindfulmate.presentation.view_model.settings

sealed interface SettingsNavigationEvent {
    data object Navigate : SettingsNavigationEvent
}
