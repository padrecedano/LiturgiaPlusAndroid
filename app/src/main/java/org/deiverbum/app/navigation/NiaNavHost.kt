package org.deiverbum.app.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deiverbum.app.feature.bugreport.navigation.bugReportScreen
import org.deiverbum.app.feature.calendar.navigation.calendarScreen
import org.deiverbum.app.feature.file.navigation.fileScreen
import org.deiverbum.app.feature.home.navigation.HomeRoute
import org.deiverbum.app.feature.home.navigation.homeScreen
import org.deiverbum.app.feature.mas.navigation.masScreen
import org.deiverbum.app.feature.mas.navigation.navigateToFile
import org.deiverbum.app.feature.settings.navigation.settingsScreen
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
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
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
        //homeScreen(onTopicClick = navController::navigateToUniversalis)
        homeScreen(
            onTopicClick = navController::navigateToUniversalis,
            currentTimeZone = appState.currentTimeZone,
            currentDate = appState.currentDate
        )

        masScreen(onTopicClick = navController::navigateToFile)

        universalisFromHome(onBackClick = navController::navigateUp)
        //masScreen(onBackClick = navController::navigateUp)
        calendarScreen(onTopicClick = navController::navigateToUniversalisFromCalendar)
        //menuScreen(onTopicClick = navController::navigateToMenu)
        fileScreen(onBackClick = navController::navigateUp)
        bugReportScreen(onBackClick = navController::navigateUp)
        settingsScreen(onBackClick = navController::navigateUp)


    }
}