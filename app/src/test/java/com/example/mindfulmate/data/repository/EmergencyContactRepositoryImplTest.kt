package com.example.mindfulmate.data.repository

import com.example.mindfulmate.data.service.emergency_contact.EmergencyContactService
import com.example.mindfulmate.data.repository.emergency_contact.EmergencyContactRepositoryImpl
import com.example.mindfulmate.domain.model.emergency_contact.GlobalEmergencyContact
import com.example.mindfulmate.domain.model.emergency_contact.UserEmergencyContact
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class EmergencyContactRepositoryImplTest {

    private lateinit var emergencyContactService: EmergencyContactService
    private lateinit var emergencyContactRepository: EmergencyContactRepositoryImpl

    @Before
    fun setUp() {
        emergencyContactService = mockk()
        emergencyContactRepository = EmergencyContactRepositoryImpl(emergencyContactService)
    }

    @Test
    fun `fetchGlobalEmergencyContacts calls service method and returns result`() = runBlocking {
        // Arrange
        val globalContacts = listOf(GlobalEmergencyContact("1", "Police", "12345"))
        coEvery { emergencyContactService.fetchGlobalEmergencyContacts() } returns globalContacts

        // Act
        val result = emergencyContactRepository.fetchGlobalEmergencyContacts()

        // Assert
        coVerify { emergencyContactService.fetchGlobalEmergencyContacts() }
        assertEquals(globalContacts, result)
    }

    @Test
    fun `addUserEmergencyContact calls service method`() = runBlocking {
        // Arrange
        val userContact = UserEmergencyContact("1", "Friend", "67890")
        coEvery { emergencyContactService.addUserEmergencyContact(userContact) } returns Unit

        // Act
        emergencyContactRepository.addUserEmergencyContact(userContact)

        // Assert
        coVerify { emergencyContactService.addUserEmergencyContact(userContact) }
    }
}
