package com.example.mindfulmate.presentation.ui.screen.community.util

import com.example.mindfulmate.R

data class CommunitySectionParams(
    val communityId: String = "",
    val title: String = "",
    val membersCount: String = "",
    val profileImageUrl: String = "",
    val backgroundImageUrl: String = "",
    val onViewCommunityClick: (String) -> Unit = {}
)
