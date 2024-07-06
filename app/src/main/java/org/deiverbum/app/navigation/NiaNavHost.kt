package org.deiverbum.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.google.samples.apps.nowinandroid.feature.search.navigation.searchScreen
import org.deiverbum.app.core.presentation.ui.NiaAppState
import org.deiverbum.app.feature.bookmarks.navigation.bookmarksScreen
import org.deiverbum.app.feature.foryou.navigation.forYouScreen
import org.deiverbum.app.feature.home.navigation.HOME_ROUTE
import org.deiverbum.app.feature.home.navigation.homeScreen
import org.deiverbum.app.feature.interests.navigation.navigateToInterests
import org.deiverbum.app.ui.interests2pane.interestsListDetailScreen

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NiaNavHost(
    appState: NiaAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(onTopicClick = navController::navigateToInterests)

        forYouScreen(onTopicClick = navController::navigateToInterests)
        bookmarksScreen(
            onTopicClick = navController::navigateToInterests,
            onShowSnackbar = onShowSnackbar,
        )
        searchScreen(
            onBackClick = navController::popBackStack,
            onInterestsClick = { appState.navigateToTopLevelDestination(TopLevelDestination.INTERESTS) },
            onTopicClick = navController::navigateToInterests,
        )
        interestsListDetailScreen()
    }
}
