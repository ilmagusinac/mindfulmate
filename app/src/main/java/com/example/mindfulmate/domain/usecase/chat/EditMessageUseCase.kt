package com.example.mindfulmate.domain.usecase.chat

import com.example.mindfulmate.domain.repository.chat.ChatRepository
import javax.inject.Inject

class EditMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String, messageId: String, newText: String): Boolean {
        return chatRepository.editMessage(chatId, messageId, newText)
    }
}