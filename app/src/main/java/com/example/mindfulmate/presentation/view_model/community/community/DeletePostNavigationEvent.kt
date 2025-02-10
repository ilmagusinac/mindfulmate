package com.example.mindfulmate.presentation.view_model.community.community

sealed interface DeletePostNavigationEvent {
    data object Navigate : DeletePostNavigationEvent
}
