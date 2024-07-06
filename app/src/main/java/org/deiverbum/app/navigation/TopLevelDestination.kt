package org.deiverbum.app.navigation

import NiaIcons
import androidx.compose.ui.graphics.vector.ImageVector
import org.deiverbum.app.R

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
) {
    HOME(
        selectedIcon = NiaIcons.Upcoming,
        unselectedIcon = NiaIcons.UpcomingBorder,
        iconTextId = R.string.accept,
        titleTextId = R.string.app_name,
    ),
    FOR_YOU(
        selectedIcon = NiaIcons.Upcoming,
        unselectedIcon = NiaIcons.UpcomingBorder,
        iconTextId = R.string.accept,
        titleTextId = R.string.app_name,
    ),
    BOOKMARKS(
        selectedIcon = NiaIcons.Bookmarks,
        unselectedIcon = NiaIcons.BookmarksBorder,
        iconTextId = R.string.accept,
        titleTextId = R.string.accept,
    ),
    INTERESTS(
        selectedIcon = NiaIcons.Grid3x3,
        unselectedIcon = NiaIcons.Grid3x3,
        iconTextId = R.string.accept,
        titleTextId = R.string.accept,
    ),
}

