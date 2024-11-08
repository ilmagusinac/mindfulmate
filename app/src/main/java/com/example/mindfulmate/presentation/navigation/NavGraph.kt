package com.example.mindfulmate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.mindfulmate.presentation.ui.welcome.WelcomeScreen
import androidx.navigation.compose.composable
import com.example.mindfulmate.presentation.ui.component.ErrorPlaceholder
import com.example.mindfulmate.presentation.ui.component.LoadingPlaceholder
import com.example.mindfulmate.presentation.ui.home.HomeScreen
import com.example.mindfulmate.presentation.ui.signin.ResetPassword
import com.example.mindfulmate.presentation.ui.signin.SignInScreen
import com.example.mindfulmate.presentation.ui.signup.SignUpScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
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
        composable(route = Screen.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                navigate = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Loading.route) {
            LoadingPlaceholder()
        }
        composable(route = Screen.Error.route) {
            ErrorPlaceholder(
                onConfirmationClick = {}
            )
        }
        composable(route = Screen.ResetPassword.route) {
            ResetPassword(
                viewModel = hiltViewModel(),
                onCancelClick = { navController.navigate(Screen.SignIn.route) },
                navigate = { navController.navigate(Screen.SignIn.route) }
            )
        }
    }
}
