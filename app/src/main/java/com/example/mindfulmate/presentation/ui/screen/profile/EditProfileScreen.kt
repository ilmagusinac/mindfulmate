package com.example.mindfulmate.presentation.ui.screen.profile

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

    var firstNameState by remember { mutableStateOf(TextFieldValue()) }
    var lastNameState by remember { mutableStateOf(TextFieldValue()) }
    var usernameState by remember { mutableStateOf(TextFieldValue()) }
    var emailState by remember { mutableStateOf(TextFieldValue()) }
    var numberState by remember { mutableStateOf(TextFieldValue()) }

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
                    firstNameFieldValue = firstNameState,
                    lastNameFieldValue = lastNameState,
                    usernameFieldValue = usernameState,
                    emailFieldValue = emailState,
                    numberFieldValue = numberState,
                    onFirstNameValueChange = { firstNameState = it },
                    onLastNameValueChange = { lastNameState = it },
                    onUsernameValueChange = { usernameState = it },
                    onEmailValueChange = { emailState = it },
                    onNumberValueChange = { numberState = it }
                ),
                onGoBackClick = onGoBackClick,
                onEditProfileClick = {
                    viewModel.updateUser(
                        EditProfileParams(
                            firstNameFieldValue = firstNameState,
                            lastNameFieldValue = lastNameState,
                            usernameFieldValue = usernameState,
                            emailFieldValue = emailState,
                            numberFieldValue = numberState
                        )
                    )
                },
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
            }
        }
    }
}

@Composable
private fun EditProfileScreen(
    editProfileParams: EditProfileParams,
    onGoBackClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    modifier: Modifier = Modifier
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
        EditProfileSection()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxmedium)))
        EditProfileInformationDetails(
            firstNameFieldValue = editProfileParams.firstNameFieldValue,
            lastNameFieldValue = editProfileParams.lastNameFieldValue,
            usernameFieldValue = editProfileParams.usernameFieldValue,
            numberFieldValue = editProfileParams.numberFieldValue,
            onFirstNameValueChange = editProfileParams.onFirstNameValueChange,
            onLastNameValueChange = editProfileParams.onLastNameValueChange,
            onUsernameValueChange = editProfileParams.onUsernameValueChange,
            onNumberValueChange = editProfileParams.onNumberValueChange
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        MindfulMateButton(
            onClick = onEditProfileClick,
            text = stringResource(id = R.string.save_edit)
        )
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    MindfulMateTheme {
        EditProfileScreen(
            editProfileParams = EditProfileParams(
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
            onGoBackClick = {},
            onEditProfileClick = {}
        )
    }
}
