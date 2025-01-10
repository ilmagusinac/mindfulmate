package com.example.mindfulmate.domain.model.chat

data class Message(
    val id: String = "",
    val senderId: String = "",
    val text: String = "",
    val timestamp: com.google.firebase.Timestamp? = null,
    val isRead: Boolean = false
)
