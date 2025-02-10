package com.example.mindfulmate.presentation.view_model.daily_checkin

sealed interface DailyCheckInUiState {
    data object Init : DailyCheckInUiState
    data object Loading : DailyCheckInUiState
    data object Success : DailyCheckInUiState
    data class Failure(val message: String) : DailyCheckInUiState
}
