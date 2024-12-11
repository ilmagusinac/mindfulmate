package com.example.mindfulmate.presentation.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen(route = "welcome_screen")
    data object SignUp : Screen(route = "signup_screen")
    data object SignIn : Screen(route = "signin_screen")
    data object Loading : Screen(route = "loading_screen")
    data object Error : Screen(route = "error_screen")
    data object ResetPassword : Screen(route = "reset_password_screen")
    data object Home : Screen(route = "home_screen")
    data object Community : Screen(route = "community_screen")
    data object Resources : Screen(route = "resources_screen")
    data object Profile : Screen(route = "profile_screen")
    data object EditProfile : Screen(route = "edit_profile_screen")
    data object Chat : Screen(route = "chat_screen")
    data object Settings : Screen(route = "settings_screen")
    data object EditCredential : Screen(route = "edit_credential_screen")
    data object DeleteAccount : Screen(route = "delete_account_screen")
    data object Username : Screen(route = "username_screen")
    data object AboutApp : Screen(route = "about_app_screen")
    data object HelpAndSupport : Screen(route = "help_and_support_screen")
    data object DailyCheckIn : Screen(route = "daily_check_in_screen")
    data object EmotionalAnalytics : Screen(route = "emotional_analytics_screen")

    companion object {
        fun getBottomNavRoutes(): List<String> {
            return listOf(
                Home.route,
                Community.route,
                Resources.route,
                Settings.route
            )
        }
    }
}
