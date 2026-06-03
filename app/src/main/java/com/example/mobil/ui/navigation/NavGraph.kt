package com.example.mobil.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobil.ui.screens.DashboardScreen
import com.example.mobil.ui.screens.NotificationFormScreen
import com.example.mobil.ui.screens.ProfileScreen
import com.example.mobil.ui.screens.RecommendationsScreen
import com.example.mobil.ui.screens.SplashScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToDashboard = {
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen()
        }
        composable(Screen.NotificationForm.route) {
            NotificationFormScreen()
        }
        composable(Screen.Recommendations.route) {
            RecommendationsScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
    }
}
