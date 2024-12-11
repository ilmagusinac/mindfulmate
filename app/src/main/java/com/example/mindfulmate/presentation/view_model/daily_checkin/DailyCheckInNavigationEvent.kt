package com.example.mindfulmate.presentation.view_model.daily_checkin

sealed interface DailyCheckInNavigationEvent {
    data object Navigate : DailyCheckInNavigationEvent
}