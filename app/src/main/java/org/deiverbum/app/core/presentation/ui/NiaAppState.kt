package org.deiverbum.app.core.presentation.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.google.samples.apps.nowinandroid.feature.search.navigation.navigateToSearch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
import org.deiverbum.app.core.data.repository.UserNewsResourceRepository
import org.deiverbum.app.core.data.util.NetworkMonitor
import org.deiverbum.app.core.data.util.TimeZoneMonitor
import org.deiverbum.app.core.ui.TrackDisposableJank
import org.deiverbum.app.feature.bookmarks.navigation.BOOKMARKS_ROUTE
import org.deiverbum.app.feature.bookmarks.navigation.navigateToBookmarks
import org.deiverbum.app.feature.foryou.navigation.FOR_YOU_ROUTE
import org.deiverbum.app.feature.foryou.navigation.navigateToForYou
import org.deiverbum.app.feature.home.navigation.HOME_ROUTE
import org.deiverbum.app.feature.home.navigation.navigateToHome
import org.deiverbum.app.feature.interests.navigation.INTERESTS_ROUTE
import org.deiverbum.app.feature.interests.navigation.navigateToInterests
import org.deiverbum.app.navigation.TopLevelDestination
import org.deiverbum.app.navigation.TopLevelDestination.BOOKMARKS
import org.deiverbum.app.navigation.TopLevelDestination.FOR_YOU
import org.deiverbum.app.navigation.TopLevelDestination.HOME
import org.deiverbum.app.navigation.TopLevelDestination.INTERESTS

@Composable
fun rememberNiaAppState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    userNewsResourceRepository: UserNewsResourceRepository,
    timeZoneMonitor: TimeZoneMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): NiaAppState {
    NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
        networkMonitor,
        userNewsResourceRepository,
        timeZoneMonitor,
    ) {
        NiaAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            windowSizeClass = windowSizeClass,
            networkMonitor = networkMonitor,
            userNewsResourceRepository = userNewsResourceRepository,
            timeZoneMonitor = timeZoneMonitor,
        )
    }
}

@Stable
class NiaAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    userNewsResourceRepository: UserNewsResourceRepository,
    timeZoneMonitor: TimeZoneMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            FOR_YOU_ROUTE -> FOR_YOU
            BOOKMARKS_ROUTE -> BOOKMARKS
            INTERESTS_ROUTE -> INTERESTS
            HOME_ROUTE -> HOME

            else -> null
        }

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    /**
     * The top level destinations that have unread news resources.
     */
    val topLevelDestinationsWithUnreadResources: StateFlow<Set<TopLevelDestination>> =
        userNewsResourceRepository.observeAllForFollowedTopics()
            .combine(userNewsResourceRepository.observeAllBookmarked()) { forYouNewsResources, bookmarkedNewsResources ->
                setOfNotNull(
                    FOR_YOU.takeIf { forYouNewsResources.any { !it.hasBeenViewed } },
                    BOOKMARKS.takeIf { bookmarkedNewsResources.any { !it.hasBeenViewed } },
                )
            }
            .stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(5_000),
                initialValue = emptySet(),
            )

    val currentTimeZone = timeZoneMonitor.currentTimeZone
        .stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(5_000),
            TimeZone.currentSystemDefault(),
        )

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        androidx.tracing.trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                FOR_YOU -> navController.navigateToForYou(topLevelNavOptions)
                BOOKMARKS -> navController.navigateToBookmarks(topLevelNavOptions)
                INTERESTS -> navController.navigateToInterests(null, topLevelNavOptions)
                HOME -> navController.navigateToHome(topLevelNavOptions)
            }
        }
    }

    fun navigateToSearch() = navController.navigateToSearch()
}

/**
 * Stores information about navigation events to be used with JankStats
 */
@Composable
private fun NavigationTrackingSideEffect(navController: NavHostController) {
    TrackDisposableJank(navController) { metricsHolder ->
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            metricsHolder.state?.putState("Navigation", destination.route.toString())
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
}
