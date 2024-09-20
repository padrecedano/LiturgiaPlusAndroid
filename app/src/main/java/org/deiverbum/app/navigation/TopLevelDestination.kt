package org.deiverbum.app.navigation

import LPlusIcons
import androidx.compose.ui.graphics.vector.ImageVector
import org.deiverbum.app.R
import org.deiverbum.app.feature.calendar.navigation.CalendarRoute
import org.deiverbum.app.feature.home.navigation.HomeRoute
import org.deiverbum.app.feature.home.navigation.ListOfBooks
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
    /*TOPIC(
        selectedIcon = LPlusIcons.Home,
        unselectedIcon = LPlusIcons.HomeBorder,
        iconTextId = R.string.feature_home_title,
        titleTextId = R.string.app_name,
        route = TopicRoute::class,

        ),*/
    HOME(
        selectedIcon = LPlusIcons.Home,
        unselectedIcon = LPlusIcons.HomeBorder,
        iconTextId = R.string.feature_home_title,
        titleTextId = R.string.app_name,
        route = HomeRoute::class,


        ),
    LIST_OF_BOOKS(
        selectedIcon = LPlusIcons.Home,
        unselectedIcon = LPlusIcons.HomeBorder,
        iconTextId = R.string.feature_home_title,
        titleTextId = R.string.app_name,
        route = ListOfBooks::class,
    ),

    /*
    FOR_YOU(
        selectedIcon = LPlusIcons.Upcoming,
        unselectedIcon = LPlusIcons.UpcomingBorder,
        iconTextId = R.string.feature_foryou_title,
        titleTextId = R.string.app_name,
        route = ForYouRoute::class,

        ),

    BOOKMARKS(
        selectedIcon = NiaIcons.Bookmarks,
        unselectedIcon = NiaIcons.BookmarksBorder,
        iconTextId = R.string.feature_bookmarks_title,
        titleTextId = R.string.feature_bookmarks_title,
    ),*/
    CALENDAR(
        selectedIcon = LPlusIcons.Add,
        unselectedIcon = LPlusIcons.UpcomingBorder,
        iconTextId = R.string.feature_bookmarks_title,
        titleTextId = R.string.feature_bookmarks_title,
        route = CalendarRoute::class,

        ),

    /*UNIVERSALIS(
        selectedIcon = NiaIcons.Grid3x3,
        unselectedIcon = NiaIcons.Grid3x3,
        iconTextId = R.string.accept,
        titleTextId = R.string.accept,
    ),*/
}

