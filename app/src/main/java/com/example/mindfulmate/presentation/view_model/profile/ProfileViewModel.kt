package com.example.mindfulmate.presentation.view_model.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class ProfileViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    private val _user: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.Init)
    val user: StateFlow<ProfileUiState> = _user.asStateFlow()

    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.Init)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _uiState.update { ProfileUiState.Loading }
            try {
                val currentUser = getUserUseCase()
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
}
