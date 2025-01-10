package com.example.mindfulmate.presentation.view_model.community.community_home

sealed interface CommunityHomeUiState {
    data object Init : CommunityHomeUiState
    data object Loading : CommunityHomeUiState
    data object Success : CommunityHomeUiState
    data class Failure(val message: String) : CommunityHomeUiState
}
