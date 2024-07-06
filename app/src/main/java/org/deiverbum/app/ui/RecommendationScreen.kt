package org.deiverbum.app.ui


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.deiverbum.app.R


@Composable
fun RecommendationScreen(
    navController: NavController,
    viewModel: IntroViewModel = hiltViewModel()
) = IntroCompose(
    navController = navController,
    text = "Recommendation",
    buttonText = R.string.audio_play
) {
    viewModel.saveUserOnboarding()
    /*navController.navigate(NavRoutes.MainRoute.name) {
        popUpTo(NavRoutes.IntroRoute.name)
    }*/
}

