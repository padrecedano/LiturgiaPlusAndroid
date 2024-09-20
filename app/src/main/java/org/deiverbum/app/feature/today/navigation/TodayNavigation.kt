package org.deiverbum.app.feature.today.navigation

import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

const val TOPIC_ID_ARG = "topicId"
const val INTERESTS_ROUTE_BASE = "ttopic_route*"
const val INTERESTS_ROUTE = "$INTERESTS_ROUTE_BASE?$TOPIC_ID_ARG={$TOPIC_ID_ARG}"


@Serializable
data class TodayRoute(val id: Int)

fun NavController.navigateToToday(navOptions: NavOptions) = navigate(route = TodayRoute, navOptions)

fun NavController.navigateToTodayy(topicId: String? = null, navOptions: NavOptions? = null) {
    val route = if (topicId != null) {
        "${INTERESTS_ROUTE_BASE}?${TOPIC_ID_ARG}=${topicId}"
    } else {
        INTERESTS_ROUTE_BASE
    }
    navigate(route, navOptions)
}

fun NavGraphBuilder.todayScreen(
    onTopicClick: (String) -> Unit,
    onBackClick: () -> Unit,

    ) {
    composable<TodayRoute> {
        //TodayRoute(onTopicClick = onTopicClick)
        val t = LoremIpsum()
        Text(t.values.last())
    }
}