package com.example.mindfulmate.domain.usecase.chat

import com.example.mindfulmate.domain.model.chat.Chat
import com.example.mindfulmate.domain.repository.chat.ChatRepository
import javax.inject.Inject

class FetchUsernameUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chat: Chat): String {
        return chatRepository.getOtherParticipantUsername(chat)
    }
}
