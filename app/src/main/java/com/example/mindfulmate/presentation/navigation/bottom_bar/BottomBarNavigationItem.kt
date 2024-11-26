package com.example.mindfulmate.presentation.navigation.bottom_bar

import androidx.annotation.StringRes

data class BottomBarNavigationItem(
    @StringRes val titleId: Int,
    val iconRes: Int,
    val route: String
)
