package com.example.fintrack.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AreaChart
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.PieChart
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
    object Portfolio: BottomNavItem(route = "portfolio", label = "Portfolio", Icons.Default.AreaChart)
    object Budgeting: BottomNavItem(route = "budgeting", label = "Budgeting", Icons.Default.AttachMoney)
    object Markets : BottomNavItem("markets", "Markets", Icons.Default.Abc)
    object News: BottomNavItem(route = "news", label = "News", Icons.Default.Newspaper)
}

/**
 * List of all bottom navigation destinations.
 */
val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Budgeting,
    BottomNavItem.Portfolio,
    BottomNavItem.News,
//    BottomNavItem.Settings
)
