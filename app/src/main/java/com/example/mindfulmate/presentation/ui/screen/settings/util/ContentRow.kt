package com.example.mindfulmate.presentation.ui.screen.settings.util

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.mindfulmate.presentation.theme.DuskyBlue

data class ContentRow(
    val title: String,
    val label: String,
    val onRowClick: () -> Unit,
    @DrawableRes val placeholderRes: Int,
    val tint: Color = DuskyBlue,
    val rowType: ContentRowType = ContentRowType.ICON
)
