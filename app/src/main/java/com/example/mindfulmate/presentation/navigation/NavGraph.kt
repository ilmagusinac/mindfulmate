package com.example.mindfulmate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import com.example.mindfulmate.presentation.ui.screen.welcome.WelcomeScreen
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.about_app.AboutAppScreen
import com.example.mindfulmate.presentation.ui.screen.chat.ChatHomeScreen
import com.example.mindfulmate.presentation.ui.screen.chat.ChatScreen
import com.example.mindfulmate.presentation.ui.screen.community.CommunityHomeScreen
import com.example.mindfulmate.presentation.ui.screen.community.CommunityPostScreen
import com.example.mindfulmate.presentation.ui.screen.community.CommunityScreen
import com.example.mindfulmate.presentation.ui.screen.community.CommunityWritePostScreen
import com.example.mindfulmate.presentation.ui.screen.community.EditPostScreen
import com.example.mindfulmate.presentation.ui.screen.daily_checkin.DailyCheckInScreen
import com.example.mindfulmate.presentation.ui.screen.emergency_contact.EmergencyContactScreen
import com.example.mindfulmate.presentation.ui.screen.emotional_analytics.MoodAnalyticsScreen
import com.example.mindfulmate.presentation.ui.screen.help_support.HelpAndSupportScreen
import com.example.mindfulmate.presentation.ui.screen.home.HomeScreen
import com.example.mindfulmate.presentation.ui.screen.profile.DeleteAccountScreen
import com.example.mindfulmate.presentation.ui.screen.profile.EditCredentialScreen
import com.example.mindfulmate.presentation.ui.screen.profile.EditProfileScreen
import com.example.mindfulmate.presentation.ui.screen.profile.ProfileScreen
import com.example.mindfulmate.presentation.ui.screen.settings.SettingsScreen
import com.example.mindfulmate.presentation.ui.screen.signin.ResetPassword
import com.example.mindfulmate.presentation.ui.screen.signin.SignInScreen
import com.example.mindfulmate.presentation.ui.screen.signup.SignUpScreen
import com.example.mindfulmate.presentation.ui.screen.signup.UsernameScreen
import com.example.mindfulmate.presentation.view_model.community.community_home.CommunityHomeViewModel
import com.example.mindfulmate.presentation.view_model.nav_graph.NavGraphViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    onMenuClick: () -> Unit,
    modifier: Modifier
) {
    val navGraphViewModel: NavGraphViewModel = hiltViewModel()
    val communitiesViewModel: CommunityHomeViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onSignUpClick = { navController.navigate(Screen.SignUp.route) },
                onSignInClick = { navController.navigate(Screen.SignIn.route) }
            )
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                viewModel = hiltViewModel(),
                onSignInClick = { navController.navigate(Screen.SignIn.route) },
                navigate = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.SignIn.route) {
            SignInScreen(
                viewModel = hiltViewModel(),
                onResetPasswordClick = { navController.navigate(Screen.ResetPassword.route) },
                navigate = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.ResetPassword.route) {
            ResetPassword(
                viewModel = hiltViewModel(),
                onCancelClick = { navController.popBackStack() },
                navigate = { navController.navigate(Screen.SignIn.route) }
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                navGraphViewModel = navGraphViewModel,
                communityViewModel = communitiesViewModel,
                onMenuClick = onMenuClick,
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onCommunityClick = { communityId ->
                    navController.navigate(Screen.Community.createRoute(communityId))
                }
            )
        }
        composable(route = Screen.CommunityHome.route) {
            CommunityHomeScreen(
                viewModel = hiltViewModel(),
                onCommunityClick = { communityId ->
                    navController.navigate(Screen.Community.createRoute(communityId))
                },
                onSearchItemClick = { searchItem ->
                    navController.navigate(Screen.Community.createRoute(searchItem.id))
                }
            )
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                viewModel = hiltViewModel(),
                onGoBackClick = { navController.popBackStack() },
                onEditProfileClick = { navController.navigate(Screen.EditProfile.route) },
                onEmotionalAnalyticsClick = { navController.navigate(Screen.EmotionalAnalytics.route) },
                onDailyInputClick = { navController.navigate(Screen.DailyCheckIn.route) }
            )
        }
        composable(route = Screen.EditProfile.route) {
            EditProfileScreen(
                viewModel = hiltViewModel(),
                onGoBackClick = { navController.popBackStack() },
                navigate = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(route = Screen.Loading.route) {
            LoadingPlaceholder()
        }
        composable(route = Screen.Error.route) {
            ErrorPlaceholder(onConfirmationClick = {})
        }
        composable(
            route = Screen.Chat("{chatId}").route
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")
                ?: throw IllegalArgumentException("Chat ID is missing")

            ChatScreen(
                viewModel = hiltViewModel(),
                chatId = chatId,
                onGoBackClick = { navController.navigate(Screen.ChatHome.route) },
                navigate = { navController.navigate(Screen.ChatHome.route) }
            )
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                viewModel = hiltViewModel(),
                onGoToProfileClick = { navController.navigate(Screen.Profile.route) },
                onEditProfileClick = { navController.navigate(Screen.EditProfile.route) },
                onEditCredentialsClick = { navController.navigate(Screen.EditCredential.route) },
                onAboutAppClick = { navController.navigate(Screen.AboutApp.route) },
                onHelpSupportClick = { navController.navigate(Screen.HelpAndSupport.route) },
                onEmergencyContactSupportClick = { navController.navigate(Screen.EmergencyContact.route) },
                navigate = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Screen.EditCredential.route) {
            EditCredentialScreen(
                viewModel = hiltViewModel(),
                onGoBackClick = { navController.navigate(Screen.Settings.route) },
                onDeleteAccountClick = { navController.navigate(Screen.Welcome.route) },
                navigate = { navController.navigate(Screen.Welcome.route) }
            )
        }
        composable(route = Screen.DeleteAccount.route) {
            DeleteAccountScreen(
                viewModel = hiltViewModel(),
                navigate = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onGoBackClick = { navController.navigate(Screen.EditCredential.route) }
            )
        }
        composable(route = Screen.Username.route) {
            var usernameState by remember { mutableStateOf(TextFieldValue("Username")) }

            UsernameScreen(
                username = usernameState,
                onConfirmationClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                },
                onBackClick = { navController.navigate(Screen.SignUp.route) },
                onUsernameValueChange = { usernameState = it }
            )
        }
        composable(route = Screen.AboutApp.route) {
            AboutAppScreen(onGoBackClick = { navController.navigate(Screen.Settings.route) })
        }
        composable(route = Screen.HelpAndSupport.route) {
            HelpAndSupportScreen(
                viewModel = hiltViewModel(),
                onGoBackClick = { navController.navigate(Screen.Settings.route) },
            )
        }
        composable(route = Screen.DailyCheckIn.route) {
            DailyCheckInScreen(
                viewModel = hiltViewModel(),
                chatViewModel = hiltViewModel(),
                navigate = { navController.navigate(Screen.Home.route) },
                onCancelClick = { navController.navigate(Screen.Home.route) },
                onChatTrigger = { message -> navGraphViewModel.triggerBottomSheet(message) }
            )
        }
        composable(route = Screen.EmotionalAnalytics.route) {
            MoodAnalyticsScreen(
                viewModel = hiltViewModel(),
                onGoBackClick = { navController.popBackStack() }
            )
        }
        composable(route = Screen.ChatHome.route) {
            ChatHomeScreen(
                viewModel = hiltViewModel(),
                onChatClicked = { chatId ->
                    navController.navigate(Screen.Chat.createRoute(chatId))
                }
            )
        }
        composable(route = Screen.Community("{communityId}").route) { backStackEntry ->
            val communityId = backStackEntry.arguments?.getString("communityId")
                ?: throw IllegalArgumentException("Community ID is required")
            CommunityScreen(
                viewModel = hiltViewModel(),
                communityId = communityId,
                onBackButtonClick = { navController.popBackStack() },
                onNewPostClick = { navController.navigate(Screen.WritePost.createRoute(communityId)) },
                onNavigateToPost = { communityId, postId ->
                    navController.navigate(Screen.CommunityPost.createRoute(communityId, postId))
                },
                onNavigateToEdit = { communityId, postId ->
                    navController.navigate(Screen.EditPost.createRoute(communityId, postId))
                },
                onChatClicked = { chatId ->
                    navController.navigate(Screen.Chat.createRoute(chatId))
                }
            )
        }
        composable(
            route = Screen.CommunityPost("{communityId}", "{postId}").route,
            arguments = listOf(
                navArgument("communityId") { type = NavType.StringType },
                navArgument("postId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val communityId = backStackEntry.arguments?.getString("communityId")
                ?: throw IllegalArgumentException("Community ID is required")
            val postId = backStackEntry.arguments?.getString("postId")
                ?: throw IllegalArgumentException("Post ID is required")

            CommunityPostScreen(
                viewModel = hiltViewModel(),
                communityId = communityId,
                postId = postId,
                onGoBackClick = { navController.popBackStack() },
                onChatClicked = { chatId ->
                    navController.navigate(Screen.Chat.createRoute(chatId))
                }
            )
        }
        composable(
            route = Screen.WritePost("{communityId}").route,
            arguments = listOf(navArgument("communityId") { type = NavType.StringType })
        ) { backStackEntry ->
            val communityId =
                backStackEntry.arguments?.getString("communityId") ?: return@composable
            CommunityWritePostScreen(
                viewModel = hiltViewModel(),
                communityId = communityId,
                onCloseClick = { navController.popBackStack() },
                navigate = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.EditPost("{communityId}", "{postId}").route,
            arguments = listOf(
                navArgument("communityId") { type = NavType.StringType },
                navArgument("postId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val communityId = backStackEntry.arguments?.getString("communityId")!!
            val postId = backStackEntry.arguments?.getString("postId")!!

            EditPostScreen(
                viewModel = hiltViewModel(),
                communityId = communityId,
                postId = postId,
                onGoBackClick = { navController.popBackStack() },
                navigate = { navController.popBackStack() }
            )
        }
        composable(route = Screen.EmergencyContact.route) {
            EmergencyContactScreen(
                viewModel = hiltViewModel(),
                onGoBackClick = { navController.popBackStack() }
            )
        }
    }
}
