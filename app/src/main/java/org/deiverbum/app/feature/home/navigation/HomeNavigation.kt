package org.deiverbum.app.feature.home.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.home.HomeScreen
import org.deiverbum.app.ui.NiaAppState


@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(route = HomeRoute, navOptions)

@ExperimentalLayoutApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.homeScreen(
    onTopicClick: (String) -> Unit,
    appState: NiaAppState,
    //onBackClick: () -> Unit,
) {
    composable<HomeRoute> {
        HomeScreen(onNextButtonClicked = onTopicClick, appState = appState)
    }
}


