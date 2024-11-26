package com.example.mindfulmate.presentation.view_model.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.data.service.AccountService
import com.example.mindfulmate.domain.repository.user.UserRepository
import com.example.mindfulmate.domain.usecase.user.GetUserUseCase
import com.example.mindfulmate.presentation.view_model.signin.SignInNavigationEvent
import com.example.mindfulmate.presentation.view_model.signin.SignInUiState
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
class MainViewModel @Inject constructor(
    private val accountService: AccountService,
    private val userRepository: UserRepository,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState.Init)
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _navigationEvent: Channel<SignInNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username.asStateFlow()

    fun isUserSignedIn(): Boolean {
        return accountService.hasUser()
    }

    fun signOut(){
        viewModelScope.launch{
            try {
                userRepository.signOut()
                _uiState.update { SignInUiState.Success(true) }
                triggerNavigation(SignInNavigationEvent.NavigateBack)
            } catch (e: Exception) {
                _uiState.update { SignInUiState.Failure("Sign-out failed: ${e.localizedMessage}") }
            }
        }
    }

    private fun triggerNavigation(event: SignInNavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }

    fun loadUser() {
        viewModelScope.launch {
            try {
                val currentUser = getUserUseCase()
                _username.value = currentUser.username
            } catch (e: Exception) {
                _username.value = "Unknown User"
            }
        }
    }
}