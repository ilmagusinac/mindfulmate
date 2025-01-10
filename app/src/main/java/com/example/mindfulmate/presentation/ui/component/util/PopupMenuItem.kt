package com.example.mindfulmate.presentation.ui.component.util

import androidx.compose.ui.graphics.painter.Painter

data class PopupMenuItem(
    val icon: Painter? = null,
    val label: String,
    val onClick: () -> Unit,
)
