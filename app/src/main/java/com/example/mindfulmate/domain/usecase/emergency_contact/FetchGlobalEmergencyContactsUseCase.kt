package com.example.mindfulmate.domain.usecase.emergency_contact

import com.example.mindfulmate.domain.model.emergency_contact.GlobalEmergencyContact
import com.example.mindfulmate.domain.repository.emergency_contact.EmergencyContactRepository
import javax.inject.Inject

class FetchGlobalEmergencyContactsUseCase @Inject constructor(
    private val emergencyContactRepository: EmergencyContactRepository
) {
    suspend operator fun invoke(): List<GlobalEmergencyContact> {
        return emergencyContactRepository.fetchGlobalEmergencyContacts()
    }
}
