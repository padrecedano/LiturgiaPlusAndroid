package org.deiverbum.app.navigation

import LPlusIcons
import androidx.compose.ui.graphics.vector.ImageVector
import org.deiverbum.app.R
import org.deiverbum.app.feature.calendar.navigation.CalendarRoute
import org.deiverbum.app.feature.home.navigation.HomeRoute
import org.deiverbum.app.feature.mas.navigation.MasRoute
import kotlin.reflect.KClass

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
    val route: KClass<*>,

    ) {

    HOME(
        selectedIcon = LPlusIcons.Home,
        unselectedIcon = LPlusIcons.HomeBorder,
        iconTextId = R.string.feature_home_title,
        titleTextId = R.string.app_name,
        route = HomeRoute::class,
    ),

    CALENDAR(
        selectedIcon = LPlusIcons.Calendar,
        unselectedIcon = LPlusIcons.CalendarBorder,
        iconTextId = R.string.feature_calendar_title,
        titleTextId = R.string.feature_calendar_title,
        route = CalendarRoute::class,

        ),
    MAS(
        selectedIcon = LPlusIcons.Other,
        unselectedIcon = LPlusIcons.OtherBorder,
        iconTextId = R.string.feature_other_title,
        titleTextId = R.string.feature_other_title,
        route = MasRoute::class,
    ),
}

