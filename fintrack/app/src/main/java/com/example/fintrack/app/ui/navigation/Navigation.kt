package com.example.fintrack.app.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

//import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login") // placeholder for next step
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
//            SplashScreen(onSplashFinished = {
//                navController.navigate(Screen.Login.route) {
//                    popUpTo(Screen.Splash.route) { inclusive = true }
//                }
//            })
        }

        // Temporary: blank login screen until we build it
        composable(Screen.Login.route) {
            Text("Login Screen Coming Soon")
        }
    }
}