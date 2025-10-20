package com.example.fintrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fintrack.ui.screens.Analytics
import com.example.fintrack.ui.screens.HomeScreen
import com.example.fintrack.ui.screens.News
import com.example.fintrack.ui.screens.Portfolio
import com.example.fintrack.ui.screens.Settings


/**
 * Defines navigation routes between composable screens.
 * Each route corresponds to a BottomNav item.
 */
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route
    ) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Budgeting.route) { Analytics() }
        composable(BottomNavItem.Portfolio.route) { Portfolio() }
        composable(BottomNavItem.News.route) { News() }
    }
}