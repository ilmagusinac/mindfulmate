package com.example.mindfulmate.presentation.ui.screen.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.signup.component.AccountDetails
import com.example.mindfulmate.presentation.ui.screen.signup.component.AlternativeSignUp
import com.example.mindfulmate.presentation.ui.screen.signup.component.SignUpActions
import com.example.mindfulmate.presentation.util.DevicesPreview
import com.example.mindfulmate.presentation.view_model.signup.SignUpNavigationEvent
import com.example.mindfulmate.presentation.view_model.signup.SignUpUiState
import com.example.mindfulmate.presentation.view_model.signup.SignUpViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onSignInClick: () -> Unit,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: SignUpUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var emailState by remember { mutableStateOf(TextFieldValue()) }
    var passwordState by remember { mutableStateOf(TextFieldValue()) }
    var repeatPasswordState by remember { mutableStateOf(TextFieldValue()) }
    val isSignUpEnabled by viewModel.isSignUpEnabled.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    NavigationEventHandler(
        viewModel = viewModel,
        navigate = navigate
    )

    LaunchedEffect(uiState) {
        when (uiState) {
            is SignUpUiState.Success -> {
                viewModel.resetUiState()
                emailState = TextFieldValue("")
                passwordState = TextFieldValue("")
                repeatPasswordState = TextFieldValue("")
            }

            is SignUpUiState.Failure -> {
                viewModel.resetUiState()
            }

            else -> Unit
        }
    }

    when (uiState) {
        is SignUpUiState.Loading -> {
            LoadingPlaceholder()
        }

        else -> {
            SignUpScreen(
                emailFieldValue = emailState,
                passwordFieldValue = passwordState,
                repeatPasswordFieldValue = repeatPasswordState,
                isSignUpEnabled = isSignUpEnabled,
                onEmailValueChange = {
                    emailState = it
                    viewModel.validateInput(emailState.text, passwordState.text, repeatPasswordState.text)
                                     },
                onPasswordValueChange = {
                    passwordState = it
                    viewModel.validateInput(emailState.text, passwordState.text, repeatPasswordState.text)
                                        },
                onRepeatPasswordValueChange = {
                    repeatPasswordState = it
                    viewModel.validateInput(emailState.text, passwordState.text, repeatPasswordState.text)
                                              },
                onSignUpClick = {
                    viewModel.signUp(
                        email = emailState.text,
                        password = passwordState.text,
                        confirmPassword = repeatPasswordState.text
                    )
                },
                onSignInClick = onSignInClick,
                onSignUpWithGoogleClick = { viewModel.startGoogleSignUp(context) },
                modifier = modifier,
                signUpErrorMessage = errorMessage
            )
        }
    }
}

@Composable
private fun NavigationEventHandler(
    viewModel: SignUpViewModel,
    navigate: () -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                is SignUpNavigationEvent.Navigate -> {
                    navigate()
                }
            }
        }
    }
}

@Composable
private fun SignUpScreen(
    emailFieldValue: TextFieldValue,
    passwordFieldValue: TextFieldValue,
    repeatPasswordFieldValue: TextFieldValue,
    isSignUpEnabled: Boolean,
    onEmailValueChange: (TextFieldValue) -> Unit,
    onPasswordValueChange: (TextFieldValue) -> Unit,
    onRepeatPasswordValueChange: (TextFieldValue) -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpWithGoogleClick: () -> Unit,
    modifier: Modifier = Modifier,
    signUpErrorMessage: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_xxxmedium),
                horizontal = dimensionResource(id = R.dimen.padding_xxxmedium)
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_heart),
            contentDescription = stringResource(id = R.string.logo_content_description),
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        AccountDetails(
            emailFieldValue = emailFieldValue,
            passwordFieldValue = passwordFieldValue,
            repeatPasswordFieldValue = repeatPasswordFieldValue,
            onEmailValueChange = onEmailValueChange,
            onPasswordValueChange = onPasswordValueChange,
            onRepeatPasswordValueChange = onRepeatPasswordValueChange
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxmedium)))
        SignUpActions(
            onSignUpClick = onSignUpClick,
            onSignInClick = onSignInClick,
            isSignUpEnabled = isSignUpEnabled,
            errorMessage = signUpErrorMessage
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxmedium)))
        AlternativeSignUp(
            onSignUpWithGoogleClick = onSignUpWithGoogleClick
        )
    }
}

@DevicesPreview
@Composable
private fun SignUpScreenPreview() {
    MindfulMateTheme {
        SignUpScreen(
            emailFieldValue = TextFieldValue(),
            passwordFieldValue = TextFieldValue(),
            repeatPasswordFieldValue = TextFieldValue(),
            isSignUpEnabled = true,
            onEmailValueChange = {},
            onPasswordValueChange = {},
            onRepeatPasswordValueChange = {},
            onSignUpClick = {},
            onSignInClick = {},
            onSignUpWithGoogleClick = {}
        )
    }
}
