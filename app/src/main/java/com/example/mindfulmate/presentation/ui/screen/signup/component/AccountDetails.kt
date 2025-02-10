package com.example.mindfulmate.presentation.ui.screen.signup.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindfulmate.R
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.ui.component.MindfulMateRegistrationTextField
import com.example.mindfulmate.presentation.util.validateEmail
import com.example.mindfulmate.presentation.util.validatePassword
import com.example.mindfulmate.presentation.util.validatePasswordMatch

@Composable
fun AccountDetails(
    emailFieldValue: TextFieldValue,
    passwordFieldValue: TextFieldValue,
    repeatPasswordFieldValue: TextFieldValue,
    onEmailValueChange: (TextFieldValue) -> Unit,
    onPasswordValueChange: (TextFieldValue) -> Unit,
    onRepeatPasswordValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var repeatPasswordError by remember { mutableStateOf<String?>(null) }
    var isTypingInRepeatPassword by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
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
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        MindfulMateRegistrationTextField(
            title = stringResource(id = R.string.password),
            text = passwordFieldValue,
            placeholder = stringResource(id = R.string.enter_password),
            onTextValueChange = {
                passwordError = it.text.validatePassword()
                onPasswordValueChange(it)
                if (isTypingInRepeatPassword) {
                    repeatPasswordError =
                        it.text.validatePasswordMatch(repeatPasswordFieldValue.text)
                }
            },
            isPasswordField = true
        )
        passwordError?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light)
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_default)))
        MindfulMateRegistrationTextField(
            title = stringResource(id = R.string.repeat_password),
            text = repeatPasswordFieldValue,
            placeholder = stringResource(id = R.string.enter_password),
            onTextValueChange = {
                repeatPasswordError = it.text.validatePasswordMatch(passwordFieldValue.toString())
                onRepeatPasswordValueChange(it)
                isTypingInRepeatPassword = true
                repeatPasswordError = passwordFieldValue.text.validatePasswordMatch(it.text)
            },
            isPasswordField = true
        )
        repeatPasswordError?.let {
            Text(
                text = it,
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Light)
            )
        }
    }
}

@Preview
@Composable
private fun AccountDetailsPreview() {
    MindfulMateTheme {
        AccountDetails(
            emailFieldValue = TextFieldValue(),
            passwordFieldValue = TextFieldValue(),
            repeatPasswordFieldValue = TextFieldValue(),
            onEmailValueChange = {},
            onPasswordValueChange = {},
            onRepeatPasswordValueChange = {}
        )
    }
}
