package com.example.mindfulmate.presentation.util

import android.util.Patterns

fun String.validatePassword(): String? {
    return when {
        this.length < 6 -> "Password must be at least 6 characters"
        this.none { it.isUpperCase() } -> "Password must contain an uppercase letter"
        this.none { it.isLowerCase() } -> "Password must contain a lowercase letter"
        this.none { it.isDigit() } -> "Password must contain a digit"
        this.none { "!@#$%^&*()_-+=<>?/{}~|".contains(it) } -> "Password must contain a special character"
        this.any { it.isWhitespace() } -> "Password must not contain whitespace"
        else -> null
    }
}

fun String.validateEmail(): String? {
    return if (Patterns.EMAIL_ADDRESS.matcher(this).matches()) null else "Invalid email format"
}

fun String.validatePasswordMatch(repeatPassword: String): String? {
    return if (this == repeatPassword) null else "Passwords do not match"
}
