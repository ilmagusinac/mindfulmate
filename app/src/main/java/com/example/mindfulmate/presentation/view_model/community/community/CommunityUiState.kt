package com.example.mindfulmate.presentation.view_model.community.community

import com.example.mindfulmate.domain.model.community.Community

sealed interface CommunityUiState {
    data object Init : CommunityUiState
    data object Loading : CommunityUiState
    data class Success(val community: Community) : CommunityUiState
    data class Failure(val message: String) : CommunityUiState
}

