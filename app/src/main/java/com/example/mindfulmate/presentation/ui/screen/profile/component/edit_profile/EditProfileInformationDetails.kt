package com.example.mindfulmate.presentation.ui.screen.profile.component.edit_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.Grey
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateButton
import com.example.mindfulmate.presentation.ui.component.MindfulMateRegistrationTextField
import com.example.mindfulmate.presentation.util.validateEmail

@Composable
fun EditProfileInformationDetails(
    firstNameFieldValue: TextFieldValue,
    lastNameFieldValue: TextFieldValue,
    usernameFieldValue: TextFieldValue,
    //emailFieldValue: TextFieldValue,
    numberFieldValue: TextFieldValue,
    onResetPasswordClick: () -> Unit,
    onFirstNameValueChange: (TextFieldValue) -> Unit,
    onLastNameValueChange: (TextFieldValue) -> Unit,
    onUsernameValueChange: (TextFieldValue) -> Unit,
    //onEmailValueChange: (TextFieldValue) -> Unit,
    onNumberValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    var emailError by remember { mutableStateOf<String?>(null) }

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
        /*
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        MindfulMateRegistrationTextField(
            title = stringResource(id = R.string.email),
            text = emailFieldValue,
            placeholder = stringResource(id = R.string.enter_email),
            onTextValueChange = {
                emailError = it.text.validateEmail()
                onEmailValueChange(it)
            }
        )
        emailError?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light)
            )
        }
         */
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        MindfulMateRegistrationTextField(
            title = stringResource(id = R.string.number),
            text = numberFieldValue,
            placeholder = stringResource(id = R.string.enter_number),
            onTextValueChange = { onNumberValueChange(it) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.reset_password_option),
                style = MaterialTheme.typography.titleMedium.copy(color = Grey, fontSize = 12.sp)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))
            MindfulMateButton(
                onClick = onResetPasswordClick,
                text = stringResource(id = R.string.reset_password),
                textPadding = PaddingValues(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(id = R.dimen.padding_xsmall)
                ),
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
                borderColor = Grey,
                textColor = Grey,
                textStyle = MaterialTheme.typography.labelSmall.copy(color = Grey)
            )
        }
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
            //emailFieldValue = TextFieldValue(),
            numberFieldValue = TextFieldValue(),
            onResetPasswordClick = {},
            onFirstNameValueChange = {},
            onLastNameValueChange = {},
            onUsernameValueChange = {},
            //onEmailValueChange = {},
            onNumberValueChange = {}
        )
    }
}
