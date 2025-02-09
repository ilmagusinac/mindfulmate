package com.example.mindfulmate.data.repository.chat

import com.example.mindfulmate.data.service.chat.ChatService
import com.example.mindfulmate.domain.model.chat.Chat
import com.example.mindfulmate.domain.model.chat.Message
import com.example.mindfulmate.domain.repository.chat.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatService: ChatService
) : ChatRepository {

    override suspend fun getMessages(chatId: String): List<Message> =
        chatService.getMessages(chatId)

    override suspend fun sendMessage(chatId: String, message: String): String? =
        chatService.sendMessage(chatId, message)

    override suspend fun listenForMessages(chatId: String, onUpdate: (List<Message>) -> Unit) {
        chatService.listenForMessages(chatId, onUpdate)
    }

    override suspend fun fetchChats(): List<Chat> =
        chatService.fetchChats()

    override suspend fun getOtherParticipantUsername(chat: Chat): String {
        val currentUserId = chatService.getCurrentUserId()
        return chat.participants.firstOrNull { it.userId != currentUserId }?.username ?: "Unknown"
    }

    override suspend fun createOrGetChat(otherUserId: String): String =
        chatService.createOrGetChat(otherUserId)

    override suspend fun deleteChat(chatId: String) =
        chatService.deleteChat(chatId)

    override suspend fun getCurrentUserId(): String =
        chatService.getCurrentUserId()

    /*override suspend fun markMessagesAsRead(chatId: String) =
        chatService.markMessagesAsRead(chatId)*/
    override suspend fun markMessagesAsRead(chatId: String, userId: String) =
        chatService.markMessagesAsRead(chatId, userId)

    override suspend fun editMessage(chatId: String, messageId: String, newText: String): Boolean =
        chatService.editMessage(chatId, messageId, newText)

    override suspend fun deleteMessage(chatId: String, messageId: String): Boolean =
        chatService.deleteMessage(chatId, messageId)

    override suspend fun listenForUnreadMessages(onUpdate: (Int) -> Unit) {
        chatService.listenForUnreadMessages(onUpdate)
    }

    override suspend fun fetchUnreadChatsCount(userId: String, onUpdate: (Int) -> Unit) =
        chatService.fetchUnreadChatsCount(userId, onUpdate)
}
