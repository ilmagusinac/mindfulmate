package com.example.mindfulmate.domain.usecase.emergency_contact

import com.example.mindfulmate.domain.repository.emergency_contact.EmergencyContactRepository
import javax.inject.Inject

class DeleteUserEmergencyContactUseCase @Inject constructor(
    private val emergencyContactRepository: EmergencyContactRepository
) {
    suspend operator fun invoke(contactId: String) {
        emergencyContactRepository.deleteUserEmergencyContact(contactId)
    }
}