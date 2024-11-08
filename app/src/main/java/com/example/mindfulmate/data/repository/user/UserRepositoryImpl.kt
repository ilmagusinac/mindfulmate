package com.example.mindfulmate.data.repository.user

import com.example.mindfulmate.data.service.AccountService
import com.example.mindfulmate.domain.model.user.User
import com.example.mindfulmate.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val accountService: AccountService
) : UserRepository {

    override val currentUser: Flow<User?> get() = accountService.currentUser
    override val currentUserId: String get() = accountService.currentUserId

    override fun hasUser(): Boolean = accountService.hasUser()
    override suspend fun signIn(email: String, password: String) = accountService.signIn(email, password)
    override suspend fun signUp(email: String, password: String) = accountService.signUp(email, password)
    override suspend fun signInWithGoogle(idToken: String) = accountService.signInWithGoogle(idToken)
    override suspend fun signOut() = accountService.signOut()
    override suspend fun deleteAccount() = accountService.deleteAccount()
    override suspend fun resetPassword(emailAddress: String) = accountService.resetPassword(emailAddress)
}
