package com.example.mindfulmate.presentation.view_model.profile.edit_profile

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.model.user.User
import com.example.mindfulmate.domain.usecase.user.GetUserUseCase
import com.example.mindfulmate.domain.usecase.user.UpdateUserUseCase
import com.example.mindfulmate.presentation.ui.screen.profile.util.EditProfileParams
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
class EditProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<EditProfileUiState> = MutableStateFlow(EditProfileUiState.Init)
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private val _navigationEvent: Channel<EditProfileNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _uiState.update { EditProfileUiState.Loading }
            try {
                val currentUser = getUserUseCase()
                val editProfileParams = EditProfileParams(
                    firstNameFieldValue = TextFieldValue(currentUser.firstName),
                    lastNameFieldValue = TextFieldValue(currentUser.lastName),
                    usernameFieldValue = TextFieldValue(currentUser.username),
                    emailFieldValue = TextFieldValue(currentUser.email),
                    numberFieldValue = TextFieldValue(currentUser.number)
                )
                _uiState.update { EditProfileUiState.Success(editProfileParams = editProfileParams) }
            } catch (e: Exception) {
                _uiState.update {
                    EditProfileUiState.Failure(
                        e.localizedMessage ?: "Failed to load user data"
                    )
                }
            }
        }
    }

    fun updateUser(editProfileParams: EditProfileParams) {
        viewModelScope.launch {
            try {
                _uiState.update { EditProfileUiState.Loading }
                val updatedUser = User(
                    firstName = editProfileParams.firstNameFieldValue.text,
                    lastName = editProfileParams.lastNameFieldValue.text,
                    username = editProfileParams.usernameFieldValue.text,
                    email = editProfileParams.emailFieldValue.text,
                    number = editProfileParams.numberFieldValue.text
                )
                updateUserUseCase(updatedUser)
                loadUser()
                triggerNavigation(EditProfileNavigationEvent.Navigate)
            } catch (e: Exception) {
                _uiState.update {
                    EditProfileUiState.Failure(
                        e.localizedMessage ?: "Failed to update user data"
                    )
                }
            }
        }
    }

    private fun triggerNavigation(event: EditProfileNavigationEvent) {
        viewModelScope.launch {
            _navigationEvent.send(event)
        }
    }
}
