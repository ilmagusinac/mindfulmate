package com.example.mindfulmate.presentation.view_model.signin

sealed interface SignInNavigationEvent {
    data object Navigate : SignInNavigationEvent
    data object NavigateBack : SignInNavigationEvent
}