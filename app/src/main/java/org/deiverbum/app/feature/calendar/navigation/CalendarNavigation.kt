package org.deiverbum.app.feature.calendar.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val LINKED_NEWS_RESOURCE_ID = "linkedNewsResourceId"

//const val CALENDAR_ROUTE = "calendar_route/{$LINKED_NEWS_RESOURCE_ID}"
const val CALENDAR_ROUTE = "calendar_route/"

private const val DEEP_LINK_URI_PATTERN =
    "https://www.nowinandroid.apps.samples.google.com/foryou/{$LINKED_NEWS_RESOURCE_ID}"

fun NavController.navigateToCalendar(navOptions: NavOptions) = navigate(CALENDAR_ROUTE, navOptions)

@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.calendarScreen(onDateSelected: (Long?) -> Unit) {
    composable(
        route = CALENDAR_ROUTE,
        /*deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
        arguments = listOf(
            navArgument(LINKED_NEWS_RESOURCE_ID) { type = NavType.StringType },
        ),*/
    ) {
        CalendarRoute(onDateSelected)
        /*InterestsRouteInHome(
            onTopicClick = onTopicClick,
            onBackClick = {},
            showBackButton = true
        )*/
    }
}
