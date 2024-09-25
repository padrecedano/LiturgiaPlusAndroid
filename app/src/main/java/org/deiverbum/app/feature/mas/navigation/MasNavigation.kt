package org.deiverbum.app.feature.mas.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.mas.MasScreen

@Serializable
data class MasRoute(
    val initialTopicId: String? = null,
)

fun NavController.navigateToMas(
    initialTopicId: String? = null,
    navOptions: NavOptions? = null,
) {
    navigate(route = MasRoute(initialTopicId), navOptions)
}

@ExperimentalLayoutApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.masScreen(
    onTopicClick: (String) -> Unit,
    //onBackClick: () -> Unit,
) {
    composable<MasRoute> {
        MasScreen(onTopicClick = onTopicClick)
    }
}


