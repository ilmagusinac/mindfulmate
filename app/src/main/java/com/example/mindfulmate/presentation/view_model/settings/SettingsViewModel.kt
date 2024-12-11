package com.example.mindfulmate.presentation.view_model.settings

import android.app.Application
import android.util.Log
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.example.mindfulmate.domain.repository.user.UserRepository
import com.example.mindfulmate.domain.usecase.user.GetUserUseCase
import com.example.mindfulmate.presentation.ui.screen.settings.util.SettingsParams
import com.example.mindfulmate.presentation.work.daily_checkin.NotificationPreferenceManager
import com.example.mindfulmate.presentation.work.daily_checkin.WorkScheduler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val getUserUseCase: GetUserUseCase,
    private val notificationPreferenceManager: NotificationPreferenceManager,
    private val application: Application
) : ViewModel() {

    private val _uiState: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState.Init)
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _navigationEvent: Channel<SettingsNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    val isNotificationsEnabled: StateFlow<Boolean> = notificationPreferenceManager
        .isNotificationsEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    init {
        loadUser()
    }

    private fun triggerNavigation(event: SettingsNavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }

    fun signOut(){
        viewModelScope.launch{
            try {
                userRepository.signOut()
                _uiState.update { SettingsUiState.Success(true) }
                triggerNavigation(SettingsNavigationEvent.Navigate)
            } catch (e: Exception) {
                _uiState.update { SettingsUiState.Failure("Sign-out failed: ${e.localizedMessage}") }
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            _uiState.update { SettingsUiState.Loading }
            try {
                val currentUser = getUserUseCase()
                val settingsParams = SettingsParams(
                    firstName = currentUser.firstName,
                    lastName = currentUser.lastName,
                    username = currentUser.username
                )
                _uiState.update { SettingsUiState.Success(settingsParams = settingsParams) }
            } catch (e: Exception) {
                _uiState.update { SettingsUiState.Failure(e.localizedMessage ?: "Failed to load user data") }
            }
        }
    }

    fun toggleNotifications() {
        viewModelScope.launch {
            val currentValue = isNotificationsEnabled.value
            notificationPreferenceManager.setNotificationEnabled(!currentValue)

            if (!currentValue) {
                WorkScheduler.scheduleDailyCheckIn(application) // Enable notifications
                Log.d("Notification", "Enabled notification")
            } else {
                WorkScheduler.cancelDailyCheckIn(application) // Disable notifications
                Log.d("Notification", "Disabled notification")

            }
        }
    }

}
