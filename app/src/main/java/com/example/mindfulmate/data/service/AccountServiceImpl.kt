package com.example.mindfulmate.data.service

import android.content.ContentValues.TAG
import android.util.Log
import com.example.mindfulmate.domain.model.user.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountServiceImpl @Inject constructor(
    private val userService: UserService
) : AccountService {

    override val currentUser: Flow<User?>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) })
                }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun signIn(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signInWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        if (Firebase.auth.currentUser == null) {
            Firebase.auth.signInWithCredential(firebaseCredential).await()
        } else {
            Firebase.auth.currentUser!!.linkWithCredential(firebaseCredential).await()
        }

        val currentUser = Firebase.auth.currentUser
            ?: throw Exception("Google sign-in failed, no user logged in")

        val userExists = try {
            userService.getUser() // This will throw an exception if the user doesn't exist
            true
        } catch (e: Exception) {
            false
        }

        // Add the user to Firestore if they don't exist
        if (!userExists) {
            val newUser = User(
                id = currentUser.uid,
                firstName = "", // Default values, can be updated later
                lastName = "",
                username = "user",
                number = "",
                email = currentUser.email ?: throw Exception("Email not available")
            )
            userService.addUser(newUser)
        }
    }

    override suspend fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()

        val currentUser = Firebase.auth.currentUser ?: throw Exception("User creation failed")
        // Create a User object with default values or initial data
        val user = User(
            id = currentUser.uid,
            firstName = "",
            lastName = "",
            username = "user",
            number = "",
            email = currentUser.email ?: throw Exception("Email not available")
        )

        // Add user to Firestore
        userService.addUser(user)
    }

    override suspend fun linkAccountWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.currentUser!!.linkWithCredential(firebaseCredential).await()
            ?: throw Exception("No user to link with Google account")
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun deleteAccount() {
        Firebase.auth.currentUser!!.delete().await()
    }

    override suspend fun resetPassword(emailAddress: String) {
        Firebase.auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }
    }

    override suspend fun updateEmail(newEmail: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
            ?: throw Exception("User not logged in")

        try {
            currentUser.updateEmail(newEmail).await()
        } catch (e: Exception) {
            if (e.message?.contains("requires-recent-login") == true) {
                throw Exception("Re-authentication required. Please log in again.")
            } else {
                throw e
            }
        }
    }

    override suspend fun reauthenticateUser(password: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
            ?: throw Exception("User not logged in")
        val email = currentUser.email ?: throw Exception("Email not found for user")

        val credential = EmailAuthProvider.getCredential(email, password)
        currentUser.reauthenticate(credential).await()
    }


}
