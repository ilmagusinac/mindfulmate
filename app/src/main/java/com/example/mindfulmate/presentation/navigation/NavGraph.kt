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
import androidx.navigation.compose.NavHost
import com.example.mindfulmate.presentation.ui.screen.welcome.WelcomeScreen
import androidx.navigation.compose.composable
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.about_app.AboutAppScreen
import com.example.mindfulmate.presentation.ui.screen.chat.ChatScreen
import com.example.mindfulmate.presentation.ui.screen.community.CommunityScreen
import com.example.mindfulmate.presentation.ui.screen.daily_checkin.DailyCheckInScreen
import com.example.mindfulmate.presentation.ui.screen.emotional_analytics.MoodAnalyticsScreen
import com.example.mindfulmate.presentation.ui.screen.help_support.HelpAndSupportScreen
import com.example.mindfulmate.presentation.ui.screen.home.HomeScreen
import com.example.mindfulmate.presentation.ui.screen.profile.DeleteAccountScreen
import com.example.mindfulmate.presentation.ui.screen.profile.EditCredentialScreen
import com.example.mindfulmate.presentation.ui.screen.profile.EditProfileScreen
import com.example.mindfulmate.presentation.ui.screen.profile.ProfileScreen
import com.example.mindfulmate.presentation.ui.screen.resources.ResourcesScreen
import com.example.mindfulmate.presentation.ui.screen.settings.SettingsScreen
import com.example.mindfulmate.presentation.ui.screen.signin.ResetPassword
import com.example.mindfulmate.presentation.ui.screen.signin.SignInScreen
import com.example.mindfulmate.presentation.ui.screen.signup.SignUpScreen
import com.example.mindfulmate.presentation.ui.screen.signup.UsernameScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    onMenuClick: () -> Unit,
    modifier: Modifier
) {
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
                onMenuClick = onMenuClick,
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }
        composable(route = Screen.Community.route) {
            CommunityScreen()
        }
        composable(route = Screen.Resources.route) {
            ResourcesScreen()
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
        composable(route = Screen.Chat.route) {
            ChatScreen()
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                viewModel = hiltViewModel(),
                onGoToProfileClick = { navController.navigate(Screen.Profile.route) },
                onEditProfileClick = { navController.navigate(Screen.EditProfile.route) },
                onEditCredentialsClick = { navController.navigate(Screen.EditCredential.route) },
                onAboutAppClick = { navController.navigate(Screen.AboutApp.route) },
                onHelpSupportClick = { navController.navigate(Screen.HelpAndSupport.route) },
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
                onDeleteAccountClick = { navController.navigate(Screen.DeleteAccount.route) },
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
            AboutAppScreen(
                onGoBackClick = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(route = Screen.HelpAndSupport.route) {
            HelpAndSupportScreen(
                onGoBackClick = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(route = Screen.DailyCheckIn.route) {
            DailyCheckInScreen(
                viewModel = hiltViewModel(),
                navigate = { navController.navigate(Screen.Home.route) },
                onCancelClick = { navController.navigate(Screen.Home.route) },
            )
        }
        composable(route = Screen.EmotionalAnalytics.route) {
            MoodAnalyticsScreen(
                viewModel = hiltViewModel(),
                onGoBackClick = { navController.navigate(Screen.Profile.route) }
            )
        }
    }
}
