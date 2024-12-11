package com.example.mindfulmate.presentation.ui.screen.help_support.util

data class HelpAndSupportContentType(
    val title: String,
    val expandedTabs: List<ContentRowType> = listOf(),
    val isExpandable: Boolean = true,
    val onArrowClick: () -> Unit = {},
)