package com.example.mindfulmate.presentation.view_model.community.community_post

sealed interface CommunityPostNavigationEvent {
    data object Navigate : CommunityPostNavigationEvent
}
