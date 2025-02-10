package com.example.mindfulmate.data.service.chat

import com.example.mindfulmate.domain.model.chat.Chat
import com.example.mindfulmate.domain.model.chat.Message

interface ChatService {
    suspend fun getMessages(chatId: String): List<Message>
    suspend fun sendMessage(chatId: String, message: String): String?
    suspend fun listenForMessages(chatId: String, onUpdate: (List<Message>) -> Unit)
    suspend fun fetchChats(): List<Chat>
    suspend fun getCurrentUserId(): String
    suspend fun createOrGetChat(otherUserId: String): String
    suspend fun deleteChat(chatId: String)
    //suspend fun markMessagesAsRead(chatId: String)
    suspend fun markMessagesAsRead(chatId: String, userId: String)
    suspend fun editMessage(chatId: String, messageId: String, newText: String): Boolean
    suspend fun deleteMessage(chatId: String, messageId: String): Boolean
    suspend fun listenForUnreadMessages(onUpdate: (Int) -> Unit)
    suspend fun fetchUnreadChatsCount(userId: String, onUpdate: (Int) -> Unit)
}
