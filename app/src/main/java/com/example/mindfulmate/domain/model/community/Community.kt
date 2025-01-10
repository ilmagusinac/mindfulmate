package com.example.mindfulmate.domain.model.community

data class Community(
    val id: String = "",
    val communityName: String = "",
    val profilePicture: String = "",
    val backgroundPicture: String = "",
    val membersCount: Int = 0,
    val description: String = ""
)
