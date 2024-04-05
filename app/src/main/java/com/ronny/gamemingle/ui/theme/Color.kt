package com.ronny.gamemingle.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val LightGrey = Color(0xFFD8D8D8)
val DarkGrey = Color(0xFF2A2A2A)

val ShimmerLightGrey = Color(0xFFF1F1F1)
val ShimmerMediumGrey = Color(0xFFE3E3E3)
val ShimmerDarkGrey = Color(0xFF222539)

val DarkHigh = Color(0xFF070F2B)
val DarkMedium = Color(0xFF030637)
val DarkLow = Color(0xFF535C91)

val Colors.welcomeScreenBackgroundColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Color.White

val Colors.titleColor
    @Composable
    get() = if (isSystemInDarkTheme()) LightGrey else Color.White

val Colors.descColor
    @Composable
    get() = if (isSystemInDarkTheme()) LightGrey.copy(alpha = 0.5f) else DarkGrey.copy(alpha = 0.5f)

val Colors.topAppBarContentColor
    @Composable
    get() = if (isSystemInDarkTheme()) LightGrey else Color.White

val Colors.topAppBarBackgroundColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Purple40