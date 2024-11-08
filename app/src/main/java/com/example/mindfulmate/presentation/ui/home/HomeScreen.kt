package com.example.mindfulmate.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton
import com.example.mindfulmate.presentation.util.DevicesPreview
import com.example.mindfulmate.presentation.view_model.signin.SignInNavigationEvent
import com.example.mindfulmate.presentation.view_model.signin.SignInUiState
import com.example.mindfulmate.presentation.view_model.signin.SignInViewModel

@Composable
fun HomeScreen(
    viewModel: SignInViewModel,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: SignInUiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavigationEventHandler(
        viewModel = viewModel,
        navigate = navigate
    )

    when (uiState) {
        is SignInUiState.Loading -> {
            LoadingPlaceholder()
        }

        is SignInUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = {/*TODO*/ })
        }

        else -> {
            HomeScreen(
                onSignOutClick = { viewModel.signOut() },
                onDeleteAccountClick = { viewModel.deleteAccount() },
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
                is SignInNavigationEvent.NavigateBack -> {
                    navigate()
                }

                is SignInNavigationEvent.Navigate -> TODO()
            }
        }
    }
}

@Composable
private fun HomeScreen(
    onSignOutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_xxxmedium))
    ) {
        Text(
            text = "HOME SCREEN",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        MindfulMateButton(
            text = "SIGN OUT",
            onClick = onSignOutClick
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        MindfulMateButton(
            text = "DELETE ACCOUNT",
            onClick = onDeleteAccountClick
        )
    }
}

@DevicesPreview
@Composable
private fun HomeScreenPreview() {
    MindfulMateTheme {
        HomeScreen(
            onSignOutClick = {},
            onDeleteAccountClick = {}
        )
    }
}
