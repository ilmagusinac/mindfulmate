package com.example.mindfulmate.presentation.ui.screen.chat.util

import androidx.annotation.DrawableRes

data class ChatRow(
    val chatId: String,
    val username: String,
    val lastMessage: String,
    val date: String? = null,
    val time: String? = null,
    val newMessage: Boolean,
    @DrawableRes val profilePicture: Int? = null,
    val isChatClicked: (String) -> Unit
)
