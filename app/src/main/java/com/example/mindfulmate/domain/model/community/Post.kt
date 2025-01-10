package com.example.mindfulmate.domain.model.community

data class Post(
    val postId: String = "",
    val username: String = "",
    val date: com.google.firebase.Timestamp? = null,
    val title: String = "",
    val body: String = "",
    val likes: Int = 0,
    val commentsCount: Int = 0
)
