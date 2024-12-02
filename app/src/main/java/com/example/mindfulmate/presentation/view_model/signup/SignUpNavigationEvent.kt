package com.example.mindfulmate.presentation.view_model.signup

sealed interface SignUpNavigationEvent {
    data object Navigate : SignUpNavigationEvent
}
