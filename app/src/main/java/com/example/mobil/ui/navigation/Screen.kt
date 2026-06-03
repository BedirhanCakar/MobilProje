package com.example.mobil.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Splash : Screen("splash", "Açılış")
    object Login : Screen("login", "Giriş Yap")
    object Register : Screen("register", "Kayıt Ol")
    object Dashboard : Screen("dashboard", "Harita", Icons.Default.Home)
    object NotificationForm : Screen("notification_form?lat={lat}&lng={lng}", "Bildirim", Icons.Default.Warning) {
        fun createRoute(lat: Double, lng: Double) = "notification_form?lat=$lat&lng=$lng"
    }
    object Recommendations : Screen("recommendations", "Tavsiyeler", Icons.Default.Info)
    object Profile : Screen("profile", "Profil", Icons.Default.Person)
}
