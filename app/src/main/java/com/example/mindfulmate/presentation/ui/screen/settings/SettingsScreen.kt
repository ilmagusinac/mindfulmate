package com.example.mindfulmate.presentation.ui.screen.settings

import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.component.MindfulMateMainHeaderSection
import com.example.mindfulmate.presentation.ui.screen.settings.util.ContentRow
import com.example.mindfulmate.presentation.ui.screen.settings.component.ContentSection
import com.example.mindfulmate.presentation.ui.screen.settings.component.ProfileTab
import com.example.mindfulmate.presentation.ui.screen.settings.util.ContentRowType
import com.example.mindfulmate.presentation.ui.screen.settings.util.SettingsParams
import com.example.mindfulmate.presentation.view_model.settings.SettingsNavigationEvent
import com.example.mindfulmate.presentation.view_model.settings.SettingsUiState
import com.example.mindfulmate.presentation.view_model.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onGoToProfileClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onEditCredentialsClick: () -> Unit,
    onAboutAppClick: () -> Unit,
    onHelpSupportClick: () -> Unit,
    onEmergencyContactSupportClick: () -> Unit,
    navigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState: SettingsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isNotificationsEnabled by viewModel.isNotificationsEnabled.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    NavigationEventHandler(
        viewModel = viewModel,
        navigate = navigate
    )

    when (uiState) {
        is SettingsUiState.Loading -> {
            LoadingPlaceholder()
        }

        is SettingsUiState.Failure -> {
            ErrorPlaceholder(
                onConfirmationClick = {}
            )
        }

        is SettingsUiState.Success -> {
            SettingsScreen(
                settingsParams = (uiState as SettingsUiState.Success).settingsParams,
                onEditCredentialsClick = onEditCredentialsClick,
                isNotificationsEnabled = isNotificationsEnabled,
                onNotificationsReminders = { viewModel.toggleNotifications()},
                onEmergencyContactSupportClick = onEmergencyContactSupportClick,
                onSignOutClick = { viewModel.signOut() },
                onHelpSupportClick = onHelpSupportClick,
                onAboutAppClick = onAboutAppClick,
                onGoToProfileClick = onGoToProfileClick,
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
private fun NavigationEventHandler(
    viewModel: SettingsViewModel,
    navigate: () -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.navigationEvent.collect { navigationEvent ->
            when (navigationEvent) {
                is SettingsNavigationEvent.Navigate -> {
                    navigate()
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen(
    settingsParams: SettingsParams,
    isNotificationsEnabled: Boolean,
    onEditCredentialsClick: () -> Unit,
    onNotificationsReminders: () -> Unit,
    onEmergencyContactSupportClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onHelpSupportClick: () -> Unit,
    onAboutAppClick: () -> Unit,
    onGoToProfileClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_xxmedium),
                horizontal = dimensionResource(id = R.dimen.padding_default)
            )
            .verticalScroll(rememberScrollState())
    ) {
        MindfulMateMainHeaderSection(
            iconRes = R.drawable.ic_profile,
            title = stringResource(id = R.string.profile)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        ProfileTab(
            firstName = settingsParams.firstName,
            lastName = settingsParams.lastName,
            username = settingsParams.username,
            profileImageUrl = settingsParams.profilePicture,
            onProfileTabClick = onGoToProfileClick
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        ContentSection(
            tabs = listOf(
                ContentRow(
                    title = stringResource(id = R.string.my_account_title),
                    label = stringResource(id = R.string.my_account_label),
                    onRowClick = onEditProfileClick,
                    placeholderRes = R.drawable.ic_profile
                ),
                ContentRow(
                    title = stringResource(id = R.string.credentials_change_title),
                    label = stringResource(id = R.string.credentials_change_label),
                    onRowClick = onEditCredentialsClick,
                    placeholderRes = R.drawable.ic_account_change
                ),
                ContentRow(
                    title = stringResource(id = R.string.notification_reminder_title),
                    label = stringResource(id = R.string.notification_reminder_label),
                    onRowClick = onNotificationsReminders,
                    placeholderRes = R.drawable.ic_notification,
                    rowType = ContentRowType.SWITCH,
                    switchState = isNotificationsEnabled

                ),
                ContentRow(
                    title = stringResource(id = R.string.emergency_contact_title),
                    label = stringResource(id = R.string.emergency_contact_label),
                    onRowClick = onEmergencyContactSupportClick,
                    placeholderRes = R.drawable.ic_emergency_contact
                ),
                ContentRow(
                    title = stringResource(id = R.string.sign_out_title),
                    label = stringResource(id = R.string.sign_out_label),
                    onRowClick = onSignOutClick,
                    placeholderRes = R.drawable.ic_signout,
                    tint = Grey
                )
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
        Text(
            text = stringResource(id = R.string.more),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Grey
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
        ContentSection(
            tabs = listOf(
                ContentRow(
                    title = stringResource(id = R.string.help_support_title),
                    label = stringResource(id = R.string.help_support_label),
                    onRowClick = onHelpSupportClick,
                    placeholderRes = R.drawable.ic_help_support
                ),
                ContentRow(
                    title = stringResource(id = R.string.about_app_title),
                    label = stringResource(id = R.string.about_app_label),
                    onRowClick = onAboutAppClick,
                    placeholderRes = R.drawable.ic_heart
                )
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xdefault)))
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    MindfulMateTheme {
        SettingsScreen(
            settingsParams = SettingsParams(
                firstName = "",
                lastName = "",
                username = ""
            ),
            isNotificationsEnabled = true,
            onNotificationsReminders = {},
            onEditCredentialsClick = {},
            onEmergencyContactSupportClick = {},
            onSignOutClick = {},
            onHelpSupportClick = {},
            onAboutAppClick = {},
            onGoToProfileClick = {},
            onEditProfileClick = {}
        )
    }
}
