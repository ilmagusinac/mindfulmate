package com.example.mindfulmate.domain.usecase.openai

import com.example.mindfulmate.data.network.openai.ChatMessage
import com.example.mindfulmate.domain.repository.openai.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(messages: List<ChatMessage>) = repository.sendMessage(messages)
}
