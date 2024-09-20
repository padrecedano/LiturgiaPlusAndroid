package org.deiverbum.app.feature.calendar.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.calendar.CalendarRoute

@Serializable
data object CalendarRoute

fun NavController.navigateToCalendar(navOptions: NavOptions) = navigate(CalendarRoute, navOptions)

@ExperimentalCoroutinesApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.calendarScreen(onDateSelected: (Long?) -> Unit) {
    composable<CalendarRoute> {
        CalendarRoute(onDateSelected)
        /*InterestsRouteInHome(
            onTopicClick = onTopicClick,
            onBackClick = {},
            showBackButton = true
        )*/
    }
}
