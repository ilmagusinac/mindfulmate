package com.example.mindfulmate.presentation.view_model.emergency_contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindfulmate.domain.model.emergency_contact.UserEmergencyContact
import com.example.mindfulmate.domain.usecase.emergency_contact.AddUserEmergencyContactUseCase
import com.example.mindfulmate.domain.usecase.emergency_contact.DeleteUserEmergencyContactUseCase
import com.example.mindfulmate.domain.usecase.emergency_contact.FetchGlobalEmergencyContactsUseCase
import com.example.mindfulmate.domain.usecase.emergency_contact.FetchUserEmergencyContactsUseCase
import com.example.mindfulmate.domain.usecase.emergency_contact.UpdateUserEmergencyContactUseCase
import com.example.mindfulmate.presentation.ui.screen.emergency_contact.util.EmergencyInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyContactViewModel @Inject constructor(
    private val fetchGlobalEmergencyContactsUseCase: FetchGlobalEmergencyContactsUseCase,
    private val fetchUserEmergencyContactsUseCase: FetchUserEmergencyContactsUseCase,
    private val addUserEmergencyContactUseCase: AddUserEmergencyContactUseCase,
    private val updateUserEmergencyContactUseCase: UpdateUserEmergencyContactUseCase,
    private val deleteUserEmergencyContactUseCase: DeleteUserEmergencyContactUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<EmergencyContactUiState> =
        MutableStateFlow(EmergencyContactUiState.Init)
    val uiState: StateFlow<EmergencyContactUiState> = _uiState.asStateFlow()

    private val _editableContact = MutableStateFlow<EmergencyInformation?>(null)
    val editableContact: StateFlow<EmergencyInformation?> = _editableContact

    fun setEditableContact(contact: EmergencyInformation?) {
        _editableContact.value = contact
    }

    fun fetchEmergencyContacts() {
        viewModelScope.launch {
            _uiState.value = EmergencyContactUiState.Loading
            try {
                val globalContacts = fetchGlobalEmergencyContactsUseCase.invoke().map {
                    EmergencyInformation(it.id, it.name, it.phone)
                }
                val userContacts = fetchUserEmergencyContactsUseCase.invoke().map {
                    EmergencyInformation(it.id, it.name, it.phone)
                }
                _uiState.value = EmergencyContactUiState.Success(globalContacts, userContacts)
            } catch (e: Exception) {
                _uiState.value =
                    EmergencyContactUiState.Failure("Failed fetching Emergency Contacts: ${e.localizedMessage}")
            }
        }
    }

    fun addUserEmergencyContact(contactName: String, contactPhone: String) {
        viewModelScope.launch {
            _uiState.value = EmergencyContactUiState.Loading
            try {
                val newContact = UserEmergencyContact(
                    name = contactName,
                    phone = contactPhone
                )
                addUserEmergencyContactUseCase.invoke(newContact)
                fetchEmergencyContacts()
            } catch (e: Exception) {
                _uiState.value =
                    EmergencyContactUiState.Failure("Failed to add contact: ${e.localizedMessage}")
            }
        }
    }

    fun updateUserEmergencyContact(contactId: String, newName: String, newPhone: String) {
        viewModelScope.launch {
            _uiState.value = EmergencyContactUiState.Loading
            try {
                val updatedContact = UserEmergencyContact(
                    id = contactId,
                    name = newName,
                    phone = newPhone
                )
                updateUserEmergencyContactUseCase.invoke(updatedContact)

                fetchEmergencyContacts()
            } catch (e: Exception) {
                _uiState.value =
                    EmergencyContactUiState.Failure("Failed to update contact: ${e.localizedMessage}")
            }
        }
    }

    fun deleteUserEmergencyContact(contactId: String) {
        viewModelScope.launch {
            _uiState.value = EmergencyContactUiState.Loading
            try {
                deleteUserEmergencyContactUseCase.invoke(contactId)
                fetchEmergencyContacts()
            } catch (e: Exception) {
                _uiState.value =
                    EmergencyContactUiState.Failure("Failed to delete contact: ${e.localizedMessage}")
            }
        }
    }

}
