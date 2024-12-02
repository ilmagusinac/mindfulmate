package com.example.mindfulmate.presentation.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.profile.component.profile.ProfileHeaderSection
import com.example.mindfulmate.presentation.ui.screen.profile.component.profile.ProfileInformationSection
import com.example.mindfulmate.presentation.ui.screen.profile.component.profile.ProfileSection
import com.example.mindfulmate.presentation.ui.screen.profile.util.ProfileInformation
import com.example.mindfulmate.presentation.ui.screen.profile.util.ProfileParams
import com.example.mindfulmate.presentation.view_model.profile.ProfileUiState
import com.example.mindfulmate.presentation.view_model.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onGoBackClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: ProfileUiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is ProfileUiState.Loading -> {
            LoadingPlaceholder()
        }

        is ProfileUiState.Failure -> {
            ErrorPlaceholder(onConfirmationClick = {})
        }

        is ProfileUiState.Success -> {
            ProfileScreen(
                profileParams = (uiState as ProfileUiState.Success).profileParams,
                onGoBackClick = onGoBackClick,
                onEditProfileClick = onEditProfileClick,
                modifier = modifier
            )
        }

        else -> {
            // no-op
        }
    }
}

@Composable
private fun ProfileScreen(
    profileParams: ProfileParams,
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
                horizontal = dimensionResource(id = R.dimen.padding_default)
            )
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeaderSection(
            onGoBackClick = onGoBackClick,
            onEditProfileClick = onEditProfileClick
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))
        ProfileSection(
            firstName = profileParams.firstName,
            lastName = profileParams.lastName,
            username = profileParams.username
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxxmedium)))
        ProfileInformationSection(
            profileInformation = listOf(
                ProfileInformation(
                    title = stringResource(id = R.string.first_name),
                    label = profileParams.firstName
                ),
                ProfileInformation(
                    title = stringResource(id = R.string.last_name),
                    label = profileParams.lastName
                ),
                ProfileInformation(
                    title = stringResource(id = R.string.username),
                    label = profileParams.username
                ),
                ProfileInformation(
                    title = stringResource(id = R.string.email),
                    label = profileParams.email
                ),
                ProfileInformation(
                    title = stringResource(id = R.string.number),
                    label = profileParams.number
                )
            )
        )
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    MindfulMateTheme {
        ProfileScreen(
            profileParams = ProfileParams(
                firstName = "Ilma",
                lastName = "Gusinac",
                username = "username",
                email = "username@gmail.com",
                number = "+387 123 456",
            ),
            onGoBackClick = {},
            onEditProfileClick = {}
        )
    }
}
