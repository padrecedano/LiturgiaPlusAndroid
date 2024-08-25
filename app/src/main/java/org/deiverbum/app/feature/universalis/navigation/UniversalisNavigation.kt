package org.deiverbum.app.feature.home.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

const val LINKED_NEWS_RESOURCE_ID = "linkedNewsResourceId"
//const val LINKED_NEWS_RESOURCE_ID = "20240319"

const val HOME_ROUTE_ = "home_route/"
const val HOME_ROUTE = "home_route/{${LINKED_NEWS_RESOURCE_ID}}"

private const val DEEP_LINK_URI_PATTERN =
    "https://www.nowinandroid.apps.samples.google.com/foryou/{$LINKED_NEWS_RESOURCE_ID}"
private const val DEEP_LINK_URI_PATTERNN =
    "https://www.deiverbum.org/api/2023/01/{$LINKED_NEWS_RESOURCE_ID}"

//fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)
fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)

@ExperimentalMaterial3AdaptiveApi

fun NavGraphBuilder.homeScreen(
    onTopicClick: (String) -> Unit,
    //onBackClick: () -> Unit,
) {
    composable(
        route = HOME_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
        arguments = listOf(
            navArgument(LINKED_NEWS_RESOURCE_ID) { type = NavType.StringType },
        ),
    ) {
        //MainScreen(onTopicClick)
//MainCompose()
        //HomeRoute(onTopicClick)
        //InterestsRouteNew(onTopicClick = onTopicClick)
        //TopicRoute(showBackButton = true, onBackClick =onBackClick, onTopicClick = onTopicClick)
        //InterestsScreen()
        /*UniversalisRoute(
            showBackButton = true,
            onBackClick = onBackClick,
            onTopicClick = onTopicClick,
        )*/
    }
}