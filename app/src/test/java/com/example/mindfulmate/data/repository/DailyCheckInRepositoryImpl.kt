package com.example.mindfulmate.data.repository

import com.example.mindfulmate.data.repository.daily_checkin.DailyCheckInRepositoryImpl
import com.example.mindfulmate.data.service.daily_checkin.DailyCheckInService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DailyCheckInRepositoryImplTest {

    private lateinit var dailyCheckInService: DailyCheckInService
    private lateinit var dailyCheckInRepository: DailyCheckInRepositoryImpl

    @Before
    fun setUp() {
        dailyCheckInService = mockk()
        dailyCheckInRepository = DailyCheckInRepositoryImpl(dailyCheckInService)
    }

    @Test
    fun `addCheckIn calls service method`() = runBlocking {
        // Arrange
        val mood = "Happy"
        coEvery { dailyCheckInService.addCheckIn(mood) } returns Unit

        // Act
        dailyCheckInRepository.addCheckIn(mood)

        // Assert
        coVerify { dailyCheckInService.addCheckIn(mood) }
    }

    @Test
    fun `getCheckIns calls service method and returns result`() = runBlocking {
        // Arrange
        val checkIns = listOf("Happy" to 0.8f, "Sad" to 0.5f)
        coEvery { dailyCheckInService.getCheckIns() } returns checkIns

        // Act
        val result = dailyCheckInRepository.getCheckIns()

        // Assert
        coVerify { dailyCheckInService.getCheckIns() }
        assertEquals(checkIns, result)
    }
}
