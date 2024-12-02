package com.example.mindfulmate.presentation.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMatePopupDialog
import com.example.mindfulmate.presentation.util.DialogButtonConfig
import com.example.mindfulmate.presentation.view_model.profile.delete_account.DeleteAccountNavigationEvent
import com.example.mindfulmate.presentation.view_model.profile.delete_account.DeleteAccountUiState
import com.example.mindfulmate.presentation.view_model.profile.delete_account.DeleteAccountViewModel

@Composable
fun DeleteAccountScreen(
    viewModel: DeleteAccountViewModel,
    navigate: () -> Unit,
    onGoBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavigationEventHandler(
        viewModel = viewModel,
        navigate = navigate
    )

    when (uiState) {
        is DeleteAccountUiState.Loading -> {
            LoadingPlaceholder()
        }

        is DeleteAccountUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = {})
        }

        else -> {
            DeleteAccountScreen(
                onDeleteAccountClick = { viewModel.deleteAccount() },
                onCancelDeleteAccountClick = onGoBackClick,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun NavigationEventHandler(
    viewModel: DeleteAccountViewModel,
    navigate: () -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                is DeleteAccountNavigationEvent.Navigate -> {
                    navigate()
                }
            }
        }
    }
}

@Composable
private fun DeleteAccountScreen(
    onDeleteAccountClick: () -> Unit,
    onCancelDeleteAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MindfulMatePopupDialog(
            dialogTitle = "Delete Account",
            dialogText = "Sorry to here this :(! Are you sure of this action?",
            buttons = listOf(
                DialogButtonConfig(
                    text = "Cancel",
                    onConfirmationClick = onCancelDeleteAccountClick,
                    isPrimary = false
                ),
                DialogButtonConfig(
                    text = "Delete",
                    onConfirmationClick = onDeleteAccountClick,
                    isPrimary = true
                )
            ),
            onDismissRequest = {},
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.spacing_xxxxmlarge),
                start = dimensionResource(id = R.dimen.padding_default),
                end = dimensionResource(id = R.dimen.padding_default)
            )
        )
    }
}

@Preview
@Composable
private fun DeleteAccountScreenPreview() {
    MindfulMateTheme {
        DeleteAccountScreen(
            onDeleteAccountClick = {},
            onCancelDeleteAccountClick = {}
        )
    }
}
