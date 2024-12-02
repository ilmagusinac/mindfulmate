package com.example.mindfulmate.domain.usecase.user

import com.example.mindfulmate.domain.repository.user.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke() {
        repository.deleteUser()
    }
}
