package com.example.mindfulmate.presentation.ui.screen.community.util

import androidx.annotation.DrawableRes
import com.example.mindfulmate.R

data class CommunityPostParams(
    val postId: String = "",
    val username: String = "",
    val date: String = "",
    val title: String = "",
    val body: String = "",
    val likesCount: String = "",
    val commentsCount: String = "",
    @DrawableRes val profilePictureUrl: Int = R.drawable.ic_profile,
    val onCommentsClick: () -> Unit
)
