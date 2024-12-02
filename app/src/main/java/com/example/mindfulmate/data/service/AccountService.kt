package com.example.mindfulmate.data.service

import com.example.mindfulmate.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUser: Flow<User?>
    val currentUserId: String

    fun hasUser(): Boolean
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun deleteAccount()
    suspend fun signInWithGoogle(idToken: String)
    suspend fun linkAccountWithGoogle(idToken: String)
    suspend fun resetPassword(emailAddress: String)
    suspend fun updateEmail(email: String, password: String, newEmail: String)
}
