package com.example.mindfulmate.presentation.util

import androidx.compose.runtime.remember
import com.example.mindfulmate.presentation.ui.component.util.SearchItem

fun String.truncateWords(): String {
    return this.split(" ")
        .take(4)
        .joinToString(" ")
        .let { if (this.length > it.length) "$it…" else it }
}

fun filterSearchResults(searchItems: List<SearchItem>, query: String): List<SearchItem> {
    return searchItems.filter { it.label.contains(query, ignoreCase = true) }
}
