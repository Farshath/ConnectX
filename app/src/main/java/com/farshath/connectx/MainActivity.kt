package com.farshath.connectx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.farshath.connectx.navigation.Screen
import com.farshath.connectx.navigation.SetupNavGraph
import com.farshath.connectx.ui.theme.ConnectXTheme
import com.farshath.connectx.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectXTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: AuthViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isUserAuthenticated = viewModel.isUserAuthenticated()
    val startDestination = if (isUserAuthenticated) Screen.Home.route else Screen.Login.route

    val showNavigation = when (currentDestination?.route) {
        Screen.Login.route, Screen.Register.route, Screen.Splash.route -> false
        else -> true
    }

    Scaffold(
        bottomBar = {
            if (showNavigation) {
                NavigationBar {
                    val items = listOf(
                        Triple("Home", Screen.Home.route, Icons.Default.Home),
                        Triple("Search", Screen.Search.route, Icons.Default.Search),
                        Triple("Create", Screen.CreatePost.route, Icons.Default.AddBox),
                        Triple("Alerts", Screen.Notifications.route, Icons.Default.Notifications),
                        Triple("Profile", Screen.Profile.route, Icons.Default.Person)
                    )
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.third, contentDescription = item.first) },
                            label = { Text(item.first) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.second } == true,
                            onClick = {
                                navController.navigate(item.second) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            SetupNavGraph(
                navController = navController,
                startDestination = startDestination
            )
        }
    }
}
