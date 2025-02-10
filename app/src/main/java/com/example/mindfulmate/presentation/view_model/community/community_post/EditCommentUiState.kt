package com.example.mindfulmate.presentation.view_model.community.community_post

import com.example.mindfulmate.domain.model.community.Post

sealed class EditCommentUiState {
    data object Init : EditCommentUiState()
    data object Loading : EditCommentUiState()
    data class Success(val body: String) : EditCommentUiState()
    data class Failure(val message: String) : EditCommentUiState()
}

