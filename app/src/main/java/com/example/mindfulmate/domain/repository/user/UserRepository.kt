package com.example.mindfulmate.domain.repository.user

import android.net.Uri
import com.example.mindfulmate.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val currentUser: Flow<User?>
    val currentUserId: String

    fun hasUser(): Boolean
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signInWithGoogle(idToken: String)
    suspend fun signOut()
    suspend fun deleteAccount()
    suspend fun resetPassword(emailAddress: String)
    suspend fun addUser(user: User)
    suspend fun getUser(): User
    suspend fun updateUser(user: User)
    suspend fun deleteUser()
    suspend fun updateEmail(email: String, password: String, newEmail: String)
    suspend fun getAllUsers(): List<Pair<String, String>>
    suspend fun uploadProfileImage(imageUri: Uri): String
    suspend fun getUsers(): List<User>
    suspend fun getUserById(userId: String): User
}
