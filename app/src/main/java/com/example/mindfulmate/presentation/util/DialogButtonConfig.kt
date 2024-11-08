package com.example.mindfulmate.presentation.util

data class DialogButtonConfig(
    val text: String,
    val onConfirmationClick: () -> Unit,
    val isPrimary: Boolean = true
)