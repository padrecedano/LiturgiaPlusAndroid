package org.deiverbum.app.feature.interests.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.deiverbum.app.feature.interests.InterestsRoute

const val TOPIC_ID_ARG = "topicId"
const val INTERESTS_ROUTE_BASE = "interests_route"
const val INTERESTS_ROUTE = "$INTERESTS_ROUTE_BASE?$TOPIC_ID_ARG={$TOPIC_ID_ARG}"

fun NavController.navigateToInterests(topicId: String? = null, navOptions: NavOptions? = null) {
    val route = if (topicId != null) {
        "${INTERESTS_ROUTE_BASE}?${TOPIC_ID_ARG}=$topicId"
    } else {
        INTERESTS_ROUTE_BASE
    }
    navigate(route, navOptions)
}

fun NavGraphBuilder.interestsScreen(
    onTopicClick: (String) -> Unit,
) {
    composable(
        route = INTERESTS_ROUTE,
        arguments = listOf(
            navArgument(TOPIC_ID_ARG) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            },
        ),
    ) {
        InterestsRoute(onTopicClick = onTopicClick)
    }
}
