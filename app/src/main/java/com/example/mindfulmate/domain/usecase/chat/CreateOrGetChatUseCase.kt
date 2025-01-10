package com.example.mindfulmate.domain.usecase.chat

import com.example.mindfulmate.domain.repository.chat.ChatRepository
import javax.inject.Inject

class CreateOrGetChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(otherUserId: String): String {
        return chatRepository.createOrGetChat(otherUserId)
    }
}
