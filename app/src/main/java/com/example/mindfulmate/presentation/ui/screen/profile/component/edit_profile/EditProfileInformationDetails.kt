package com.example.mindfulmate.presentation.ui.screen.profile.component.edit_profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateRegistrationTextField

@Composable
fun EditProfileInformationDetails(
    firstNameFieldValue: TextFieldValue,
    lastNameFieldValue: TextFieldValue,
    usernameFieldValue: TextFieldValue,
    numberFieldValue: TextFieldValue,
    onFirstNameValueChange: (TextFieldValue) -> Unit,
    onLastNameValueChange: (TextFieldValue) -> Unit,
    onUsernameValueChange: (TextFieldValue) -> Unit,
    onNumberValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        MindfulMateRegistrationTextField(
            title = stringResource(id = R.string.first_name),
            text = firstNameFieldValue,
            placeholder = stringResource(id = R.string.enter_first_name),
            onTextValueChange = { onFirstNameValueChange(it) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        MindfulMateRegistrationTextField(
            title = stringResource(id = R.string.last_name),
            text = lastNameFieldValue,
            placeholder = stringResource(id = R.string.enter_last_name),
            onTextValueChange = { onLastNameValueChange(it) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        MindfulMateRegistrationTextField(
            title = stringResource(id = R.string.username),
            text = usernameFieldValue,
            placeholder = stringResource(id = R.string.enter_username),
            onTextValueChange = { onUsernameValueChange(it) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        MindfulMateRegistrationTextField(
            title = stringResource(id = R.string.number),
            text = numberFieldValue,
            placeholder = stringResource(id = R.string.enter_number),
            onTextValueChange = { onNumberValueChange(it) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
    }
}

@Preview
@Composable
private fun AccountDetailsPreview() {
    MindfulMateTheme {
        EditProfileInformationDetails(
            firstNameFieldValue = TextFieldValue(),
            lastNameFieldValue = TextFieldValue(),
            usernameFieldValue = TextFieldValue(),
            numberFieldValue = TextFieldValue(),
            onFirstNameValueChange = {},
            onLastNameValueChange = {},
            onUsernameValueChange = {},
            onNumberValueChange = {}
        )
    }
}
