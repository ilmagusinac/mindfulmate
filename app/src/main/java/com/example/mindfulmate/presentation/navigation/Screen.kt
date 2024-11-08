package com.example.mindfulmate.presentation.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen(route = "welcome_screen")
    data object SignUp : Screen(route = "signup_screen")
    data object SignIn : Screen(route = "signin_screen")
    data object Home : Screen(route = "home_screen")
    data object Loading : Screen(route = "loading_screen")
    data object Error : Screen(route = "error_screen")
    data object ResetPassword : Screen(route = "reset_password_screen")
}