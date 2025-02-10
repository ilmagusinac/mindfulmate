package com.example.mindfulmate.domain.usecase.user

import com.example.mindfulmate.domain.model.user.User
import com.example.mindfulmate.domain.repository.user.UserRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): List<User> {
        return userRepository.getUsers()
    }
}