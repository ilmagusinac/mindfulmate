package com.example.mindfulmate.presentation.view_model.profile.edit_credential

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.repository.user.UserRepository
import com.example.mindfulmate.domain.usecase.user.GetUserUseCase
import com.example.mindfulmate.domain.usecase.user.UpdateUserUseCase
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
class EditCredentialViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<EditCredentialUiState> = MutableStateFlow(EditCredentialUiState.Init)
    val uiState: StateFlow<EditCredentialUiState> = _uiState.asStateFlow()

    private val _navigationEvent: Channel<EditCredentialNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private var _resetEmail: MutableStateFlow<String?> = MutableStateFlow(null)
    val resetEmail: StateFlow<String?> = _resetEmail.asStateFlow()

    private var _resetPassword: MutableStateFlow<String?> = MutableStateFlow(null)
    val resetPassword: StateFlow<String?> = _resetPassword.asStateFlow()

    private val _isEmailEnabled = MutableStateFlow(false)
    val isEmailEnabled: StateFlow<Boolean> = _isEmailEnabled.asStateFlow()

    private val _isEmailPasswordEnabled = MutableStateFlow(false)
    val isEmailPasswordEnabled: StateFlow<Boolean> = _isEmailPasswordEnabled.asStateFlow()

    fun validateEmail(email: String) {
        _isEmailEnabled.value = email.isNotBlank()
    }

    fun validatePasswordEmail(
        email: String,
        newEmail: String,
        password: String
    ) {
        _isEmailPasswordEnabled.value = email.isNotBlank() && newEmail.isNotBlank() && password.isNotBlank()
    }

    fun resetPassword(emailAddress: String) {
        viewModelScope.launch {
            try {
                userRepository.resetPassword(emailAddress)
                _resetPassword.value = "Check your email!"
                _uiState.update { EditCredentialUiState.Success }
                signOut()
                triggerNavigation(EditCredentialNavigationEvent.Navigate)
            } catch (e: Exception) {
                _uiState.update { EditCredentialUiState.Failure("Reset password failed: ${e.localizedMessage}") }
                _resetPassword.value = "Reset password failed: ${e.localizedMessage}"
            }
        }
    }

    fun updateEmail(
        currentEmail: String,
        currentPassword: String,
        newEmail: String
    ) {
        viewModelScope.launch {
            try {
                val currentUser = getUserUseCase()
                val updatedUser = currentUser.copy(email = newEmail)
                updateUserUseCase(updatedUser)
                userRepository.updateEmail(currentEmail, currentPassword, newEmail)
                _resetEmail.value = "Check your email!"
                updateUserUseCase(updatedUser)
                _uiState.update { EditCredentialUiState.Success }
                signOut()
                triggerNavigation(EditCredentialNavigationEvent.Navigate)
            } catch (e: Exception) {
                _uiState.update { EditCredentialUiState.Failure("Email update failed: ${e.localizedMessage}") }
                _resetEmail.value = "Email update failed: ${e.localizedMessage}"
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                userRepository.signOut()
                _uiState.update { EditCredentialUiState.Success }
            } catch (e: Exception) {
                _uiState.update { EditCredentialUiState.Failure("Sign-out failed: ${e.localizedMessage}") }
            }
        }
    }

    fun resetUiState() {
        _uiState.update { EditCredentialUiState.Init }
    }

    private fun triggerNavigation(event: EditCredentialNavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }
}
