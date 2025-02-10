package com.example.mindfulmate.presentation.ui.screen.profile.util

import androidx.compose.ui.text.input.TextFieldValue

data class ExpandedFiledType(
    val title: String,
    val text: TextFieldValue,
    val placeholder: String,
    val onTextValueChange: (TextFieldValue) -> Unit
)