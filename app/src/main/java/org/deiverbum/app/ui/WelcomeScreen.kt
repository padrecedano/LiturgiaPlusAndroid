package org.deiverbum.app.ui


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun WelcomeScreen(navController: NavController = rememberNavController()) = IntroCompose(
    navController = navController,
    text = "Welcome",
    showBackButton = false
) {
    navController.navigate(IntroNavOption.MotivationScreen.name)
}



