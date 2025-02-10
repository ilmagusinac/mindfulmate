package com.example.mindfulmate.presentation.view_model.community.community_post

import com.example.mindfulmate.domain.model.community.Post

sealed interface DeleteCommentUiState {
    data object Init : DeleteCommentUiState
    data object Loading : DeleteCommentUiState
    data object Success : DeleteCommentUiState
    data class Failure(val error: String) : DeleteCommentUiState
}