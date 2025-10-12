//package com.example.fintrack.app.ui.theme
//
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//
//private val LightColorScheme = lightColorScheme(
//    primary = /* your brand primary color */,
//    onPrimary = /* your brand onPrimary */,
//    background = /* light bg color */,
//    surface = /* etc */
//)
//
//private val DarkColorScheme = darkColorScheme(
//    primary = /* dark mode primary */,
//    onPrimary = /* etc */,
//    background = /* dark bg */,
//    surface = /* etc */
//)
//
//@Composable
//fun FintrackSplashTheme(
//    content: @Composable () -> Unit
//) {
//    val colors = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
//    MaterialTheme(
//        colorScheme = colors,
//        typography = Typography, // reuse your typography
//        shapes = Shapes,         // reuse shapes
//        content = content
//    )
//}