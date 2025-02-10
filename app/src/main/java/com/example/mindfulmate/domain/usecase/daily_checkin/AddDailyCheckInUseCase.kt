package com.example.mindfulmate.domain.usecase.daily_checkin

import com.example.mindfulmate.domain.repository.daily_checkin.DailyCheckInRepository
import javax.inject.Inject

class AddDailyCheckInUseCase @Inject constructor(
    private val dailyCheckInRepository: DailyCheckInRepository
) {
    suspend operator fun invoke(mood: String): Boolean {
        return try {
            dailyCheckInRepository.addCheckIn(mood)
            true
        } catch (e: Exception) {
            false
        }
    }
}
