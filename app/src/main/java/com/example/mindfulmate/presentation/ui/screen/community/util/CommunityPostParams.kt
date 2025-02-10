package com.example.mindfulmate.presentation.ui.screen.community.util

data class CommunityPostParams(
    val postId: String = "",
    val username: String = "",
    val date: String = "",
    val title: String = "",
    val body: String = "",
    val likesCount: String = "",
    val commentsCount: String = "",
    val isOwner: Boolean = false,
    val profilePictureUrl: String? = null,
    val onCommentsClick: () -> Unit,
    val onEditClick: () -> Unit,
    val onDeleteClick: () -> Unit,
    val onUserClick: () -> Unit
)
