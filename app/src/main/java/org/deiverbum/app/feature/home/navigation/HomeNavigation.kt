package org.deiverbum.app.feature.home.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import org.deiverbum.app.feature.home.HomeRoute

const val LINKED_NEWS_RESOURCE_ID = "linkedNewsResourceId"
const val HOME_ROUTE = "home_route/{$LINKED_NEWS_RESOURCE_ID}"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.nowinandroid.apps.samples.google.com/foryou/{$LINKED_NEWS_RESOURCE_ID}"

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeScreen(onTopicClick: (String) -> Unit) {
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
        HomeRoute(onTopicClick)

    }
}
