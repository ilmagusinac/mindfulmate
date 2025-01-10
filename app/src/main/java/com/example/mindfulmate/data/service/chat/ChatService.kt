package com.example.mindfulmate.data.service.chat

import com.example.mindfulmate.domain.model.chat.Chat
import com.example.mindfulmate.domain.model.chat.Message

interface ChatService {
    suspend fun getMessages(chatId: String): List<Message>
    suspend fun sendMessage(chatId: String, message: String): Boolean
    suspend fun listenForMessages(chatId: String, onUpdate: (List<Message>) -> Unit)
    suspend fun fetchChats(): List<Chat>
    suspend fun getCurrentUserId(): String
    suspend fun createOrGetChat(otherUserId: String): String
    suspend fun deleteChat(chatId: String)
}
