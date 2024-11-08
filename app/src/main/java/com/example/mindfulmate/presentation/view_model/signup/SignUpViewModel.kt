package com.example.mindfulmate.presentation.view_model.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.R
import com.example.mindfulmate.domain.repository.user.UserRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
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
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState.Init)
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _navigationEvent: Channel<SignUpNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun signUp(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            if (password != confirmPassword) {
                _uiState.update { SignUpUiState.Failure("Passwords do not match") }
                return@launch
            }
            try {
                userRepository.signUp(email, password)
                _uiState.update { SignUpUiState.Success(true) }
                triggerNavigation(SignUpNavigationEvent.Navigate)
            } catch (e: Exception) {
                _uiState.update { SignUpUiState.Failure("Sign-up failed: ${e.localizedMessage}") }
            }
        }
    }

    fun resetUiState() {
        _uiState.update { SignUpUiState.Init }
    }

    private fun triggerNavigation(event: SignUpNavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }

    private fun onSignUpWithGoogle(credential: Credential) {
        viewModelScope.launch {
            try {
                if (credential is GoogleIdTokenCredential) {
                    val idToken = credential.idToken
                    if (idToken != null) {
                        userRepository.signInWithGoogle(idToken)
                        _uiState.update { SignUpUiState.Success(true) }
                        triggerNavigation(SignUpNavigationEvent.Navigate)
                    } else {
                        _uiState.update { SignUpUiState.Failure("No valid ID token found.") }
                    }
                } else {
                    _uiState.update { SignUpUiState.Failure("Invalid credential type for Google sign-in.") }
                }
            } catch (e: Exception) {
                _uiState.update { SignUpUiState.Failure("Sign-in failed: ${e.localizedMessage}") }
            }
        }
    }

    fun startGoogleSignUp(context: Context) {
        val credentialManager = CredentialManager.create(context)
        viewModelScope.launch {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            try {
                val result = credentialManager.getCredential(context, request)
                result.credential?.let { credential ->
                    onSignUpWithGoogle(credential)
                }
            } catch (e: GetCredentialException) {
                _uiState.update { SignUpUiState.Failure("Google sign-in failed: ${e.localizedMessage}") }
            }
        }
    }
}
