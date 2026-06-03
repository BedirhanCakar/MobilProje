package com.example.mobil.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Splash : Screen("splash", "Açılış")
    object Dashboard : Screen("dashboard", "Harita", Icons.Default.Home)
    object NotificationForm : Screen("notification_form", "Bildirim", Icons.Default.Warning)
    object Recommendations : Screen("recommendations", "Tavsiyeler", Icons.Default.Info)
    object Profile : Screen("profile", "Profil", Icons.Default.Person)
}
