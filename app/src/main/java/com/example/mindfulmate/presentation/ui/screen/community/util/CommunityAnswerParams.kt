package com.example.mindfulmate.presentation.ui.screen.community.util

import androidx.annotation.DrawableRes
import com.example.mindfulmate.R

data class CommunityAnswerParams(
    val username: String = "",
    val comment: String = "",
    @DrawableRes val placeholderRes: Int = R.drawable.ic_profile
)
