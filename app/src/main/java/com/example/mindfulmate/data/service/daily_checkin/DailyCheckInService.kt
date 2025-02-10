package com.example.mindfulmate.data.service.daily_checkin

interface DailyCheckInService {
    suspend fun addCheckIn(mood: String)
    suspend fun getCheckIns(): List<Pair<String, Float>>
}
