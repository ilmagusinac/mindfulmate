package com.example.mindfulmate.domain.usecase.user

import com.example.mindfulmate.domain.repository.user.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): List<Pair<String, String>> {
        return userRepository.getAllUsers()
    }
}
