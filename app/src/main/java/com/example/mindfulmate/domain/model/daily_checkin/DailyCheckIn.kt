package com.example.mindfulmate.domain.model.daily_checkin

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class DailyCheckIn(
    val mood: String = "",
    val date: String = "",
    @ServerTimestamp
    val timestamp: Date? = null
)
