package com.example.mindfulmate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.mindfulmate.presentation.ui.screen.welcome.WelcomeScreen
import androidx.navigation.compose.composable
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.screen.chat.ChatScreen
import com.example.mindfulmate.presentation.ui.screen.community.CommunityScreen
import com.example.mindfulmate.presentation.ui.screen.home.HomeScreen
import com.example.mindfulmate.presentation.ui.screen.profile.EditProfileScreen
import com.example.mindfulmate.presentation.ui.screen.profile.ProfileScreen
import com.example.mindfulmate.presentation.ui.screen.resources.ResourcesScreen
import com.example.mindfulmate.presentation.ui.screen.settings.SettingsScreen
import com.example.mindfulmate.presentation.ui.screen.signin.ResetPassword
import com.example.mindfulmate.presentation.ui.screen.signin.SignInScreen
import com.example.mindfulmate.presentation.ui.screen.signup.SignUpScreen

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
            //ChatScreen()
            CommunityScreen()
        }
        composable(route = Screen.Resources.route) {
            ResourcesScreen()
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                viewModel = hiltViewModel(),
                onGoBackClick = { navController.popBackStack() },
                onEditProfileClick = { navController.navigate(Screen.EditProfile.route)}
            )
        }
        composable(route = Screen.EditProfile.route) {
            EditProfileScreen(
                viewModel = hiltViewModel(),
                onResetPasswordClick = { navController.navigate(Screen.ResetPassword.route) },
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
                navigate = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
