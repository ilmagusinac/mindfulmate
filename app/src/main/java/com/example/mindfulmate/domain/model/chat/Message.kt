package com.example.mindfulmate.domain.model.chat

import com.google.firebase.firestore.PropertyName

data class Message(
    val id: String = "",
    val senderId: String = "",
    val text: String = "",
    val timestamp: com.google.firebase.Timestamp? = null,
    @get:PropertyName("isRead")
    @set:PropertyName("isRead")
    var isRead: Boolean = false
)
