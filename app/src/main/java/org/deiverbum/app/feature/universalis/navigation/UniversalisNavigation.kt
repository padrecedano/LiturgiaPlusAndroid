package org.deiverbum.app.feature.universalis.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.Serializable
import org.deiverbum.app.feature.tts.TtsViewModel
import org.deiverbum.app.feature.tts.navigation.ttsScreenInReaderGraph
import org.deiverbum.app.feature.universalis.UniversalisFromHomeScreen
import org.deiverbum.app.navigation.UniversalisScreen

const val READER_GRAPH_ROUTE = "reader_graph"

@Serializable
object ReaderGraphEntryRoute

@Serializable
data class UniversalisRoute(
    val initialTopicId: String,
    // val initialDate: Int //= DateTimeUtil.getTodayDate(),//Utils.hoy.toInt(),
)

fun NavGraphBuilder.universalisScreen(
    navController: NavController // Recibe el NavController de nivel superior
) {
    composable<UniversalisRoute> { backStackEntry ->
        val routeArgs: UniversalisRoute = backStackEntry.toRoute()

        UniversalisScreen(
            initialTopicId = routeArgs.initialTopicId,
            //initialDate = routeArgs.initialDate,
            navController = navController // Si UniversalisScreen necesita el NavController
        )
    }
}

@Serializable
object UniversalisGraphStartRoute // Una ruta simple para el startDestination del grafo

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
//@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3AdaptiveApi
fun NavGraphBuilder.universalisFromHome(
    onBackClick: () -> Unit, navController: NavController // <--- PASO 2: Aceptar navController aquí
) {
    composable<UniversalisRoute> {
        //TtsScreen()
        //TtsScreenWithBottomSheetControls()
        /*UniversalisFromHomeScreen(
            onBackClick = onBackClick,
            navController = navController, // <--- PASO 3: Pasar navController a la pantalla

        )*/
    }
}

fun NavController.navigateToUniversalis(
    topicId: String,
    //currentDate:Int,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(route = UniversalisRoute(topicId)) { navOptions() }
}

fun NavController.navigateToUniversalisFromCalendar(
    topicId: String,
    currentDate: Int,
    navOptions: NavOptionsBuilder.() -> Unit = {}
) {
    //navigate(route = CalendarRoute(topicId,currentDate)) { navOptions() }
    navigate(route = UniversalisRoute(topicId)) { navOptions() }

}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoroutinesApi
//@RequiresApi(Build.VERSION_CODES.O) // Por UniversalisFromHomeScreen
@OptIn(
    ExperimentalMaterial3Api::class,     // Por UniversalisFromHomeScreen
    ExperimentalFoundationApi::class,    // Por UniversalisFromHomeScreen
    ExperimentalMaterial3AdaptiveApi::class // Por UniversalisFromHomeScreen
)
fun NavGraphBuilder.readerGraph(
    navController: NavController,
    // Esta lambda se llamará cuando el usuario intente navegar "hacia arriba"
    // desde la pantalla de inicio del readerGraph, efectivamente saliendo del flujo.
    onExitReaderGraph: () -> Unit
) {
    navigation(
        // La ruta que identifica este grafo anidado.
        route = READER_GRAPH_ROUTE,
        // La pantalla inicial DENTRO de este grafo anidado.
        // Usamos ReaderGraphEntryRoute como un punto de entrada simple y sin args.
        startDestination = "org.deiverbum.app.feature.universalis.navigation.ReaderGraphEntryRoute" // <--- CORREGIDO
    ) {
        // 1. Composable para el punto de entrada del grafo (ReaderGraphEntryRoute)
        //    En muchos casos, este podría redirigir inmediatamente a la pantalla
        //    principal con argumentos por defecto o simplemente actuar como
        //    un contenedor lógico si la navegación siempre es a UniversalisRoute
        //    con argumentos específicos.
        composable<ReaderGraphEntryRoute> {
            // Aquí podrías tener una pantalla de carga o una redirección.
            // Por simplicidad, asumiremos que la navegación siempre se hará
            // a UniversalisRoute con los argumentos necesarios.
            // Si ReaderGraphEntryRoute debe mostrar algo o redirigir:
            // navController.navigate(UniversalisRoute(initialTopicId = "default")) {
            //     popUpTo(ReaderGraphEntryRoute) { inclusive = true }
            // }
            // O si es solo un placeholder para la estructura del grafo,
            // y la navegación siempre es a UniversalisRoute directamente,
            // este composable podría no ser estrictamente necesario si
            // UniversalisRoute fuera el startDestination (pero UniversalisRoute
            // tiene argumentos, lo que complica ser startDestination directamente).

            // Para este ejemplo, vamos a asumir que la navegación al grafo
            // se hace especificando UniversalisRoute con sus argumentos.
            // Si se navega solo a READER_GRAPH_ROUTE, y este es el startDestination,
            // necesitarías una UI aquí o una redirección.
            // Por ahora, lo dejamos como un posible punto de extensión.
            // Si tu lógica es que al entrar al READER_GRAPH_ROUTE siempre
            // vas a UniversalisRoute, entonces la navegación externa debe
            // apuntar a UniversalisRoute directamente.
        }

        // 2. Pantalla de Origen (UniversalisFromHomeScreen que usa UniversalisResourceData)
        //    Esta es la pantalla donde el usuario ve el contenido y el "ReaderButton".
        composable<UniversalisRoute> { backStackEntry ->
            // Obtener el ViewModel TtsViewModel con ámbito al READER_GRAPH_ROUTE.
            // Esta es la clave para compartir el ViewModel.
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(READER_GRAPH_ROUTE)
            }
            val ttsViewModel: TtsViewModel = hiltViewModel(parentEntry)

            // (Opcional) Obtener argumentos de la ruta si los necesitas aquí
            // val arguments = backStackEntry.toRoute<UniversalisRoute>()
            // val topicId = arguments.initialTopicId
            // val date = arguments.initialDate

            UniversalisFromHomeScreen(
                onBackClick = {
                    // Decide la lógica de "atrás" aquí.
                    // Si es la primera pantalla del grafo o no hay más atrás
                    // dentro del grafo, llama a onExitReaderGraph.
                    if (navController.previousBackStackEntry?.destination?.parent?.route
                        == READER_GRAPH_ROUTE
                    ) {
                        navController.popBackStack()
                    } else {
                        onExitReaderGraph()
                    }
                },
                navController = navController,
                viewModelTts = ttsViewModel // <--- Pasando el ViewModel compartido
                // No necesitas pasar 'viewModel' (UniversalisViewModel) aquí,
                // ya que UniversalisFromHomeScreen lo obtiene con hiltViewModel().
                // No necesitas pasar 'analyticsHelper' aquí si
                // UniversalisFromHomeScreen lo obtiene con LocalAnalyticsHelper.current.
            )
        }

        // 3. Pantalla TTS (TtsScreenWithBottomSheetControls)
        //    Se añade usando la función de extensión que definimos antes.
        ttsScreenInReaderGraph(
            navController = navController
        )
    }
}
