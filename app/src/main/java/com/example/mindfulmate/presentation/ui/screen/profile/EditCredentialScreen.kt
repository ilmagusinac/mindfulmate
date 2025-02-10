package com.example.mindfulmate.presentation.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.DuskyBlue
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.community.DeleteContentPopUp
import com.example.mindfulmate.presentation.ui.screen.profile.util.ContentRow
import com.example.mindfulmate.presentation.ui.screen.profile.component.edit_credential.EditCredentialHeaderSection
import com.example.mindfulmate.presentation.ui.screen.profile.component.edit_credential.EditCredentialInformationSection
import com.example.mindfulmate.presentation.ui.screen.profile.util.ExpandedFiledType
import com.example.mindfulmate.presentation.ui.screen.profile.util.EditCredentialParams
import com.example.mindfulmate.presentation.view_model.profile.edit_credential.EditCredentialNavigationEvent
import com.example.mindfulmate.presentation.view_model.profile.edit_credential.EditCredentialUiState
import com.example.mindfulmate.presentation.view_model.profile.edit_credential.EditCredentialViewModel

@Composable
fun EditCredentialScreen(
    viewModel: EditCredentialViewModel,
    onGoBackClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: EditCredentialUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val resetPassword by viewModel.resetPassword.collectAsStateWithLifecycle()
    val resetEmail by viewModel.resetEmail.collectAsStateWithLifecycle()

    var currentPasswordState by remember { mutableStateOf(TextFieldValue("")) }
    var currentEmailState by remember { mutableStateOf(TextFieldValue("")) }
    var emailState by remember { mutableStateOf(TextFieldValue("")) }
    var newEmailState by remember { mutableStateOf(TextFieldValue("")) }

    val isEditPasswordEnabled by viewModel.isEmailEnabled.collectAsStateWithLifecycle()
    val isEditEmailPasswordEnabled by viewModel.isEmailPasswordEnabled.collectAsStateWithLifecycle()
    val isDeleteAccountVisible by viewModel.isDeleteAccountVisible.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    NavigationEventHandler(
        viewModel = viewModel,
        navigate = navigate,
        navigateDeleted = onDeleteAccountClick
    )

    LaunchedEffect(uiState) {
        when (uiState) {
            is EditCredentialUiState.Success -> {
                viewModel.resetUiState()
                currentPasswordState = TextFieldValue("")
                currentEmailState = TextFieldValue("")
                emailState = TextFieldValue("")
                newEmailState = TextFieldValue("")
            }

            is EditCredentialUiState.Failure -> {
                viewModel.resetUiState()
            }

            else -> Unit
        }
    }

    when (uiState) {
        is EditCredentialUiState.Loading -> {
            LoadingPlaceholder()
        }

        else -> {
            EditCredentialScreen(
                editCredentialParams = EditCredentialParams(
                    currentPasswordFieldValue = currentPasswordState,
                    currentEmailFieldValue = currentEmailState,
                    emailFieldValue = emailState,
                    newEmailFieldValue = newEmailState,
                    messagePassword = resetPassword,
                    messageEmail = resetEmail,
                    isEditPasswordEnable = isEditPasswordEnabled,
                    isEditEmailEnable = isEditEmailPasswordEnabled
                ),
                onCurrentPasswordValueChange = {
                    currentPasswordState = it
                    viewModel.validatePasswordEmail(
                        email = currentEmailState.text,
                        newEmail = newEmailState.text,
                        password = currentPasswordState.text
                    )
                },
                onCurrentEmailValueChange = {
                    currentEmailState = it
                    viewModel.validatePasswordEmail(
                        email = currentEmailState.text,
                        newEmail = newEmailState.text,
                        password = currentPasswordState.text
                    )
                },
                onEmailValueChange = {
                    emailState = it
                    viewModel.validateEmail(email = emailState.text)
                },
                onNewEmailValueChange = {
                    newEmailState = it
                    viewModel.validatePasswordEmail(
                        email = currentEmailState.text,
                        newEmail = newEmailState.text,
                        password = currentPasswordState.text
                    )
                },
                onEditEmailClick = {
                    viewModel.updateEmail(
                        currentEmail = currentEmailState.text,
                        currentPassword = currentPasswordState.text,
                        newEmail = newEmailState.text
                    )
                },
                onEditPasswordClick = { viewModel.resetPassword(emailState.text) },
                onGoBackClick = onGoBackClick,
                onDeleteAccountClick = { viewModel.showDeleteAccountPopup() },
                modifier = modifier
            )
        }
    }

    if(isDeleteAccountVisible) {
        DeleteContentPopUp(
            deleteTitle = "Delete Account",
            deleteDialog = "Sorry to hear this :(. Are you sure of this action?",
            onCancelClick = { viewModel.hideDeleteAccountPopup() },
            onDeleteClick = { viewModel.confirmDeleteAccount() }
        )
    }
}

