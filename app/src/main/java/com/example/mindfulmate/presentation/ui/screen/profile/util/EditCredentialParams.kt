package com.example.mindfulmate.presentation.ui.screen.profile.util

import androidx.compose.ui.text.input.TextFieldValue

data class EditCredentialParams(
    val currentPasswordFieldValue: TextFieldValue = TextFieldValue(),
    val currentEmailFieldValue: TextFieldValue = TextFieldValue(),
    val emailFieldValue: TextFieldValue = TextFieldValue(),
    val newEmailFieldValue: TextFieldValue = TextFieldValue(),
    val messagePassword: String? = null,
    val messageEmail: String? = null,
    val isEditEmailEnable: Boolean = true,
    val isEditPasswordEnable: Boolean= true
)
