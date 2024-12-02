package com.example.mindfulmate.presentation.view_model.signin

import android.content.Context
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.mindfulmate.R
import com.example.mindfulmate.domain.repository.user.UserRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

@HiltViewModel
class SignInViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _uiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState.Init)
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _navigationEvent: Channel<SignInNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private var _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private var _resetPassword: MutableStateFlow<String?> = MutableStateFlow(null)
    val resetPassword: StateFlow<String?> = _resetPassword.asStateFlow()

    private val _isSignInEnabled = MutableStateFlow(false)
    val isSignInEnabled: StateFlow<Boolean> = _isSignInEnabled.asStateFlow()

    fun validateInput(email: String, password: String) {
        _isSignInEnabled.value = email.isNotBlank() && password.isNotBlank()
    }

    fun signIn(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                userRepository.signIn(email, password)
                _uiState.update { SignInUiState.Success(true) }
                triggerNavigation(SignInNavigationEvent.Navigate)
                _errorMessage.value = null
            } catch (e: Exception) {
                _uiState.update { SignInUiState.Failure("Sign-in failed: ${e.localizedMessage}") }
                _errorMessage.value = "Sign-in failed: ${e.localizedMessage}"
            }
        }
    }

    fun resetUiState() {
        _uiState.update { SignInUiState.Init }
    }

    private fun triggerNavigation(event: SignInNavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }

    private fun onSignInWithGoogle(credential: Credential) {
        viewModelScope.launch {
            try {
                if (credential is GoogleIdTokenCredential) {
                    val idToken = credential.idToken
                    if (idToken != null) {
                        userRepository.signInWithGoogle(idToken)
                        _uiState.update { SignInUiState.Success(true) }
                        triggerNavigation(SignInNavigationEvent.Navigate)
                    } else {
                        _uiState.update { SignInUiState.Failure("No valid ID token found.") }
                    }
                } else {
                    _uiState.update { SignInUiState.Failure("Invalid credential type for Google sign-in.") }
                }
            } catch (e: Exception) {
                _uiState.update { SignInUiState.Failure("Sign-in failed: ${e.localizedMessage}") }
            }
        }
    }

    fun startGoogleSignIn(context: Context) {
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
                    onSignInWithGoogle(credential)
                }
            } catch (e: GetCredentialException) {
                _uiState.update { SignInUiState.Failure("Google sign-in failed: ${e.localizedMessage}") }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                userRepository.signOut()
                _uiState.update { SignInUiState.Success(true) }
                triggerNavigation(SignInNavigationEvent.NavigateBack)
            } catch (e: Exception) {
                _uiState.update { SignInUiState.Failure("Sign-out failed: ${e.localizedMessage}") }
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            try {
                userRepository.deleteAccount()
                _uiState.update { SignInUiState.Success(true) }
                triggerNavigation(SignInNavigationEvent.NavigateBack)
            } catch (e: Exception) {
                _uiState.update { SignInUiState.Failure("Delete account failed: ${e.localizedMessage}") }
            }
        }
    }

    fun resetPassword(emailAddress: String) {
        viewModelScope.launch {
            try {
                userRepository.resetPassword(emailAddress)
                _resetPassword.value = "Check your email!"
                _uiState.update { SignInUiState.Success(true) }
                triggerNavigation(SignInNavigationEvent.Navigate)
            } catch (e: Exception) {
                _uiState.update { SignInUiState.Failure("Reset password failed: ${e.localizedMessage}") }
                _resetPassword.value = "Reset password failed: ${e.localizedMessage}"
            }
        }
    }
}
