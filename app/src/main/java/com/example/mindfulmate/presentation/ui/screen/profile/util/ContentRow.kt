package com.example.mindfulmate.presentation.ui.screen.profile.util

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.mindfulmate.R

data class ContentRow(
    val title: String,
    val buttonTitle: String? = null,
    val label: String,
    val expandedTabs: List<ExpandedFiledType> = listOf(),
    @DrawableRes val placeholderRes: Int = R.drawable.ic_profile,
    val tint: Color = Color.Gray,
    val onButtonClick: () -> Unit = {},
    val onArrowClick: () -> Unit = {},
    val message: String? = null,
    val isEnabled: Boolean = true,
    val isExpandable: Boolean = true
)
