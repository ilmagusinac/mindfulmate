package com.example.mindfulmate.data.repository.emergency_contact

import com.example.mindfulmate.data.service.emergency_contact.EmergencyContactService
import com.example.mindfulmate.domain.model.emergency_contact.GlobalEmergencyContact
import com.example.mindfulmate.domain.model.emergency_contact.UserEmergencyContact
import com.example.mindfulmate.domain.repository.emergency_contact.EmergencyContactRepository
import javax.inject.Inject

class EmergencyContactRepositoryImpl @Inject constructor(
    private val emergencyContactService: EmergencyContactService
) : EmergencyContactRepository {

    override suspend fun fetchGlobalEmergencyContacts(): List<GlobalEmergencyContact> =
        emergencyContactService.fetchGlobalEmergencyContacts()

    override suspend fun fetchUserEmergencyContacts(): List<UserEmergencyContact> =
        emergencyContactService.fetchUserEmergencyContacts()

    override suspend fun addUserEmergencyContact(contact: UserEmergencyContact) =
        emergencyContactService.addUserEmergencyContact(contact)

    override suspend fun updateUserEmergencyContact(contact: UserEmergencyContact) =
        emergencyContactService.updateUserEmergencyContact(contact)

    override suspend fun deleteUserEmergencyContact(contactId: String) =
        emergencyContactService.deleteUserEmergencyContact(contactId)
}
