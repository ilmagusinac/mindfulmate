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
) : ChatService {

override suspend fun sendMessage(chatId: String, messageText: String): String? {
    val senderId = auth.currentUser?.uid ?: throw Exception("User not logged in")

    return try {
        val chatRef = firestore.collection("chats").document(chatId)

        val chatSnapshot = chatRef.get().await()
        val participants = chatSnapshot.get("participants") as? List<Map<String, String>>

        if (participants == null) {
            throw Exception("Participants not found")
        }

        val recipientIds = participants.mapNotNull { it["userId"] }.filter { it != senderId }

        val documentRef = chatRef.collection("messages")
            .add(
                mapOf(
                    "senderId" to senderId,
                    "text" to messageText,
                    "timestamp" to FieldValue.serverTimestamp(),
                    "isRead" to false
                )
            ).await()

        documentRef.update("id", documentRef.id).await()

        chatRef.update(
            mapOf(
                "lastMessage" to messageText,
                "lastMessageTimestamp" to FieldValue.serverTimestamp(),
                "hasUnreadMessages" to true,
                "unreadBy" to recipientIds
            )
        ).await()

        documentRef.id
    } catch (e: Exception) {
        e.printStackTrace()
        null
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
            val currentUserProfilePicture = currentUserSnapshot.getString("profileImageUrl")
                ?: "No current user profile picture"

            val otherUserSnapshot = firestore.collection("users")
                .document(otherUserId)
                .get()
                .await()
            val otherUsername = otherUserSnapshot.getString("username")
                ?: throw Exception("Username not found for other user")
            val otherUserProfilePicture =
                otherUserSnapshot.getString("profileImageUrl") ?: "no other user profile picture"


            val snapshot = firestore.collection("chats")
                .get()
                .await()

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
                        mapOf(
                            "userId" to currentUserId,
                            "username" to currentUsername,
                            "profilePicture" to currentUserProfilePicture
                        ),
                        mapOf(
                            "userId" to otherUserId,
                            "username" to otherUsername,
                            "profilePicture" to otherUserProfilePicture
                        )
                    ),
                    "lastMessage" to "No messages",
                    "lastMessageTimestamp" to FieldValue.serverTimestamp(),
                    "hasUnreadMessages" to false,
                    "unreadBy" to emptyList<String>()
                )

                val chatRef = firestore.collection("chats").add(newChat).await()
                chatRef.id
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Failed to create or retrieve chat")
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
/*
    override suspend fun markMessagesAsRead(chatId: String) {
        try {
            val unreadMessages = firestore.collection("chats")
                .document(chatId)
                .collection("messages")
                .whereEqualTo("isRead", false)
                .get()
                .await()

            for (document in unreadMessages.documents) {
                document.reference.update("isRead", true).await()
            }

            firestore.collection("chats")
                .document(chatId)
                .update("hasUnreadMessages", false)
                .await()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/
    override suspend fun markMessagesAsRead(chatId: String, userId: String) {
        try {
            val chatRef = firestore.collection("chats").document(chatId)

            // ✅ Remove the user from `unreadBy`
            chatRef.update(
                mapOf(
                    "unreadBy" to FieldValue.arrayRemove(userId)
                )
            ).await()

            // ✅ Check if `unreadBy` is empty, then remove dot indicator
            val chatSnapshot = chatRef.get().await()
            val unreadByList = chatSnapshot.get("unreadBy") as? List<String> ?: emptyList()

            if (unreadByList.isEmpty()) {
                chatRef.update("hasUnreadMessages", false).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override suspend fun editMessage(chatId: String, messageId: String, newText: String): Boolean {
    return try {
        val chatRef = firestore.collection("chats").document(chatId)
        val messagesRef = chatRef.collection("messages")

        messagesRef.document(messageId)
            .update(
                mapOf(
                    "text" to newText,
                    "timestamp" to com.google.firebase.Timestamp.now()
                )
            ).await()

        val latestMessages = messagesRef
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        val latestMessageDoc = latestMessages.documents.firstOrNull()
        if (latestMessageDoc != null && latestMessageDoc.id == messageId) {
            chatRef.update(
                mapOf(
                    "lastMessage" to newText,
                    "lastMessageTimestamp" to com.google.firebase.Timestamp.now()
                )
            ).await()
        }

        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}



    override suspend fun deleteMessage(chatId: String, messageId: String): Boolean {
        return try {
            val chatRef = firestore.collection("chats").document(chatId)
            val messagesRef = chatRef.collection("messages")

            // ✅ Fetch the message to be deleted
            val messageDoc = messagesRef.document(messageId).get().await()
            if (!messageDoc.exists()) {
                throw Exception("Message not found")
            }

            val messageTimestamp = messageDoc.getTimestamp("timestamp")

            // ✅ Delete the message
            messagesRef.document(messageId).delete().await()

            // 🔹 Find the last message after deletion
            val latestMessages = messagesRef
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            if (latestMessages.isEmpty) {
                // 🔥 No more messages left, set "No messages"
                chatRef.update(
                    mapOf(
                        "lastMessage" to "No messages",
                        "lastMessageTimestamp" to FieldValue.serverTimestamp()
                    )
                ).await()
            } else {
                // ✅ Set the message before the deleted one as the last message
                val newLastMessage = latestMessages.documents.first()
                val newText = newLastMessage.getString("text") ?: "No messages"
                val newTimestamp = newLastMessage.getTimestamp("timestamp") ?: com.google.firebase.Timestamp.now()

                chatRef.update(
                    mapOf(
                        "lastMessage" to newText,
                        "lastMessageTimestamp" to newTimestamp
                    )
                ).await()
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun listenForUnreadMessages(onUpdate: (Int) -> Unit) {
        val currentUserId = auth.currentUser?.uid ?: return

        firestore.collection("chats")
            .whereArrayContains("participants.userId", currentUserId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    return@addSnapshotListener
                }

                if (snapshot == null || snapshot.isEmpty) {
                    println("No chats found for user: $currentUserId")
                    onUpdate(0)
                    return@addSnapshotListener
                }

                var unreadChatsCount = 0

                snapshot.documents.forEach { chatDoc ->
                    val chatId = chatDoc.id
                    val hasUnreadMessages = chatDoc.getBoolean("hasUnreadMessages") ?: false

                    println("Chat ID: $chatId | hasUnreadMessages: $hasUnreadMessages")

                    if (hasUnreadMessages) {
                        unreadChatsCount++
                    }
                }

                println("Total Unread Chats: $unreadChatsCount")
                onUpdate(unreadChatsCount)
            }
    }

    override suspend fun fetchUnreadChatsCount(userId: String, onUpdate: (Int) -> Unit) {
        firestore.collection("chats")
            .whereArrayContains("unreadBy", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    return@addSnapshotListener
                }

                val unreadCount = snapshot?.documents?.size ?: 0
                onUpdate(unreadCount) // ✅ Update the UI with the unread chat count
            }
    }

}
