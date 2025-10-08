package com.example.fintrack.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector


/**
 * Represents a single bottom navigation item.
 */
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Budgets_n_Savings : BottomNavItem("b_n_s", "Budgets & Saving", Icons.Default.Info)
    object Portfolio: BottomNavItem(route = "portfolio", label = "Portfolio", Icons.Default.Info)
    object Analytics: BottomNavItem(route = "analytics", label = "Analytics", Icons.Default.Info)
    object Markets : BottomNavItem("markets", "Markets", Icons.Default.ShoppingCart)
    object News: BottomNavItem(route = "news", label = "News", Icons.Default.Info)
    object Settings : BottomNavItem("settings", "Settings", Icons.Default.Settings)
}

/**
 * List of all bottom navigation destinations.
 */
val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Analytics,
    BottomNavItem.Portfolio,
    BottomNavItem.News,
    BottomNavItem.Settings
)
