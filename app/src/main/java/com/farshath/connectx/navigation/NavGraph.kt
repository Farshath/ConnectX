package com.farshath.connectx.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.farshath.connectx.ui.screens.ChatScreen
import com.farshath.connectx.ui.screens.CreatePostScreen
import com.farshath.connectx.ui.screens.HomeScreen
import com.farshath.connectx.ui.screens.LoginScreen
import com.farshath.connectx.ui.screens.NotificationsScreen
import com.farshath.connectx.ui.screens.ProfileScreen
import com.farshath.connectx.ui.screens.RegisterScreen
import com.farshath.connectx.ui.screens.SearchScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToCreatePost = {
                    navController.navigate(Screen.CreatePost.route)
                }
            )
        }
        composable(route = Screen.Search.route) {
            SearchScreen()
        }
        composable(route = Screen.CreatePost.route) {
            CreatePostScreen(
                onPostCreated = {
                    navController.popBackStack()
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = Screen.Notifications.route) {
            NotificationsScreen()
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Screen.Chat.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) {
            ChatScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
