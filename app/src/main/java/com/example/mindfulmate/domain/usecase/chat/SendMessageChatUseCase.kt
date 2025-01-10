package com.example.mindfulmate.domain.usecase.chat

import com.example.mindfulmate.domain.repository.chat.ChatRepository
import javax.inject.Inject

class SendMessageChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String, message: String): Boolean {
        return chatRepository.sendMessage(chatId, message)
    }
}
