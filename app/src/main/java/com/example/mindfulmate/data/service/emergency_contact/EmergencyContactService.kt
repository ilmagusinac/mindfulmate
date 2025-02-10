package com.example.mindfulmate.data.service.emergency_contact

import com.example.mindfulmate.domain.model.emergency_contact.GlobalEmergencyContact
import com.example.mindfulmate.domain.model.emergency_contact.UserEmergencyContact

interface EmergencyContactService {
    suspend fun fetchGlobalEmergencyContacts(): List<GlobalEmergencyContact>
    suspend fun fetchUserEmergencyContacts(): List<UserEmergencyContact>
    suspend fun addUserEmergencyContact(contact: UserEmergencyContact)
    suspend fun updateUserEmergencyContact(contact: UserEmergencyContact)
    suspend fun deleteUserEmergencyContact(contactId: String)
}
