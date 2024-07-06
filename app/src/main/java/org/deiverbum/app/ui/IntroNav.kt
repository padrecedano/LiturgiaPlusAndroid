package org.deiverbum.app.ui


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.deiverbum.app.ui.settings.SettingsDialog


fun NavGraphBuilder.introGraph(navController: NavController) {
    navigation(
        startDestination = IntroNavOption.WelcomeScreen.name,
        route = "NavRoutes.IntroRoute.name"
    ) {
        composable(IntroNavOption.WelcomeScreen.name) {
            WelcomeScreen(navController)
        }
        composable(IntroNavOption.MotivationScreen.name) {
            MotivationScreen(navController)
        }
        composable(IntroNavOption.RecommendationScreen.name) {
            RecommendationScreen(navController)
        }
        composable(IntroNavOption.SettingsScreen.name) {
            SettingsDialog(onDismiss = { false })
        }
    }
}

enum class IntroNavOption {
    WelcomeScreen,
    MotivationScreen,
    RecommendationScreen,
    SettingsScreen
}