package com.example.mindfulmate.data.service.emergency_contact

import com.example.mindfulmate.domain.model.emergency_contact.GlobalEmergencyContact
import com.example.mindfulmate.domain.model.emergency_contact.UserEmergencyContact
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EmergencyContactServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : EmergencyContactService {

    override suspend fun fetchGlobalEmergencyContacts(): List<GlobalEmergencyContact> {
        return try {
            val snapshot = FirebaseFirestore.getInstance()
                .collection("emergency_contacts")
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(GlobalEmergencyContact::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun fetchUserEmergencyContacts(): List<UserEmergencyContact> {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        return try {
            val snapshot = FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .collection("emergency_contacts")
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(UserEmergencyContact::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addUserEmergencyContact(contact: UserEmergencyContact) {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")
        val docRef = firestore.collection("users")
            .document(userId)
            .collection("emergency_contacts")
            .document()

        val contactWithId = contact.copy(id = docRef.id)

        docRef.set(contactWithId).await()
    }

    override suspend fun updateUserEmergencyContact(contact: UserEmergencyContact) {
        val userId = auth.currentUser?.uid ?: throw Exception("User not logged in")

        if (contact.id.isEmpty()) {
            throw Exception("Contact ID is missing. Cannot update.")
        }

        firestore.collection("users")
            .document(userId)
            .collection("emergency_contacts")
            .document(contact.id)
            .set(contact)
            .await()
    }

    override suspend fun deleteUserEmergencyContact(contactId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: throw Exception("User not logged in")
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("emergency_contacts")
            .document(contactId)
            .delete()
            .await()
    }
}
