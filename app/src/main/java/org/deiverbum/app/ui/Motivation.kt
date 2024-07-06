package org.deiverbum.app.ui


import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun MotivationScreen(navController: NavController) = IntroCompose(
    navController = navController,
    text = "Motivation"
) {
    navController.navigate(IntroNavOption.RecommendationScreen.name)
}

