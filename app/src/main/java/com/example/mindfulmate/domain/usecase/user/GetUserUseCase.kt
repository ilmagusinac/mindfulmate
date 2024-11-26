package com.example.mindfulmate.domain.usecase.user

import com.example.mindfulmate.domain.model.user.User
import com.example.mindfulmate.domain.repository.user.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): User {
        return repository.getUser()
    }
}
