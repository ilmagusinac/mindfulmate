package com.example.mindfulmate.domain.repository.user

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
    suspend fun  resetPassword(emailAddress: String)
}
