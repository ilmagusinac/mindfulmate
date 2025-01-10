package com.example.mindfulmate.presentation.ui.screen.community.util

import com.example.mindfulmate.R

data class TopCommunityProfile(
    val imageRes: Int = R.drawable.ic_community,
    val name: String = "",
    val onCommunityClick: () -> Unit
)
