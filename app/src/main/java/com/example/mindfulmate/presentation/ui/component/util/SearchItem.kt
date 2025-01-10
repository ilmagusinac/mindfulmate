package com.example.mindfulmate.presentation.ui.component.util

import androidx.annotation.DrawableRes
import com.example.mindfulmate.R

data class SearchItem(
    val id: String = "",
    val label: String = "",
    @DrawableRes val placeholderRes: Int = R.drawable.ic_heart
)
