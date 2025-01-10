package com.example.mindfulmate.presentation.view_model.community.community_write_post

sealed interface CommunityWritePostNavigationEvent {
    data object Navigate : CommunityWritePostNavigationEvent
}
