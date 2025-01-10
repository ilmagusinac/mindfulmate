package com.example.mindfulmate.data.service.chat

import com.example.mindfulmate.domain.model.chat.Chat
import com.example.mindfulmate.domain.model.chat.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): ChatService {

    override suspend fun sendMessage(chatId: String, message: String): Boolean {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        val messageData = mapOf(
            "senderId" to userId,
            "text" to message,
            "timestamp" to FieldValue.serverTimestamp(),
            "isRead" to false
        )

        try {
            firestore.collection("chats")
                .document(chatId)
                .collection("messages")
                .add(messageData)
                .await()

            firestore.collection("chats")
                .document(chatId)
                .update(
                    mapOf(
                        "lastMessage" to message,
                        "lastMessageTimestamp" to FieldValue.serverTimestamp()
                    )
                ).await()

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    override suspend fun getMessages(chatId: String): List<Message> {
        try {
            val snapshot = firestore.collection("chats")
                .document(chatId)
                .collection("messages")
                .orderBy("timestamp")
                .get()
                .await()

            return snapshot.documents.mapNotNull { document ->
                document.toObject(Message::class.java)?.copy(id = document.id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    override suspend fun listenForMessages(chatId: String, onUpdate: (List<Message>) -> Unit) {
        firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    e.printStackTrace()
                    return@addSnapshotListener
                }
                val messages = snapshot?.documents?.mapNotNull { it.toObject(Message::class.java) }
                onUpdate(messages.orEmpty())
            }
    }

    override suspend fun fetchChats(): List<Chat> {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        return try {
            val snapshot = firestore.collection("chats")
                .get()
                .await()

            snapshot.documents.mapNotNull { document ->
                val chat = document.toObject(Chat::class.java)?.copy(chatId = document.id)
                if (chat?.participants?.any { it.userId == userId } == true) {
                    chat
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: throw Exception("User not logged in")
    }

    override suspend fun createOrGetChat(otherUserId: String): String {
        val currentUserId = auth.currentUser?.uid ?: throw Exception("User not logged in")

        return try {
            val currentUserSnapshot = firestore.collection("users")
                .document(currentUserId)
                .get()
                .await()
            val currentUsername = currentUserSnapshot.getString("username")
                ?: throw Exception("Username not found for current user")

            val otherUserSnapshot = firestore.collection("users")
                .document(otherUserId)
                .get()
                .await()
            val otherUsername = otherUserSnapshot.getString("username")
                ?: throw Exception("Username not found for other user")

            val snapshot = firestore.collection("chats")
                .get()
                .await()

            snapshot.documents.forEach { document ->
                val participants = document.get("participants") as? List<Map<String, String>>
            }

            // Check manually if there's a chat with both participants
            val existingChat = snapshot.documents.firstOrNull { document ->
                val participants = document.get("participants") as? List<Map<String, String>>
                participants != null &&
                        participants.any { it["userId"] == currentUserId } &&
                        participants.any { it["userId"] == otherUserId }
            }

            if (existingChat != null) {
                existingChat.id
            } else {
                val newChat = mapOf(
                    "participants" to listOf(
                        mapOf("userId" to currentUserId, "username" to currentUsername),
                        mapOf("userId" to otherUserId, "username" to otherUsername)
                    ),
                    "lastMessage" to "",
                    "lastMessageTimestamp" to FieldValue.serverTimestamp(),
                    "hasUnreadMessages" to false
                )

                val chatRef = firestore.collection("chats").add(newChat).await()
                chatRef.id
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to create or retrieve chat")
        }
    }

    private suspend fun getOtherUsername(userId: String): String {
        return try {
            val userDoc = firestore.collection("users").document(userId).get().await()
            userDoc.getString("username") ?: "Unknown"
        } catch (e: Exception) {
            e.printStackTrace()
            "Unknown"
        }
    }

    override suspend fun deleteChat(chatId: String) {
        val currentUserId = auth.currentUser?.uid ?: throw Exception("User not logged in")

        try {
            val chatSnapshot = firestore.collection("chats")
                .document(chatId)
                .get()
                .await()

            if (!chatSnapshot.exists()) {
                throw Exception("Chat not found")
            }

            val participants = chatSnapshot.get("participants") as? List<Map<String, String>>

            val isParticipant = participants?.any { it["userId"] == currentUserId } == true
            if (!isParticipant) {
                throw Exception("You are not a participant of this chat")
            }

            firestore.collection("chats")
                .document(chatId)
                .delete()
                .await()

        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to delete chat: ${e.message}")
        }
    }
}
