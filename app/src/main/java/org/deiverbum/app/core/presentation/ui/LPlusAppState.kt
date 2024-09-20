package org.deiverbum.app.core.presentation.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.util.NetworkMonitor
import org.deiverbum.app.core.data.util.TimeZoneMonitor
import org.deiverbum.app.core.ui.TrackDisposableJank
import org.deiverbum.app.feature.calendar.navigation.navigateToCalendar
import org.deiverbum.app.feature.home.navigation.navigateToHome
import org.deiverbum.app.feature.home.navigation.navigateToHomee
import org.deiverbum.app.navigation.TopLevelDestination
import org.deiverbum.app.navigation.TopLevelDestination.CALENDAR
import org.deiverbum.app.navigation.TopLevelDestination.HOME
import org.deiverbum.app.navigation.TopLevelDestination.LIST_OF_BOOKS

//import org.deiverbum.app.navigation.TopLevelDestination.UNIVERSALIS

@Composable
fun rememberLPlusAppState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    universalisRepository: UniversalisRepository,
    //userNewsResourceRepository: UserNewsResourceRepository,
    timeZoneMonitor: TimeZoneMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): LPlusAppState {
    NavigationTrackingSideEffect(navController)
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
        networkMonitor,
        universalisRepository,
        //userNewsResourceRepository,
        timeZoneMonitor,
    ) {
        LPlusAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            windowSizeClass = windowSizeClass,
            networkMonitor = networkMonitor,
            universalisRepository = universalisRepository,
            //userNewsResourceRepository = userNewsResourceRepository,
            timeZoneMonitor = timeZoneMonitor,
        )
    }
}

@Stable
class LPlusAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    universalisRepository: UniversalisRepository,
    //userNewsResourceRepository: UserNewsResourceRepository,
    timeZoneMonitor: TimeZoneMonitor,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination


    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                currentDestination?.hasRoute(route = topLevelDestination.route) ?: false
            }
        }

    /*
        val currentTopLevelDestination: TopLevelDestination?
            @Composable get() = when (currentDestination?.route) {
                HOME_ROUTE -> HOME
                FOR_YOU_ROUTE -> FOR_YOU
                CALENDAR_ROUTE -> CALENDAR

                //BOOKMARKS_ROUTE -> BOOKMARKS
                INTERESTS_ROUTE -> INTERESTS
                else -> null
            }*/

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    val shouldRead = universalisRepository.getReader()

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
        trace("Navigation: ${topLevelDestination.name}") {
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
                HOME -> navController.navigateToHome(topLevelNavOptions)
                LIST_OF_BOOKS -> navController.navigateToHomee(topLevelNavOptions)

                //FOR_YOU -> navController.navigateToForYou(topLevelNavOptions)
                //BOOKMARKS -> navController.navigateToBookmarks(topLevelNavOptions)
                //TOPIC -> navController.navigateToTodays(null, topLevelNavOptions)
                CALENDAR -> navController.navigateToCalendar(topLevelNavOptions)
            }
        }
    }

    //fun navigateToSearch() = navController.navigateToSearch()
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