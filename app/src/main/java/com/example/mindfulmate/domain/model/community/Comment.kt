package com.example.mindfulmate.domain.model.community

data class Comment(
    val userId: String = "",
    val commentId: String = "",
    val comment: String = "",
    val username: String = "",
    val profilePicture: String = "",
    val timestamp: com.google.firebase.Timestamp? = null,
)
