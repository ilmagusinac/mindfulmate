package com.example.mindfulmate.presentation.ui.screen.about_app.util

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.mindfulmate.R

data class AboutAppContentRow(
    val title: String,
    val expandedTabs: List<ContentRowType> = listOf(),
    val isExpandable: Boolean = true,
    @DrawableRes val placeholderRes: Int = R.drawable.ic_profile,
    val tint: Color = Color.Gray,
    val onArrowClick: () -> Unit = {},
)
