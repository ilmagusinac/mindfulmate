package com.example.mindfulmate.presentation.view_model.daily_checkin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.usecase.daily_checkin.AddDailyCheckInUseCase
import com.example.mindfulmate.domain.usecase.daily_checkin.GetDailyCheckInsUseCase
import com.example.mindfulmate.presentation.work.daily_checkin.CheckInStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyCheckInViewModel @Inject constructor(
    private val addDailyCheckInUseCase: AddDailyCheckInUseCase,
    private val checkInStateManager: CheckInStateManager,
    private val getDailyCheckInsUseCase: GetDailyCheckInsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<DailyCheckInUiState> = MutableStateFlow(DailyCheckInUiState.Init)
    val uiState: StateFlow<DailyCheckInUiState> = _uiState.asStateFlow()

    private val _navigationEvent: Channel<DailyCheckInNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _chartData: MutableStateFlow<List<Pair<String, Float>>> = MutableStateFlow(emptyList())
    val chartData: StateFlow<List<Pair<String, Float>>> = _chartData.asStateFlow()

    private val _pieChartData: MutableStateFlow<Map<String, Float>> = MutableStateFlow(emptyMap())
    val pieChartData: StateFlow<Map<String, Float>> = _pieChartData.asStateFlow()

    private val _triggeredMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val triggeredMessage: StateFlow<String?> = _triggeredMessage.asStateFlow()

    fun addCheckIn(mood: String, onChatTrigger: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val result = addDailyCheckInUseCase(mood)
                if (result) {
                    checkInStateManager.setCheckInCompleted()
                    _uiState.update { DailyCheckInUiState.Success }

                    val message = when(mood.lowercase()){
                        "happy" -> "Great to hear you're feeling happy! Keep up the positivity!"
                        "sad" -> "I'm here for you. Would you like to talk about what's on your mind?"
                        "neutral" -> "It is okay to feel like this sometimes!"
                        else -> null
                    }

                    _triggeredMessage.emit(message)
                    println("MESSAGE TRIGGER: ${_triggeredMessage.value}")
                    fetchCheckIns()
                    onChatTrigger(message)
                    triggerNavigation(DailyCheckInNavigationEvent.Navigate)
                } else {
                    _uiState.update { DailyCheckInUiState.Failure("Add check-in failed") }
                }
            } catch (e: Exception) {
                _uiState.update { DailyCheckInUiState.Failure("Add check-in failed: ${e.localizedMessage}") }
            }

        }
    }

    fun fetchCheckIns() {
        viewModelScope.launch {
            try {
                val rawData = getDailyCheckInsUseCase()
                _chartData.emit(rawData)
            } catch (e: Exception) {
                _chartData.emit(emptyList())
            }
        }
    }

    fun fetchMoodAveragesForPieChart() {
        viewModelScope.launch {
            try {
                val rawData = getDailyCheckInsUseCase()

                val moodCounts = rawData.groupingBy { it.second }.eachCount()
                val totalMoods = moodCounts.values.sum()

                val moodPercentages = mapOf(
                    "Happy" to (moodCounts[80f]?.toFloat() ?: 0f) / totalMoods * 100,
                    "Neutral" to (moodCounts[55f]?.toFloat() ?: 0f) / totalMoods * 100,
                    "Sad" to (moodCounts[30f]?.toFloat() ?: 0f) / totalMoods * 100
                )

                _pieChartData.emit(moodPercentages)
            } catch (e: Exception) {
                _pieChartData.emit(emptyMap())
            }
        }
    }

    private fun triggerNavigation(event: DailyCheckInNavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }
}
