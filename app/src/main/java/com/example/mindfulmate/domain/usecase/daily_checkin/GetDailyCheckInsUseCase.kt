package com.example.mindfulmate.domain.usecase.daily_checkin

import com.example.mindfulmate.domain.repository.daily_checkin.DailyCheckInRepository
import javax.inject.Inject

class GetDailyCheckInsUseCase @Inject constructor(
    private val dailyCheckInRepository: DailyCheckInRepository
) {
    suspend operator fun invoke(): List<Pair<String, Float>> {
        return dailyCheckInRepository.getCheckIns()
    }
}
