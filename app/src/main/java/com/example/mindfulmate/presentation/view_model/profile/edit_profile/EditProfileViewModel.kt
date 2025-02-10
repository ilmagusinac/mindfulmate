package com.example.mindfulmate.presentation.view_model.profile.edit_profile

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.model.user.User
import com.example.mindfulmate.domain.usecase.user.GetAllUsersUseCase
import com.example.mindfulmate.domain.usecase.user.GetUserUseCase
import com.example.mindfulmate.domain.usecase.user.UpdateUserUseCase
import com.example.mindfulmate.domain.usecase.user.UploadProfileImageUseCase
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
    private val updateUserUseCase: UpdateUserUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<EditProfileUiState> = MutableStateFlow(EditProfileUiState.Init)
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private val _navigationEvent: Channel<EditProfileNavigationEvent> = Channel(Channel.CONFLATED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri.asStateFlow()

    private val _uploadState = MutableStateFlow<String?>(null)
    val uploadState: StateFlow<String?> = _uploadState.asStateFlow()

    private val _usernameError = MutableStateFlow<String?>(null)
    val usernameError: StateFlow<String?> = _usernameError.asStateFlow()

    private val _phoneError = MutableStateFlow<String?>(null)
    val phoneError: StateFlow<String?> = _phoneError.asStateFlow()

    private val _isSaveButtonEnabled = MutableStateFlow(true)
    val isSaveButtonEnabled: StateFlow<Boolean> = _isSaveButtonEnabled.asStateFlow()

    private val _toastMessage = Channel<String>(Channel.CONFLATED)
    val toastMessage = _toastMessage.receiveAsFlow()

    init {
        loadUser()
    }

    fun validateUsername(username: String) {
        viewModelScope.launch {
            val allUsers = getAllUsersUseCase.invoke()
            val isUsernameTaken = allUsers.any { it.username == username && it.id != getUserUseCase().id }

            _usernameError.emit(
                if (isUsernameTaken) "Username already exists. Please choose another one." else null
            )

            updateSaveButtonState()
        }
    }

    fun validatePhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            val isValid = phoneNumber.matches(Regex("^[0-9]{10,15}\$")) // Adjust regex as needed

            _phoneError.emit(
                if (!isValid) "Invalid phone number format" else null
            )

            updateSaveButtonState()
        }
    }

    private fun updateSaveButtonState() {
        viewModelScope.launch {
            val isDisabled = _usernameError.value != null || _phoneError.value != null
            _isSaveButtonEnabled.emit(!isDisabled)
        }
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
                    numberFieldValue = TextFieldValue(currentUser.number),
                    imageUrl = currentUser.profileImageUrl,
                    myCommunities = currentUser.myCommunities
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
                var profileImageUrl = editProfileParams.imageUrl

                _selectedImageUri.value?.let { uri ->
                    profileImageUrl = uploadProfileImageUseCase.invoke(uri)
                }

                val currentUser = getUserUseCase()

                val updatedUser = User(
                    firstName = editProfileParams.firstNameFieldValue.text,
                    lastName = editProfileParams.lastNameFieldValue.text,
                    username = editProfileParams.usernameFieldValue.text,
                    email = editProfileParams.emailFieldValue.text,
                    number = editProfileParams.numberFieldValue.text,
                    profileImageUrl = profileImageUrl ?: "",
                    myCommunities = currentUser.myCommunities
                )
                updateUserUseCase(updatedUser)
                loadUser()
                _toastMessage.send("Profile updated successfully")
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

    fun onImageSelected(uri: Uri?) {
        viewModelScope.launch {
            _selectedImageUri.emit(uri)
        }
    }
}
