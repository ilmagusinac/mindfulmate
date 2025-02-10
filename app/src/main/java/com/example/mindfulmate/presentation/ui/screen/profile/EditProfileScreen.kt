package com.example.mindfulmate.presentation.ui.screen.profile

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton
import com.example.mindfulmate.presentation.ui.screen.profile.component.edit_profile.EditProfileHeaderSection
import com.example.mindfulmate.presentation.ui.screen.profile.component.edit_profile.EditProfileInformationDetails
import com.example.mindfulmate.presentation.ui.screen.profile.component.edit_profile.EditProfileSection
import com.example.mindfulmate.presentation.ui.screen.profile.util.EditProfileParams
import com.example.mindfulmate.presentation.view_model.profile.edit_profile.EditProfileNavigationEvent
import com.example.mindfulmate.presentation.view_model.profile.edit_profile.EditProfileUiState
import com.example.mindfulmate.presentation.view_model.profile.edit_profile.EditProfileViewModel

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel,
    onGoBackClick: () -> Unit,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: EditProfileUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedImageUri by viewModel.selectedImageUri.collectAsStateWithLifecycle()
    val uploadState by viewModel.uploadState.collectAsStateWithLifecycle()

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.onImageSelected(uri)
                Log.d("PhotoPicker", "Selected URI: $uri")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    var firstNameState by remember { mutableStateOf(TextFieldValue()) }
    var lastNameState by remember { mutableStateOf(TextFieldValue()) }
    var usernameState by remember { mutableStateOf(TextFieldValue()) }
    var emailState by remember { mutableStateOf(TextFieldValue()) }
    var numberState by remember { mutableStateOf(TextFieldValue()) }
    val profileImageUrl = uploadState ?: selectedImageUri?.toString()
    ?: (uiState as? EditProfileUiState.Success)?.editProfileParams?.imageUrl
    val usernameError by viewModel.usernameError.collectAsStateWithLifecycle()
    val phoneError by viewModel.phoneError.collectAsStateWithLifecycle()
    val isSaveButtonEnabled by viewModel.isSaveButtonEnabled.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is EditProfileUiState.Success) {
            val successState = uiState as EditProfileUiState.Success
            firstNameState = successState.editProfileParams.firstNameFieldValue
            lastNameState = successState.editProfileParams.lastNameFieldValue
            usernameState = successState.editProfileParams.usernameFieldValue
            emailState = successState.editProfileParams.emailFieldValue
            numberState = successState.editProfileParams.numberFieldValue
        }
    }

    NavigationEventHandler(
        viewModel = viewModel,
        navigate = navigate
    )

    when (uiState) {
        is EditProfileUiState.Loading -> {
            LoadingPlaceholder()
        }

        is EditProfileUiState.Failure -> {
            ErrorPlaceholder(
                onConfirmationClick = {}
            )
        }

        is EditProfileUiState.Success -> {
            EditProfileScreen(
                editProfileParams = EditProfileParams(
                    imageUrl = profileImageUrl,
                    firstNameFieldValue = firstNameState,
                    lastNameFieldValue = lastNameState,
                    usernameFieldValue = usernameState,
                    emailFieldValue = emailState,
                    numberFieldValue = numberState,
                    onFirstNameValueChange = { firstNameState = it },
                    onLastNameValueChange = { lastNameState = it },
                    onUsernameValueChange = {
                        usernameState = it
                        viewModel.validateUsername(it.text)
                                            },
                    onEmailValueChange = { emailState = it },
                    onNumberValueChange = {
                        numberState = it
                        viewModel.validatePhoneNumber(it.text)
                    }
                ),
                onGoBackClick = onGoBackClick,
                onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                onEditProfileClick = {
                    viewModel.updateUser(
                        EditProfileParams(
                            imageUrl = profileImageUrl,
                            firstNameFieldValue = firstNameState,
                            lastNameFieldValue = lastNameState,
                            usernameFieldValue = usernameState,
                            emailFieldValue = emailState,
                            numberFieldValue = numberState
                        )
                    )
                },
                usernameError = usernameError,
                phoneError = phoneError,
                isSaveButtonEnabled = isSaveButtonEnabled,
                modifier = modifier
            )
        }

        else -> {
            // no-op
        }
    }
}

@Composable
private fun NavigationEventHandler(
    viewModel: EditProfileViewModel,
    navigate: () -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                is EditProfileNavigationEvent.Navigate -> {
                    navigate()
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun EditProfileScreen(
    editProfileParams: EditProfileParams,
    onClick: () -> Unit,
    onGoBackClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
    usernameError: String ? = null,
    phoneError: String ? = null,
    isSaveButtonEnabled: Boolean = true
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                horizontal = dimensionResource(id = R.dimen.padding_medium)
            )
            .verticalScroll(rememberScrollState())
    ) {
        EditProfileHeaderSection(onGoBackClick = onGoBackClick)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxmedium)))
        EditProfileSection(
            imageUrl = editProfileParams.imageUrl,
            onClick = onClick
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxmedium)))
        EditProfileInformationDetails(
            firstNameFieldValue = editProfileParams.firstNameFieldValue,
            lastNameFieldValue = editProfileParams.lastNameFieldValue,
            usernameFieldValue = editProfileParams.usernameFieldValue,
            numberFieldValue = editProfileParams.numberFieldValue,
            onFirstNameValueChange = editProfileParams.onFirstNameValueChange,
            onLastNameValueChange = editProfileParams.onLastNameValueChange,
            onUsernameValueChange = editProfileParams.onUsernameValueChange,
            onNumberValueChange = editProfileParams.onNumberValueChange,
            usernameError = usernameError,
            phoneError = phoneError
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        MindfulMateButton(
            onClick = onEditProfileClick,
            text = stringResource(id = R.string.save_edit),
            enabled = isSaveButtonEnabled
        )
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    MindfulMateTheme {
        EditProfileScreen(
            editProfileParams = EditProfileParams(
                imageUrl = null,
                firstNameFieldValue = TextFieldValue(),
                lastNameFieldValue = TextFieldValue(),
                usernameFieldValue = TextFieldValue(),
                emailFieldValue = TextFieldValue(),
                numberFieldValue = TextFieldValue(),
                currentPasswordFieldValue = TextFieldValue(),
                newPasswordFieldValue = TextFieldValue(),
                repeatNewPasswordFieldValue = TextFieldValue(),
                onFirstNameValueChange = {},
                onLastNameValueChange = {},
                onUsernameValueChange = {},
                onEmailValueChange = {},
                onNumberValueChange = {},
                onCurrentPasswordValueChange = {},
                onNewPasswordValueChange = {},
                onRepeatNewPasswordFieldValueChange = {}
            ),
            onClick = {},
            onGoBackClick = {},
            onEditProfileClick = {}
        )
    }
}
