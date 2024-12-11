package com.example.mindfulmate.data.repository.daily_checkin

import com.example.mindfulmate.data.service.daily_checkin.DailyCheckInService
import com.example.mindfulmate.domain.repository.daily_checkin.DailyCheckInRepository
import javax.inject.Inject

class DailyCheckInRepositoryImpl @Inject constructor(
    private val dailyCheckInService: DailyCheckInService
) : DailyCheckInRepository {

    override suspend fun addCheckIn(mood: String) =
        dailyCheckInService.addCheckIn(mood)

    override suspend fun getCheckIns(): List<Pair<String, Float>> =
        dailyCheckInService.getCheckIns()
}