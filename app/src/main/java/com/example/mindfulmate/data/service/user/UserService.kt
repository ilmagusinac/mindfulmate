package com.example.mindfulmate.data.service.user

import android.net.Uri
import com.example.mindfulmate.domain.model.user.User

interface UserService {
    suspend fun addUser(user: User)
    suspend fun getUser(): User
    suspend fun updateUser(user: User)
    suspend fun deleteUser()
    suspend fun getAllUsers(): List<Pair<String, String>>
    suspend fun uploadProfileImage(imageUri: Uri): String
    suspend fun getUsers(): List<User>
    suspend fun getUserById(userId: String): User
}
