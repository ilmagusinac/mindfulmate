package com.example.mindfulmate.presentation.ui.screen.emergency_contact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.emergency_contact.component.AddEmergencyContact
import com.example.mindfulmate.presentation.ui.screen.emergency_contact.component.DeleteUserContactField
import com.example.mindfulmate.presentation.ui.screen.emergency_contact.component.EmergencyContactHeader
import com.example.mindfulmate.presentation.ui.screen.emergency_contact.component.EmergencyContactSection
import com.example.mindfulmate.presentation.ui.screen.emergency_contact.component.UserEmergencyContactSection
import com.example.mindfulmate.presentation.ui.screen.emergency_contact.util.EmergencyInformation
import com.example.mindfulmate.presentation.view_model.emergency_contact.EmergencyContactUiState
import com.example.mindfulmate.presentation.view_model.emergency_contact.EmergencyContactViewModel

@Composable
fun EmergencyContactScreen(
    viewModel: EmergencyContactViewModel,
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: EmergencyContactUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var contactNameState by remember { mutableStateOf(TextFieldValue("")) }
    var contactPhoneState by remember { mutableStateOf(TextFieldValue("")) }
    val editableContact by viewModel.editableContact.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var contactToDelete by remember { mutableStateOf<EmergencyInformation?>(null) }

    LaunchedEffect(editableContact) {
        editableContact?.let {
            contactNameState = TextFieldValue(it.title)
            contactPhoneState = TextFieldValue(it.label)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchEmergencyContacts()
    }

    when (uiState) {
        is EmergencyContactUiState.Loading -> {
            LoadingPlaceholder()
        }

        is EmergencyContactUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = {})
        }

        is EmergencyContactUiState.Success -> {
            val state = uiState as EmergencyContactUiState.Success
            EmergencyContactScreen(
                onGoBackClick = onGoBackClick,
                contactName = contactNameState,
                contactPhone = contactPhoneState,
                onContactNameChange = { contactNameState = it },
                onContactPhoneChange = { contactPhoneState = it },
                onAddContactClick = {
                    if (editableContact != null) {
                        viewModel.updateUserEmergencyContact(
                            editableContact!!.id,
                            contactNameState.text,
                            contactPhoneState.text
                        )
                        viewModel.setEditableContact(null)
                    } else {
                        viewModel.addUserEmergencyContact(
                            contactNameState.text,
                            contactPhoneState.text
                        )
                    }
                    contactNameState = TextFieldValue("")
                    contactPhoneState = TextFieldValue("")
                },
                globalEmergencyInformation = state.globalContacts,
                userEmergencyInformation = state.userContacts,
                onEditClick = { contact ->
                    viewModel.setEditableContact(contact)
                },
                onDeleteClick = { contact ->
                    contactToDelete = contact
                    showDeleteDialog = true
                },
                modifier = modifier
            )
        }

        else -> {
            // no-op
        }
    }

    if (showDeleteDialog && contactToDelete != null) {
        DeleteUserContactField(
            onDeleteClick = {
                viewModel.deleteUserEmergencyContact(contactToDelete!!.id)
                showDeleteDialog = false
            },
            onCancelClick = { showDeleteDialog = false }
        )
    }

}

@Composable
private fun EmergencyContactScreen(
    onGoBackClick: () -> Unit,
    contactName: TextFieldValue,
    contactPhone: TextFieldValue,
    onContactNameChange: (TextFieldValue) -> Unit,
    onContactPhoneChange: (TextFieldValue) -> Unit,
    onAddContactClick: () -> Unit,
    onEditClick: (EmergencyInformation) -> Unit,
    onDeleteClick: (EmergencyInformation) -> Unit,
    globalEmergencyInformation: List<EmergencyInformation>,
    userEmergencyInformation: List<EmergencyInformation>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var shouldScrollToTop by remember { mutableStateOf(false) }

    LaunchedEffect(shouldScrollToTop) {
        if (shouldScrollToTop) {
            scrollState.animateScrollTo(0)
            shouldScrollToTop = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                horizontal = dimensionResource(id = R.dimen.padding_medium)
            )
            .verticalScroll(scrollState)
    ) {
        EmergencyContactHeader(onGoBackClick = onGoBackClick)
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))
        AddEmergencyContact(
            contactName = contactName,
            contactPhone = contactPhone,
            onContactNameChange = onContactNameChange,
            onContactPhoneChange = onContactPhoneChange,
            onAddContactClick = onAddContactClick
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))
        Text(
            text = stringResource(id = R.string.global_emergency_contacts),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Grey,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_xxdefault)))
        EmergencyContactSection(emergencyInformation = globalEmergencyInformation)
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        Text(
            text = stringResource(id = R.string.users_emergency_contacts),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Grey,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
        )
        Spacer(modifier = modifier.height(dimensionResource(id = R.dimen.spacing_xxdefault)))
        UserEmergencyContactSection(
            emergencyInformation = userEmergencyInformation,
            onEditClick = { contact ->
                onEditClick(contact)
                shouldScrollToTop = true
            },
            onDeleteClick = onDeleteClick
        )
    }
}

@Preview
@Composable
private fun EmergencyContactScreenPreview() {
    MindfulMateTheme {
        EmergencyContactScreen(
            onGoBackClick = {},
            contactName = TextFieldValue("sss"),
            contactPhone = TextFieldValue("something"),
            globalEmergencyInformation = listOf(
                EmergencyInformation(
                    title = "Email",
                    label = "Ilma"
                ),
                EmergencyInformation(
                    title = "First Name",
                    label = "Ilma"
                ),
                EmergencyInformation(
                    title = "Number",
                    label = "061031166"
                )
            ),
            userEmergencyInformation = listOf(
                EmergencyInformation(
                    title = "Email",
                    label = "Ilma"
                ),
                EmergencyInformation(
                    title = "First Name",
                    label = "Ilma"
                ),
                EmergencyInformation(
                    title = "Number",
                    label = "061031166"
                )
            ),
            onContactNameChange = {},
            onContactPhoneChange = {},
            onAddContactClick = {},
            onDeleteClick = {},
            onEditClick = {}
        )
    }
}
