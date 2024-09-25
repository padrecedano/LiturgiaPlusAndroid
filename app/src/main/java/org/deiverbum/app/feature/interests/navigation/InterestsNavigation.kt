package org.deiverbum.app.feature.interests.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.Serializable
import org.deiverbum.app.util.Utils

@Serializable
data class InterestsRoute(
    val initialTopicId: String? = "0",
    val initialDate: Int? = Utils.hoy.toInt()
)

@ExperimentalLayoutApi
@ExperimentalCoroutinesApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.interestsScreen(
    //onTopicClick: () -> Unit,
    //onDateSelected: (Long) -> Unit,
    onTopicClick: (String) -> Unit
) {
    composable<InterestsRoute> {
        Text("............Interests Navigation")
    }
}

fun NavController.navigateToInterests(
    initialTopicId: String? = null,
    navOptions: NavOptions? = null,
) {
    navigate(route = InterestsRoute(initialTopicId), navOptions)
}