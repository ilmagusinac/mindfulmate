package com.example.mindfulmate.presentation.view_model.profile.delete_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.repository.user.UserRepository
import com.example.mindfulmate.domain.usecase.user.DeleteUserUseCase
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
class DeleteAccountViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DeleteAccountUiState> = MutableStateFlow(DeleteAccountUiState.Init)
    val uiState: StateFlow<DeleteAccountUiState> = _uiState.asStateFlow()

    private val _navigationEvent: Channel<DeleteAccountNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun deleteAccount() {
        viewModelScope.launch {
            _uiState.update { DeleteAccountUiState.Loading }
            try {
                deleteUserUseCase()
                userRepository.deleteAccount()
                triggerNavigation(DeleteAccountNavigationEvent.Navigate)
                _uiState.update { DeleteAccountUiState.Success }
            } catch (e: Exception) {
                _uiState.update { DeleteAccountUiState.Failure("Failed to delete account: ${e.localizedMessage}") }
            }
        }
    }

    private fun triggerNavigation(event: DeleteAccountNavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }
}
