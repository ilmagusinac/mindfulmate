package com.example.mindfulmate.data.service.user

import com.example.mindfulmate.domain.model.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserServiceImpl(
    firestore: FirebaseFirestore,
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
}
