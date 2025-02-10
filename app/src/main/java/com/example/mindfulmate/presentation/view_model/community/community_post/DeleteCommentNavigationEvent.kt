package com.example.mindfulmate.presentation.view_model.community.community_post

sealed interface DeleteCommentNavigationEvent {
    data object Navigate : DeleteCommentNavigationEvent
}
