package com.example.mindfulmate.data.service.user

import com.example.mindfulmate.domain.model.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserServiceImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : UserService {

    private val usersCollection = firestore.collection("users")

    override suspend fun addUser(user: User) {
        val currentUserUid = auth.currentUser?.uid ?: throw Exception("User not logged in")
        usersCollection.document(currentUserUid).set(user).await()
    }

    override suspend fun getUser(): User {
        val currentUserUid = auth.currentUser?.uid ?: throw Exception("User not logged in")
        val snapshot = usersCollection.document(currentUserUid).get().await()
        return snapshot.toObject(User::class.java) ?: throw Exception("User not found")
    }

    override suspend fun updateUser(user: User) {
        val currentUserUid = auth.currentUser?.uid ?: throw Exception("User not logged in")
        usersCollection.document(currentUserUid).set(user).await()
    }

    override suspend fun deleteUser() {
        val currentUserUid = auth.currentUser?.uid ?: throw Exception("User not logged in")
        usersCollection.document(currentUserUid).delete().await()
    }

    override suspend fun getAllUsers(): List<Pair<String, String>> {
        val currentUserId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        return try {
            val snapshot = firestore.collection("users").get().await()
            snapshot.documents.mapNotNull { document ->
                val userId = document.id
                val username = document.getString("username")
                if (userId != currentUserId && username != null) userId to username else null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
