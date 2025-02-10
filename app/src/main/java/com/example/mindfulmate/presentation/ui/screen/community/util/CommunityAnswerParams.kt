package com.example.mindfulmate.presentation.ui.screen.community.util

import androidx.annotation.DrawableRes
import com.example.mindfulmate.R

data class CommunityAnswerParams(
    val username: String = "",
    val comment: String = "",
    val isOwner: Boolean = false,
    val placeholderRes: String? = null,
    val onEditClick: () -> Unit,
    val onDeleteClick: () -> Unit,
    val onUserClick: () -> Unit
)
