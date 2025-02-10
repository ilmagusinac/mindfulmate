package com.example.mindfulmate.presentation.ui.screen.chat.util

import androidx.annotation.DrawableRes

data class ChatRow(
    val chatId: String = "",
    val currentUserId: String = "",
    val username: String = "",
    val lastMessage: String = "",
    val date: String? = null,
    val time: String? = null,
    val newMessage: Boolean = false,
    val profilePicture: String? = null,
    val isChatClicked: (String) -> Unit = {},
    val unreadBy: List<String> = emptyList()
)
