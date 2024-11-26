package com.example.mindfulmate.presentation.navigation.drawer

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateProfileImage

@Composable
fun Drawer(
    username: String,
    onSignOutButtonClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onPrivacyAndPolicyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_xxxmedium),
                    vertical = dimensionResource(id = R.dimen.padding_xxxmedium)
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_medium))
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xdefault)))
            Text(
                text = stringResource(id = R.string.welcome_title),
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Grey,
                    fontSize = 16.sp
                )
            )
        }
        Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_xxxmedium))) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_xlarge)))
            DrawerRow(
                icon = R.drawable.ic_settings,
                title = stringResource(id = R.string.settings_management),
                onDrawerRowClick = onSettingsClick
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xmedium)))
            DrawerRow(
                icon = R.drawable.ic_privacy_policy,
                title = stringResource(id = R.string.privacy_and_policy),
                onDrawerRowClick = onPrivacyAndPolicyClick
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                MindfulMateProfileImage(
                    imageUrl = null,
                    placeholderRes = R.drawable.ic_profile
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xdefault)))
                Text(
                    text = "@$username",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Grey,
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onSignOutButtonClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_signout),
                        tint = Grey,
                        contentDescription = null,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_medium))
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_xxmedium)))
        }
    }
}

@Composable
fun DrawerRow(
    @DrawableRes icon: Int,
    title: String,
    onDrawerRowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onDrawerRowClick() }
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_medium)),
            tint = Grey
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_default)))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Grey,
                fontWeight = FontWeight.W400
            )
        )
    }
}

@Preview
@Composable
private fun DrawerRowPreview() {
    MindfulMateTheme {
        DrawerRow(
            icon = R.drawable.ic_settings,
            title = "Settings Management",
            onDrawerRowClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DrawerPreview() {
    MindfulMateTheme {
        Drawer(
            username = "username",
            onSignOutButtonClick = {},
            onSettingsClick = {},
            onPrivacyAndPolicyClick = {}
        )
    }
}
