package org.deiverbum.app.feature.universalis.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.universalis.UniversalisFromHomeScreen
import org.deiverbum.app.util.Utils

@Serializable
data class UniversalisRoute(
    var initialTopicId: String? = null,
    var date: Int? = Utils.hoy.toInt()
)

@ExperimentalMaterial3Api
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.universalisFromHome(onBackClick: () -> Unit) {
    composable<UniversalisRoute> {
        UniversalisFromHomeScreen(
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToUniversalis(
    topicId: String,
    navOptions: NavOptionsBuilder.() -> Unit = {},
) {
    navigate(route = UniversalisRoute(topicId)) { navOptions() }
}

fun NavController.navigateToUniversalisFromCalendar(
    topicId: String,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = UniversalisRoute(topicId)) { navOptions() }
}
