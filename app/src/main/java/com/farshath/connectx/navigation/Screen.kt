package com.farshath.connectx.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
    object Search : Screen("search")
    object CreatePost : Screen("create_post")
    object Notifications : Screen("notifications")
    object Profile : Screen("profile")
    object PostDetails : Screen("post_details/{postId}") {
        fun createRoute(postId: String) = "post_details/$postId"
    }
    object Chat : Screen("chat/{userId}") {
        fun createRoute(userId: String) = "chat/$userId"
    }
    object Settings : Screen("settings")
}
