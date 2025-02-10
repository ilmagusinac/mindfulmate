package com.example.mindfulmate.presentation.ui.screen.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMatePopupTextField
import com.example.mindfulmate.presentation.util.DialogButtonConfig
import com.example.mindfulmate.presentation.view_model.signin.SignInNavigationEvent
import com.example.mindfulmate.presentation.view_model.signin.SignInUiState
import com.example.mindfulmate.presentation.view_model.signin.SignInViewModel

@Composable
fun ResetPassword(
    viewModel: SignInViewModel,
    onCancelClick: () -> Unit,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: SignInUiState by viewModel.uiState.collectAsStateWithLifecycle()
    var resetPasswordState by remember { mutableStateOf(TextFieldValue("")) }
    val resetPassword by viewModel.resetPassword.collectAsStateWithLifecycle()

    NavigationEventHandler(
        viewModel = viewModel,
        navigate = navigate
    )

    LaunchedEffect(uiState) {
        when (uiState) {
            is SignInUiState.Success -> {
                viewModel.resetUiState()
                resetPasswordState = TextFieldValue("")
            }

            is SignInUiState.Failure -> {
                viewModel.resetUiState()
            }

            else -> Unit
        }
    }

    when (uiState) {
        is SignInUiState.Loading -> {
            LoadingPlaceholder()
        }

        else -> {
            ResetPassword(
                resetPassword = resetPasswordState,
                onConfirmationClick = { viewModel.resetPassword(resetPasswordState.text) },
                onCancelClick = onCancelClick,
                onResetPasswordValueChange = { resetPasswordState = it },
                modifier = modifier,
                emailMessageCheck = resetPassword
            )
        }
    }
}

@Composable
private fun NavigationEventHandler(
    viewModel: SignInViewModel,
    navigate: () -> Unit
) {
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                is SignInNavigationEvent.Navigate -> {
                    navigate()
                }

                is SignInNavigationEvent.NavigateBack -> TODO()
            }
        }
    }
}

@Composable
private fun ResetPassword(
    resetPassword: TextFieldValue,
    onConfirmationClick: () -> Unit,
    onCancelClick: () -> Unit,
    onResetPasswordValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    emailMessageCheck: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xxxmedium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = stringResource(id = R.string.logo_content_description),
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxmedium)))
        MindfulMatePopupTextField(
            dialogTitle = stringResource(id = R.string.reset_password),
            placeholder = stringResource(id = R.string.enter_your_email),
            text = resetPassword,
            buttons = listOf(
                DialogButtonConfig(
                    text = stringResource(id = R.string.reset),
                    onConfirmationClick = onConfirmationClick,
                    isPrimary = true
                ),
                DialogButtonConfig(
                    text = stringResource(id = R.string.cancel),
                    onConfirmationClick = onCancelClick,
                    isPrimary = false
                )
            ),
            onTextValueChange = onResetPasswordValueChange,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
            message = emailMessageCheck
        )
    }
}

@Preview
@Composable
private fun ResetPasswordPreview() {
    MindfulMateTheme {
        var resetPasswordState by remember { mutableStateOf(TextFieldValue("")) }

        ResetPassword(
            resetPassword = resetPasswordState,
            onConfirmationClick = {},
            onCancelClick = {},
            onResetPasswordValueChange = { resetPasswordState = it },
        )
    }
}
