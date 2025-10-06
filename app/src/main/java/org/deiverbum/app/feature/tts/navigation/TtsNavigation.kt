package org.deiverbum.app.feature.tts.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.tts.TtsScreenWithBottomSheetControls
import org.deiverbum.app.feature.tts.TtsViewModel
import org.deiverbum.app.feature.universalis.navigation.UniversalisRoute

const val READER_GRAPH_ROUTE = "reader_graph" // Ejemplo, ajústalo a tu constante real

@Serializable
object TtsRoute // No necesita argumentos si el texto se pasa por ViewModel


fun NavController.navigateToTts(navOptions: NavOptions? = null) {
    this.navigate(TtsRoute, navOptions)
}

// Esta función definirá el composable para TtsRoute DENTRO del readerGraph.
// La llamaremos desde la construcción del readerGraph.
fun NavGraphBuilder.ttsScreenInReaderGraph( // Nombre más descriptivo
    navController: NavController
) {
    composable<TtsRoute> { backStackEntry ->
        // Obtiene el ViewModel con ámbito al grafo padre (reader_graph)
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(READER_GRAPH_ROUTE)
        }
        val ttsViewModel: TtsViewModel = hiltViewModel(parentEntry)

        TtsScreenWithBottomSheetControls(
            viewModel = ttsViewModel,
            onNavigateBack = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.ttsScreen(
    onTopicClick: () -> Unit,
    navController: NavController, // Necesario para obtener el NavBackStackEntry del grafo padre
    onNavigateBack: () -> Boolean = { navController.popBackStack() }

) {
    composable<TtsRoute> { backStackEntry ->
        val parentGraphEntry = remember(backStackEntry) {
            navController.getBackStackEntry(READER_GRAPH_ROUTE)
        }
        val ttsViewModel: TtsViewModel = hiltViewModel(parentGraphEntry)
        TtsScreenWithBottomSheetControls(
            viewModel = ttsViewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.readerGraph(
    navController: NavController,
    onExitReaderGraph: () -> Unit
) {
    navigation(
        route = READER_GRAPH_ROUTE,
        startDestination = "org.deiverbum.app.feature.universalis.navigation.UniversalisGraphStartRoute" // <--- Usa la ruta objeto simple aquí
    ) {
        // Composable para la ruta de inicio conceptual (podría redirigir o ser un placeholder)
        // O, más comúnmente, la startDestination es una de las rutas reales con argumentos,
        // pero la navegación a ella se hace explícitamente.
        // En este caso, UniversalisGraphStartRoute es solo para satisfacer el requisito de startDestination.
        // La navegación real vendrá de una llamada como navController.navigateToUniversalis(UniversalisRoute(...))

        // Tu composable existente para UniversalisRoute
        composable<UniversalisRoute> { backStackEntry -> // <--- Tu ruta con argumentos
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(READER_GRAPH_ROUTE)
            }
            hiltViewModel(parentEntry)

            // Obtén los argumentos de UniversalisRoute si los necesitas aquí
            // val args = backStackEntry.toRoute<UniversalisRoute>()
            // val topicId = args.initialTopicId
            // val date = args.initialDate

            /*UniversalisFromHomeScreen(
                viewModelTts = ttsViewModel,
                navController = navController,
                // Pasa los argumentos args.topicId, args.date si es necesario
                // ...
            )*/
        }

        // El composable para TtsRoute
        composable<TtsRoute> { backStackEntry ->
            // ... (como antes) ...
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(READER_GRAPH_ROUTE)
            }
            val ttsViewModel: TtsViewModel = hiltViewModel(parentEntry)
            TtsScreenWithBottomSheetControls(
                viewModel = ttsViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}