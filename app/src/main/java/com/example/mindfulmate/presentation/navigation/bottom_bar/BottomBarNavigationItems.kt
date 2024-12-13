package com.example.mindfulmate.presentation.navigation.bottom_bar

import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.navigation.Screen

object BottomBarNavigationItems {
    val items = listOf(
        BottomBarNavigationItem(
            titleId = R.string.home,
            iconRes = R.drawable.ic_home,
            route = Screen.Home.route
        ),
        BottomBarNavigationItem(
            titleId = R.string.community,
            iconRes = R.drawable.ic_community,
            route = Screen.Community.route
        ),
        BottomBarNavigationItem(
            titleId = R.string.chat,
            iconRes = R.drawable.ic_chat,
            route = Screen.Chat.route
        ),
        BottomBarNavigationItem(
            titleId = R.string.profile,
            iconRes = R.drawable.ic_profile,
            route = Screen.Settings.route
        )
    )
}
