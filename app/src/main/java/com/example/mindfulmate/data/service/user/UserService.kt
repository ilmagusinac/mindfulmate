package com.example.mindfulmate.data.service.user

import com.example.mindfulmate.domain.model.user.User

interface UserService {
    suspend fun addUser(user: User)
    suspend fun getUser(): User
    suspend fun updateUser(user: User)
    suspend fun deleteUser()
}
