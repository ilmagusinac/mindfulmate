package com.example.mindfulmate.domain.usecase.user

import com.example.mindfulmate.domain.model.user.User
import com.example.mindfulmate.domain.repository.user.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.updateUser(user)
    }
}
