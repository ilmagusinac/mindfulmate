package com.example.mindfulmate.domain.usecase.chat

import com.example.mindfulmate.domain.repository.chat.ChatRepository
import javax.inject.Inject

class DeleteChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String) {
        return chatRepository.deleteChat(chatId)
    }
}
