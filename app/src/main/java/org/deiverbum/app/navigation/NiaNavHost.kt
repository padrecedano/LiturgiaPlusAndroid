package org.deiverbum.app.navigation


import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deiverbum.app.feature.bugreport.navigation.bugReportScreen
import org.deiverbum.app.feature.calendar.navigation.calendarScreen
import org.deiverbum.app.feature.file.navigation.fileScreen
import org.deiverbum.app.feature.home.navigation.HomeRoute
import org.deiverbum.app.feature.home.navigation.homeScreen
import org.deiverbum.app.feature.mas.navigation.masScreen
import org.deiverbum.app.feature.mas.navigation.navigateToFile
import org.deiverbum.app.feature.settings.navigation.settingsScreen
import org.deiverbum.app.feature.tts.navigation.navigateToTts
import org.deiverbum.app.feature.tts.navigation.ttsScreen
import org.deiverbum.app.feature.universalis.navigation.navigateToUniversalisFromCalendar
import org.deiverbum.app.feature.universalis.navigation.readerGraph
import org.deiverbum.app.feature.universalis.navigation.universalisScreen
import org.deiverbum.app.ui.NiaAppState


/**
 * Gráfico de Navegación de alto nivel.
 * Ver: https://d.android.com/jetpack/compose/nav-adaptive
 *
 * El gráfico de navegación declarado en este archivo define las diferentes rutas
 * de alto nivel. La navegación a cada ruta es manejada usando
 * State y Back Handlers.
 */
@SuppressLint("RestrictedApi")
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


    //TODO: Borrar
    /*
    DisposableEffect(Unit) { // Para que se añada y quite correctamente con el ciclo de vida
        val listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                val logMessage = StringBuilder()
                logMessage.append("---------------- NavigationListener ----------------\n")
                logMessage.append("  Nuevo Destino (route string): ${destination.route ?: "null"}\n")
                logMessage.append("  ID del Destino: ${destination.id}\n")
                logMessage.append("  Display Name: ${destination.displayName}\n")
                logMessage.append("  Argumentos: $arguments\n")
                logMessage.append("----------------------------------------------------")

                Log.d("NavigationListener", logMessage.toString()) }
        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
*/
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
    ) {
        homeScreen(navController = navController)
        universalisScreen(navController = appState.navController)
        //homeScreen(onTopicClick = navController::navigateToUniversalis)
        /*homeScreen(
            navController = navController
            /*onTopicClick = navController::navigateToUniversalis,
            currentTimeZone = appState.currentTimeZone,
            currentDate = appState.currentDate*/
        )
*/
        composable("simple_test_route") {
            Log.d("NiaNavHost", "NAVEGADO A simple_test_route")
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            ) {
                Text("SIMPLE TEST ROUTE OK", color = Color.White, fontSize = 24.sp)
            }
        }

        homeScreen(navController = navController)
        universalisScreen(navController = appState.navController)

        // --- VERIFICA ESTA SECCIÓN CUIDADOSAMENTE ---
        /*composable<UniversalisRoute> { backStackEntry ->
            // Deserializa los argumentos de la ruta usando toRoute()
            // Esto es crucial si UniversalisRoute es una data class @Serializable
            val universalisRouteArgs: UniversalisRoute = backStackEntry.toRoute()

            Log.d("AppNavHost", "Componiendo UniversalisScreen para ruta: $universalisRouteArgs")

            UniversalisScreen(
                // Si UniversalisScreen toma un ViewModel que usa SavedStateHandle para los args:
                // viewModel = hiltViewModel() // El ViewModel de Universalis debería obtener los args
                // a través de SavedStateHandle o toRoute() en el ViewModel.

                // O si pasas los argumentos directamente a UniversalisScreen:
                initialTopicId = universalisRouteArgs.initialTopicId,
                initialDate = universalisRouteArgs.initialDate,
                navController = navController // Si UniversalisScreen necesita el NavController
            )
        }
        */
        readerGraph(
            navController = navController,
            onExitReaderGraph = {
                // Define qué sucede cuando el usuario sale del flujo de lectura.
                // Podría ser simplemente volver atrás o navegar a una pantalla específica.
                navController.popBackStack()
                // O, si quieres ir a un destino específico después de salir:
                // navController.navigate("home_screen_route_string") {
                //     popUpTo(READER_GRAPH_ROUTE) { inclusive = true }
                // }
            }
        )

        masScreen(onTopicClick = navController::navigateToFile)
        //universalisScreen(navController = appState.navController)

        /*universalisFromHome(
            onBackClick = navController::navigateUp,
            navController = navController // <--- PASO 1: Pasar navController aquí

        )*/
        //masScreen(onBackClick = navController::navigateUp)
        calendarScreen(onTopicClick = navController::navigateToUniversalisFromCalendar)
        //menuScreen(onTopicClick = navController::navigateToMenu)
        fileScreen(onBackClick = navController::navigateUp)
        bugReportScreen(onBackClick = navController::navigateUp)
        settingsScreen(onBackClick = navController::navigateUp)
        ttsScreen(
            onTopicClick = navController::navigateToTts,
            navController = navController,
            onNavigateBack = navController::navigateUp
        )


    }
}

@Composable
fun <NavHostController> UniversalisScreen(
    initialTopicId: String?,
    initialDate: Int,
    navController: NavHostController
) {
    Text(LoremIpsum().values.first())
    Text(LoremIpsum().values.first())
    Text(LoremIpsum().values.first())
    Text(LoremIpsum().values.first())
    Text(LoremIpsum().values.first())
}

@Composable
fun UniversalisScreen( // <--- QUITA EL PARÁMETRO DE TIPO GENÉRICO
    initialTopicId: String?,
    //initialDate: Int,
    navController: NavController // <--- USA NavController (o NavHostController si es necesario)
    // viewModel: UniversalisViewModel = hiltViewModel() // Lo añadirás después
) {
    val initialDate = 20250715
    // Por ahora, solo para probar la navegación:
    Log.d("UniversalisScreen", "COMPUESTA con Topic: $initialTopicId, Date: $initialDate")
    Text("UniversalisScreen Contenido:")
    Text("Topic ID: $initialTopicId")
    Text("Date: $initialDate")
    Text(LoremIpsum().values.first())
    // Aquí es donde eventualmente integrarás tu ViewModel, el contenido real,
    // y el botón para el BottomSheet TTS.
}

const val READER_GRAPH_ROUTE = "reader_graph"
