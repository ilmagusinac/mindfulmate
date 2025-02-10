package com.example.mindfulmate.domain.usecase.emergency_contact

import com.example.mindfulmate.domain.model.emergency_contact.UserEmergencyContact
import com.example.mindfulmate.domain.repository.emergency_contact.EmergencyContactRepository
import javax.inject.Inject

class UpdateUserEmergencyContactUseCase @Inject constructor(
    private val emergencyContactRepository: EmergencyContactRepository
) {
    suspend operator fun invoke(contact: UserEmergencyContact) {
        emergencyContactRepository.updateUserEmergencyContact(contact)
    }
}
