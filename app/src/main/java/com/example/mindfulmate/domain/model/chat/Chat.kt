package com.example.mindfulmate.domain.model.chat

import com.google.firebase.Timestamp

data class Participant(
    val userId: String = "",
    val username: String = "",
    val profilePicture: String = ""
)

data class Chat(
    val chatId: String = "",
    val participants: List<Participant> = emptyList(),
    val lastMessage: String = "",
    val lastMessageTimestamp: Timestamp? = null,
    val hasUnreadMessages: Boolean = false,
    val unreadBy: List<String> = emptyList()
)
