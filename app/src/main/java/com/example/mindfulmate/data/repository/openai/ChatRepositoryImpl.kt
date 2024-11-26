package com.example.mindfulmate.data.repository.openai

import com.example.mindfulmate.data.network.openai.ChatMessage
import com.example.mindfulmate.data.network.openai.ChatRequest
import com.example.mindfulmate.data.network.openai.ChatResponse
import com.example.mindfulmate.data.network.openai.OpenAIService
import com.example.mindfulmate.domain.repository.openai.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val apiService: OpenAIService
) : ChatRepository {

    override suspend fun sendMessage(messages: List<ChatMessage>): ChatResponse? {
        return try {
            val request = ChatRequest(messages = messages)
            val response = apiService.sendMessage(request)
            response.body()
        } catch (e: Exception) {
            null
        }
    }
}
