package com.example.mindfulmate.domain.usecase.emergency_contact

import com.example.mindfulmate.domain.model.emergency_contact.UserEmergencyContact
import com.example.mindfulmate.domain.repository.emergency_contact.EmergencyContactRepository
import javax.inject.Inject

class AddUserEmergencyContactUseCase @Inject constructor(
    private val emergencyContactRepository: EmergencyContactRepository
) {
    suspend operator fun invoke(contact: UserEmergencyContact, isUpdate: Boolean = false) {
        if (isUpdate) {
            emergencyContactRepository.updateUserEmergencyContact(contact)
        } else {
            emergencyContactRepository.addUserEmergencyContact(contact)
        }
    }
}
