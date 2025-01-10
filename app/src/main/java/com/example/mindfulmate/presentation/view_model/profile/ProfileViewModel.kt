package com.example.mindfulmate.presentation.view_model.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.usecase.daily_checkin.GetDailyCheckInsUseCase
import com.example.mindfulmate.domain.usecase.user.GetUserUseCase
import com.example.mindfulmate.presentation.ui.screen.profile.util.ProfileParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getDailyCheckInsUseCase: GetDailyCheckInsUseCase
) : ViewModel() {

    private val _user: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.Init)
    val user: StateFlow<ProfileUiState> = _user.asStateFlow()

    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.Init)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _lastDailyCheckIn: MutableStateFlow<String?> = MutableStateFlow(null)
    val lastDailyCheckIn: StateFlow<String?> = _lastDailyCheckIn.asStateFlow()

    init {
        loadUser()
        fetchLastDailyCheckIn()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _uiState.update { ProfileUiState.Loading }
            try {
                val currentUser = getUserUseCase()
                println(currentUser)
                val profileParams = ProfileParams(
                    firstName = currentUser.firstName,
                    lastName = currentUser.lastName,
                    username = currentUser.username,
                    email = currentUser.email,
                    number = currentUser.number
                )
                _uiState.update { ProfileUiState.Success(profileParams = profileParams) }
            } catch (e: Exception) {
                _uiState.update { ProfileUiState.Failure(e.localizedMessage ?: "Failed to load user data") }
            }
        }
    }

    private fun fetchLastDailyCheckIn() {
        viewModelScope.launch {
            try {
                val checkIns = getDailyCheckInsUseCase()
                val latestCheckIn = checkIns.maxByOrNull { it.first }?.first
                _lastDailyCheckIn.emit(latestCheckIn)
            } catch (e: Exception) {
                _lastDailyCheckIn.emit(null)
            }
        }
    }
}
