package com.example.mindfulmate.presentation.ui.screen.community.util

import com.example.mindfulmate.R

data class CommunitySectionParams(
    val communityId: String = "",
    val title: String = "",
    val membersCount: String = "",
    val imageRes: Int = R.drawable.ic_splash,
    val onViewCommunityClick: (String) -> Unit = {}
)
