package com.example.mindfulmate.presentation.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen(route = "welcome_screen")
    data object SignUp : Screen(route = "signup_screen")
    data object SignIn : Screen(route = "signin_screen")
    data object Loading : Screen(route = "loading_screen")
    data object Error : Screen(route = "error_screen")
    data object ResetPassword : Screen(route = "reset_password_screen")
    data object Home : Screen(route = "home_screen")
    data object CommunityHome : Screen(route = "community_home_screen")
    data object Profile : Screen(route = "profile_screen")
    data object EditProfile : Screen(route = "edit_profile_screen")
    data class Chat(val chatId: String) : Screen(route = "chat_screen/{chatId}") {
        companion object {
            fun createRoute(chatId: String) = "chat_screen/$chatId"
        }
    }

    data object Settings : Screen(route = "settings_screen")
    data object EditCredential : Screen(route = "edit_credential_screen")
    data object DeleteAccount : Screen(route = "delete_account_screen")
    data object Username : Screen(route = "username_screen")
    data object AboutApp : Screen(route = "about_app_screen")
    data object HelpAndSupport : Screen(route = "help_and_support_screen")
    data object DailyCheckIn : Screen(route = "daily_check_in_screen")
    data object EmotionalAnalytics : Screen(route = "emotional_analytics_screen")
    data object ChatHome : Screen(route = "chat_home_screen")
    data class Community(val communityId: String) :
        Screen(route = "community_screen/{communityId}") {
        companion object {
            fun createRoute(communityId: String) = "community_screen/$communityId"
        }
    }

    data class CommunityPost(
        val communityId: String,
        val postId: String
    ) : Screen(route = "community_post_screen/{communityId}/{postId}") {
        companion object {
            fun createRoute(
                communityId: String,
                postId: String
            ): String {
                return "community_post_screen/$communityId/$postId"
            }
        }
    }

    data class WritePost(val communityId: String) : Screen("write_post_screen/{communityId}") {
        companion object {
            fun createRoute(communityId: String) = "write_post_screen/$communityId"
        }
    }

    data class EditPost(
        val communityId: String,
        val postId: String
    ) :
        Screen(route = "edit_post_screen/{communityId}/{postId}") {
        companion object {
            fun createRoute(communityId: String, postId: String) =
                "edit_post_screen/$communityId/$postId"
        }
    }

    data object EmergencyContact : Screen(route = "emergency_contact_screen")

    companion object {
        fun getBottomNavRoutes(): List<String> {
            return listOf(
                Home.route,
                CommunityHome.route,
                ChatHome.route,
                Settings.route
            )
        }
    }
}
