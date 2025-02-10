package com.example.mindfulmate.presentation.view_model.chat.chat

sealed interface ChatNavigationEvent {
    data object Navigate : ChatNavigationEvent
}
