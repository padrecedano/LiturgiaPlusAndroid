package org.deiverbum.app.navigation


import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deiverbum.app.feature.calendar.navigation.calendarScreen
import org.deiverbum.app.feature.home.navigation.HomeRoute
import org.deiverbum.app.feature.home.navigation.homeScreen
import org.deiverbum.app.feature.mas.navigation.masScreen
import org.deiverbum.app.feature.mas.navigation.navigateToMas
import org.deiverbum.app.feature.universalis.navigation.navigateToUniversalis
import org.deiverbum.app.feature.universalis.navigation.navigateToUniversalisFromCalendar
import org.deiverbum.app.feature.universalis.navigation.universalisFromHome
import org.deiverbum.app.ui.NiaAppState


/**
 * Gráfico de Navegación de alto nivel.
 * Ver: https://d.android.com/jetpack/compose/nav-adaptive
 *
 * El gráfico de navegación declarado en este archivo define las diferentes rutas
 * de alto nivel. La navegación a cada ruta es manejada usando
 * State y Back Handlers.
 */
@ExperimentalCoroutinesApi
@ExperimentalLayoutApi
@ExperimentalMaterial3AdaptiveApi
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NiaNavHost(
    appState: NiaAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
    ) {
        homeScreen(onTopicClick = navController::navigateToUniversalis)
        universalisFromHome()
        masScreen(onTopicClick = navController::navigateToMas)
        calendarScreen(onTopicClick = navController::navigateToUniversalisFromCalendar)
    }
}