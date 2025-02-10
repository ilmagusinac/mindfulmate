package com.example.mindfulmate.data.service.user

import android.net.Uri
import com.example.mindfulmate.domain.model.user.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UserServiceImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage
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

    /*override suspend fun updateUser(user: User) {
        val currentUserUid = auth.currentUser?.uid ?: throw Exception("User not logged in")
        usersCollection.document(currentUserUid).set(user).await()
    }*/
    override suspend fun updateUser(user: User) {
        val currentUserUid = auth.currentUser?.uid ?: throw Exception("User not logged in")

        // 🔹 Use merge() to keep existing fields (e.g., myCommunities)
        usersCollection.document(currentUserUid)
            .set(user, SetOptions.merge()) // 🔹 This prevents removing existing fields
            .await()
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

    override suspend fun getUsers(): List<User> {
        val currentUserId = auth.currentUser?.uid ?: throw Exception("User not logged in")

        return try {
            val snapshot = firestore.collection("users").get().await()
            snapshot.documents.mapNotNull { document ->
                val user = document.toObject(User::class.java)?.copy(id = document.id)
                if (user != null && user.id != currentUserId) user else null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


    override suspend fun uploadProfileImage(imageUri: Uri): String {
        val currentUserUid = auth.currentUser?.uid ?: throw Exception("User not logged in")
        val fileRef = storage.reference.child("profileImages/$currentUserUid.jpg")
        fileRef.putFile(imageUri).await()
        val downloadUrl = fileRef.downloadUrl.await().toString()

        // Update Firestore with new profile image URL
        usersCollection.document(currentUserUid)
            .update("profileImageUrl", downloadUrl)
            .await()

        return downloadUrl
    }

    override suspend fun getUserById(userId: String): User {
        return try {
            val documentSnapshot = firestore.collection("users")
                .document(userId)
                .get()
                .await()

            documentSnapshot.toObject(User::class.java) ?: throw Exception("User not found")
        } catch (e: Exception) {
            throw Exception("Error fetching user: ${e.message}")
        }
    }
}
