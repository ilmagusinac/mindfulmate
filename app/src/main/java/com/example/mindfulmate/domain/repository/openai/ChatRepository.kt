package com.example.mindfulmate.domain.repository.openai

import com.example.mindfulmate.data.network.openai.ChatMessage
import com.example.mindfulmate.data.network.openai.ChatResponse

interface ChatRepository {
    suspend fun sendMessage(messages: List<ChatMessage>): ChatResponse?
}