@Composable
private fun NavigationEventHandler(
    viewModel: EditCredentialViewModel,
    navigate: () -> Unit,
    navigateDeleted: () -> Unit
) {
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                is EditCredentialNavigationEvent.Navigate -> {
                    navigate()
                }
                is EditCredentialNavigationEvent.NavigateDeleted -> {
                    navigateDeleted()
                }
            }
        }
    }
}

@Composable
private fun EditCredentialScreen(
    editCredentialParams: EditCredentialParams,
    onCurrentPasswordValueChange: (TextFieldValue) -> Unit,
    onCurrentEmailValueChange: (TextFieldValue) -> Unit,
    onEmailValueChange: (TextFieldValue) -> Unit,
    onNewEmailValueChange: (TextFieldValue) -> Unit,
    onGoBackClick: () -> Unit,
    onEditEmailClick: () -> Unit,
    onEditPasswordClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                horizontal = dimensionResource(id = R.dimen.padding_default)
            )
            .verticalScroll(rememberScrollState())
    ) {
        EditCredentialHeaderSection(onGoBackClick = onGoBackClick)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))
        EditCredentialInformationSection(
            tabs = listOf(
                ContentRow(
                    title = stringResource(id = R.string.email),
                    label = stringResource(id = R.string.change_your_email_credential),
                    placeholderRes = R.drawable.ic_change_email,
                    tint = DuskyBlue,
                    expandedTabs = listOf(
                        ExpandedFiledType(
                            title = stringResource(id = R.string.current_password),
                            text = editCredentialParams.currentPasswordFieldValue,
                            placeholder = stringResource(id = R.string.enter_current_password),
                            onTextValueChange = onCurrentPasswordValueChange
                        ),
                        ExpandedFiledType(
                            title = stringResource(id = R.string.current_email),
                            text = editCredentialParams.currentEmailFieldValue,
                            placeholder = stringResource(id = R.string.enter_current_email),
                            onTextValueChange = onCurrentEmailValueChange
                        ),
                        ExpandedFiledType(
                            title = stringResource(id = R.string.new_email),
                            text = editCredentialParams.newEmailFieldValue,
                            placeholder = stringResource(id = R.string.enter_new_email),
                            onTextValueChange = onNewEmailValueChange
                        )
                    ),
                    buttonTitle = stringResource(id = R.string.email),
                    onButtonClick = onEditEmailClick,
                    message = editCredentialParams.messageEmail,
                    isEnabled = editCredentialParams.isEditEmailEnable
                ),
                ContentRow(
                    title = stringResource(id = R.string.password),
                    label = stringResource(id = R.string.change_password_credential),
                    placeholderRes = R.drawable.ic_change_password,
                    tint = DuskyBlue,
                    expandedTabs = listOf(
                        ExpandedFiledType(
                            title = stringResource(id = R.string.email),
                            text = editCredentialParams.emailFieldValue,
                            placeholder = stringResource(id = R.string.enter_your_email),
                            onTextValueChange = onEmailValueChange
                        )
                    ),
                    buttonTitle = stringResource(id = R.string.password),
                    onButtonClick = onEditPasswordClick,
                    message = editCredentialParams.messagePassword,
                    isEnabled = editCredentialParams.isEditPasswordEnable
                ),
                ContentRow(
                    title = stringResource(id = R.string.delete_account),
                    label = stringResource(id = R.string.delete_your_mindful_mate_account),
                    placeholderRes = R.drawable.ic_change_password,
                    tint = DuskyBlue,
                    isExpandable = false,
                    onArrowClick = onDeleteAccountClick
                ),
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun EditCredentialScreenPreview() {
    MindfulMateTheme { }
}
