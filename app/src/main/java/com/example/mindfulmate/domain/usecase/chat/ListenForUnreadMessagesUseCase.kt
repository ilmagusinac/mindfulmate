package com.example.mindfulmate.domain.usecase.chat

import com.example.mindfulmate.domain.repository.chat.ChatRepository
import javax.inject.Inject

class ListenForUnreadMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke( onUpdate: (Int) -> Unit) {
        return chatRepository.listenForUnreadMessages(onUpdate)
    }
}
