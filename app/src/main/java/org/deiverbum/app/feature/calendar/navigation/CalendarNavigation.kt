@file:OptIn(ExperimentalLayoutApi::class)

package org.deiverbum.app.feature.calendar.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.calendar.CalendarScreen
import org.deiverbum.app.util.Utils

@Serializable
data class CalendarRoute(
    val initialTopicId: String? = "-1",
    val initialDate: Int? = Utils.hoy.toInt()
)

fun NavController.navigateToCalendar(
    //initialTopicId: String? = null,
    navOptions: NavOptions? = null,
) {
    navigate(route = CalendarRoute(), navOptions)
}

@ExperimentalLayoutApi
@ExperimentalCoroutinesApi
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.calendarScreen(
    onTopicClick: (String) -> Unit
) {
    composable<CalendarRoute> {
        CalendarScreen(onTopicClick = onTopicClick, onDateSelected = {})
    }
}

