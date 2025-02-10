package com.example.mindfulmate.domain.usecase.chat

import com.example.mindfulmate.domain.repository.chat.ChatRepository
import javax.inject.Inject

class FetchUnreadChatsCountUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(userId: String, onUpdate: (Int) -> Unit)  {
        return chatRepository.fetchUnreadChatsCount(userId, onUpdate)
    }
}
