package com.example.mindfulmate.domain.usecase.chat

import com.example.mindfulmate.domain.model.chat.Chat
import com.example.mindfulmate.domain.repository.chat.ChatRepository
import javax.inject.Inject

class FetchChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): List<Chat> {
        return chatRepository.fetchChats()
    }
}
