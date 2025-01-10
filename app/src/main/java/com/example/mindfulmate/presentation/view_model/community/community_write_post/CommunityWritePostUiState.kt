package com.example.mindfulmate.presentation.view_model.community.community_write_post

sealed interface CommunityWritePostUiState {
    data object Init : CommunityWritePostUiState
    data object Loading : CommunityWritePostUiState
    data object Success : CommunityWritePostUiState
    data class Failure(val message: String) : CommunityWritePostUiState
}
