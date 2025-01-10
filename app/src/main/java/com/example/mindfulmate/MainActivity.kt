package com.example.mindfulmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mindfulmate.presentation.navigation.NavGraph
import com.example.mindfulmate.presentation.navigation.Screen
import com.example.mindfulmate.presentation.navigation.Screen.Companion.getBottomNavRoutes
import com.example.mindfulmate.presentation.navigation.bottom_bar.BottomBarNavigationComponent
import com.example.mindfulmate.presentation.navigation.bottom_bar.BottomBarNavigationItems
import com.example.mindfulmate.presentation.navigation.drawer.Drawer
import com.example.mindfulmate.presentation.navigation.drawer.DrawerShape
import com.example.mindfulmate.presentation.theme.MindfulMateTheme
import com.example.mindfulmate.presentation.view_model.main.MainViewModel
import com.example.mindfulmate.presentation.work.daily_checkin.CheckInStateManager
import com.example.mindfulmate.presentation.work.daily_checkin.NotificationPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var checkInStateManager: CheckInStateManager

    @Inject
    lateinit var notificationPreferenceManager: NotificationPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            MindfulMateTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = hiltViewModel()
                val currentBackStackEntry =
                    navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)
                val currentRoute = currentBackStackEntry.value?.destination?.route
                val showBottomBar = currentRoute in getBottomNavRoutes()

                LaunchedEffect(Unit) {
                    val destination = intent.getStringExtra("destination")
                    if (destination == "daily_check_in") {
                        navController.navigate(Screen.DailyCheckIn.route)
                    }
                }

                BottomNavScaffold(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    showBottomBar = showBottomBar
                )
            }
        }
    }
}

@Composable
fun BottomNavScaffold(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    showBottomBar: Boolean
) {
    val bottomNavigationItems = BottomBarNavigationItems.items
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentBackStackEntry =
        navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)
    val currentRoute = currentBackStackEntry.value?.destination?.route
    val selectedItemIndex = bottomNavigationItems.indexOfFirst { it.route == currentRoute }
        .takeIf { it != -1 } ?: 0
    val username by mainViewModel.username.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        mainViewModel.loadUser()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentRoute == Screen.Home.route,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = DrawerShape(0.dp, 0.97f),
                content = {
                    Drawer(
                        onSignOutButtonClick = {
                            mainViewModel.signOut()
                            scope.launch { drawerState.close() }
                            navController.navigate(Screen.Welcome.route) {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onSettingsClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Screen.Settings.route)
                        },
                        onHelpAndSupportClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Screen.HelpAndSupport.route)
                        }
                    )
                }
            )
        }
    ) {
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    BottomBarNavigationComponent(
                        items = bottomNavigationItems,
                        selectedItemIndex = selectedItemIndex,
                        onItemSelected = { index ->
                            navController.navigate(bottomNavigationItems[index].route) {
                                launchSingleTop = true
                                popUpTo(Screen.Home.route) { inclusive = false }
                            }
                        }
                    )
                }
            },
            content = { innerPadding ->
                NavGraph(
                    navController = navController,
                    startDestination = if (mainViewModel.isUserSignedIn()) Screen.Home.route else Screen.Welcome.route,
                    modifier = Modifier.padding(innerPadding),
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            }
        )
    }
}
