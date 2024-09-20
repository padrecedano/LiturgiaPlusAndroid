package org.deiverbum.app.feature.foryou.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.deiverbum.app.ui.home.HomeScreenBody


@Serializable
data class TopicRoute(val id: String)

fun NavController.navigateToTopic(topicId: String, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = TopicRoute(topicId)) {
        navOptions()
    }
}

fun NavGraphBuilder.topicScreenn(
    //showBackButton: Boolean,
    //onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,
) {
    composable<TopicRoute> {
        TopicScreenn(
            //showBackButton = showBackButton,
            //onBackClick = onBackClick,
            onTopicClick = onTopicClick,
        )
    }
}

@Composable
fun TopicScreenn(onTopicClick: (String) -> Unit) {
    Text("TS")
}

@Serializable
data object ForYouRoute

fun NavController.navigateToForYou(navOptions: NavOptions) =
    navigate(route = ForYouRoute, navOptions)

fun NavGraphBuilder.forYouScreen(onTopicClick: (String) -> Unit) {
    composable<ForYouRoute> {
        //Text("Lipsum*")
        //ForYouScreen(onTopicClick)
        HomeScreenBody(onTopicClick)

    }
}
/*
const val LINKED_NEWS_RESOURCE_ID = "linkedNewsResourceId"
const val FOR_YOU_ROUTE = "for_you_route"///{$LINKED_NEWS_RESOURCE_ID}"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.nowinandroid.apps.samples.google.com/foryou/{$LINKED_NEWS_RESOURCE_ID}"

fun NavController.navigateToForYou(navOptions: NavOptions) = navigate(FOR_YOU_ROUTE, navOptions)

@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.forYouScreen(onTopicClick: (String) -> Unit) {
    composable(
        route = FOR_YOU_ROUTE,
        /*deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
        arguments = listOf(
            navArgument(LINKED_NEWS_RESOURCE_ID) { type = NavType.StringType },
        ),*/
    ) {
        //Text("Lipsum")
        //TodayRoute(onTopicClick = onTopicClick)


        //HomeScreenBody (onTopicClick)


    }
}
*/
