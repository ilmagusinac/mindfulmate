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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.signin.component.AccountDetails
import com.example.mindfulmate.presentation.ui.screen.signin.component.AlternativeSignIn
import com.example.mindfulmate.presentation.ui.screen.signin.component.SignInActions
import com.example.mindfulmate.presentation.util.DevicesPreview
import com.example.mindfulmate.presentation.view_model.signin.SignInNavigationEvent
import com.example.mindfulmate.presentation.view_model.signin.SignInUiState
import com.example.mindfulmate.presentation.view_model.signin.SignInViewModel

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    onResetPasswordClick: () -> Unit,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: SignInUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var emailState by remember { mutableStateOf(TextFieldValue("")) }
    var passwordState by remember { mutableStateOf(TextFieldValue("")) }
    val isSignInEnabled by viewModel.isSignInEnabled.collectAsStateWithLifecycle()
    val signInError by viewModel.errorMessage.collectAsStateWithLifecycle()

    NavigationEventHandler(
        viewModel = viewModel,
        navigate = navigate
    )

    LaunchedEffect(uiState) {
        when (uiState) {
            is SignInUiState.Success -> {
                viewModel.resetUiState()
                emailState = TextFieldValue("")
                passwordState = TextFieldValue("")
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
            SignInScreen(
                emailFieldValue = emailState,
                passwordFieldValue = passwordState,
                signInErrorMessage = signInError,
                onEmailValueChange = {
                    emailState = it
                    viewModel.validateInput(emailState.text, passwordState.text)
                                     },
                onPasswordValueChange = {
                    passwordState = it
                    viewModel.validateInput(emailState.text, passwordState.text)
                                        },
                isSignInEnabled = isSignInEnabled,
                onSignInClick = { viewModel.signIn(emailState.text, passwordState.text) },
                onResetPasswordClick = onResetPasswordClick,
                onSignInWithGoogleClick = { viewModel.startGoogleSignIn(context) },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun NavigationEventHandler(
    viewModel: SignInViewModel,
    navigate: () -> Unit,
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
private fun SignInScreen(
    emailFieldValue: TextFieldValue,
    passwordFieldValue: TextFieldValue,
    isSignInEnabled: Boolean,
    onEmailValueChange: (TextFieldValue) -> Unit,
    onPasswordValueChange: (TextFieldValue) -> Unit,
    onSignInClick: () -> Unit,
    onResetPasswordClick: () -> Unit,
    onSignInWithGoogleClick: () -> Unit,
    modifier: Modifier = Modifier,
    signInErrorMessage: String? = null,
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
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
        AccountDetails(
            emailFieldValue = emailFieldValue,
            onEmailValueChange = onEmailValueChange,
            passwordFieldValue = passwordFieldValue,
            onPasswordValueChange = onPasswordValueChange
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))
        SignInActions(
            onSignInClick = onSignInClick,
            onResetPasswordClick = onResetPasswordClick,
            isSignInEnabled = isSignInEnabled,
            errorMessage = signInErrorMessage

        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxmedium)))
        AlternativeSignIn(
            onSignInWithGoogleClick = onSignInWithGoogleClick
        )
    }
}

@DevicesPreview
@Composable
private fun SignInScreenPreview() {
    MindfulMateTheme {
        SignInScreen(
            emailFieldValue = TextFieldValue(),
            passwordFieldValue = TextFieldValue(),
            onEmailValueChange = {},
            onPasswordValueChange = {},
            isSignInEnabled = true,
            onSignInClick = {},
            onResetPasswordClick = {},
            onSignInWithGoogleClick = {}
        )
    }
}
