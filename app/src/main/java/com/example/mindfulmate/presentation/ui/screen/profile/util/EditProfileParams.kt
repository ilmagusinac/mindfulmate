package com.example.mindfulmate.presentation.ui.screen.profile.util

import androidx.compose.ui.text.input.TextFieldValue

data class EditProfileParams(
    val firstNameFieldValue: TextFieldValue = TextFieldValue(),
    val lastNameFieldValue: TextFieldValue = TextFieldValue(),
    val usernameFieldValue: TextFieldValue = TextFieldValue(),
    val emailFieldValue: TextFieldValue = TextFieldValue(),
    val numberFieldValue: TextFieldValue = TextFieldValue(),
    val currentPasswordFieldValue: TextFieldValue = TextFieldValue(),
    val newPasswordFieldValue: TextFieldValue = TextFieldValue(),
    val repeatNewPasswordFieldValue: TextFieldValue = TextFieldValue(),
    val onFirstNameValueChange: (TextFieldValue) -> Unit = {},
    val onLastNameValueChange: (TextFieldValue) -> Unit = {},
    val onUsernameValueChange: (TextFieldValue) -> Unit = {},
    val onEmailValueChange: (TextFieldValue) -> Unit = {},
    val onNumberValueChange: (TextFieldValue) -> Unit = {},
    val onCurrentPasswordValueChange: (TextFieldValue) -> Unit = {},
    val onNewPasswordValueChange: (TextFieldValue) -> Unit = {},
    val onRepeatNewPasswordFieldValueChange: (TextFieldValue) -> Unit = {},
)
