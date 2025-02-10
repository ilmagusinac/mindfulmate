package com.example.mindfulmate.domain.usecase.chat

import com.example.mindfulmate.domain.model.chat.Message
import com.example.mindfulmate.domain.repository.chat.ChatRepository
import javax.inject.Inject

class GetMessageChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String): List<Message> {
        return chatRepository.getMessages(chatId)
    }

    suspend fun listenToMessages(chatId: String, onUpdate: (List<Message>) -> Unit) {
        chatRepository.listenForMessages(chatId, onUpdate)
    }
}
