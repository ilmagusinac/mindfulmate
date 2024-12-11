package com.example.mindfulmate.domain.repository.daily_checkin

interface DailyCheckInRepository {
    suspend fun addCheckIn(mood: String)
    suspend fun getCheckIns(): List<Pair<String, Float>>
}
