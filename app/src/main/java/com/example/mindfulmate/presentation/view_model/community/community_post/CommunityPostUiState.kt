package com.example.mindfulmate.presentation.view_model.community.community_post

import com.example.mindfulmate.domain.model.community.Post

sealed interface CommunityPostUiState {
    data object Init : CommunityPostUiState
    data object Loading : CommunityPostUiState
    data class Success(val post: Post) : CommunityPostUiState
    data class Failure(val error: String) : CommunityPostUiState
}

