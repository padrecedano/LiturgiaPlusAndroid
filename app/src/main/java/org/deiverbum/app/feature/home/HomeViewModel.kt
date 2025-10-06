package org.deiverbum.app.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.deiverbum.app.core.analytics.AnalyticsEvent
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.core.domain.GetHomeUseCase
import org.deiverbum.app.core.model.universalis.HomeResource
import org.deiverbum.app.util.DateTimeUtil
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val analyticsHelper: AnalyticsHelper,
    private val getHomeUseCase: GetHomeUseCase // Inyectado para la sincronización proactiva
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Idle)
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<HomeNavigationEvent>()
    val navigationEvent: SharedFlow<HomeNavigationEvent> = _navigationEvent.asSharedFlow()

    init {
        //ensureCurrentDateDataExists()
    }

    private fun ensureCurrentDateDataExists() {
        viewModelScope.launch {
            try {
                val now = Clock.System.now()
                now.toLocalDateTime(TimeZone.currentSystemDefault())
                val currentDateAsInt = DateTimeUtil.getTodayDate()//(currentLocalDateTime)
                getHomeUseCase.invoke(date = currentDateAsInt).firstOrNull()
                Log.d(
                    "HomeViewModel",
                    "Sincronización proactiva para $currentDateAsInt completada."
                )
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error durante la sincronización proactiva.", e)
            }
        }
    }

    // 4. Renombrar y ajustar la función de clic
    fun onItemUIClicked(itemUITitle: String) { // Recibe el título del ItemUI
        viewModelScope.launch {
            val now = Clock.System.now()
            now.toLocalDateTime(TimeZone.currentSystemDefault())
            val currentDateAsInt = DateTimeUtil.getTodayDate()//(currentLocalDateTime)

            Log.d(
                "HomeViewModel",
                "ItemUI pulsado: $itemUITitle. Navegando con fecha: $currentDateAsInt"
            )

            // El topicIdForNavigation puede ser el mismo itemUITitle o uno transformado si es necesario
            val topicIdForNav = when (itemUITitle) {
                // Si necesitas mapear títulos a IDs específicos para la navegación:
                // "Vísperas" -> "Visperas" // Ejemplo de mapeo
                // Por defecto, usa el título tal cual si es el ID esperado por UniversalisRoute
                else -> itemUITitle
            }

            _navigationEvent.emit(
                HomeNavigationEvent.NavigateToUniversalis(
                    topicIdForNav,
                    currentDateAsInt
                )
            )
        }
    }

    private fun ensureCurrentDateDataExistss() {
        viewModelScope.launch {
            try {
                val now = Clock.System.now()
                now.toLocalDateTime(TimeZone.currentSystemDefault())
                val currentDateAsInt =
                    DateTimeUtil.getTodayDate()//DateTimeUtil.localDateTimeToInt(currentLocalDateTime)
                getHomeUseCase.invoke(date = currentDateAsInt).firstOrNull()
                Log.d(
                    "HomeViewModel",
                    "Sincronización proactiva para $currentDateAsInt completada."
                )
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error durante la sincronización proactiva.", e)
            }
        }
    }

    // 4. Renombrar y ajustar la función de clic
    fun onItemUIClickedd(itemUITitle: String) { // Recibe el título del ItemUI
        viewModelScope.launch {
            val now = Clock.System.now()
            now.toLocalDateTime(TimeZone.currentSystemDefault())
            val currentDateAsInt =
                DateTimeUtil.getTodayDate()//DateTimeUtil.localDateTimeToInt(currentLocalDateTime)

            Log.d(
                "HomeViewModel",
                "ItemUI pulsado: $itemUITitle. Navegando con fecha: $currentDateAsInt"
            )

            // El topicIdForNavigation puede ser el mismo itemUITitle o uno transformado si es necesario
            val topicIdForNav = when (itemUITitle) {
                // Si necesitas mapear títulos a IDs específicos para la navegación:
                // "Vísperas" -> "Visperas" // Ejemplo de mapeo
                // Por defecto, usa el título tal cual si es el ID esperado por UniversalisRoute
                else -> itemUITitle
            }

            _navigationEvent.emit(
                HomeNavigationEvent.NavigateToUniversalis(
                    topicIdForNav,
                    currentDateAsInt
                )
            )
        }
    }
}
/*
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val analyticsHelper: AnalyticsHelper,
    timeZoneMonitor: TimeZoneMonitor,
    val userDataRepository: UserDataRepository,
    getHomeUseCase: GetHomeUseCase,
) : ViewModel() {

    private val selectedTopicIdKey = "selectedTopicIdKey"
    private val easterKey = "easterKey"

    private val route: UniversalisRoute = savedStateHandle.toRoute()

    private val selectedTopicId = savedStateHandle.getStateFlow(
        key = selectedTopicIdKey,
        initialValue = route.initialTopicId.toString(),
    )

    private val easter = savedStateHandle.getStateFlow(
        key = easterKey,
        initialValue = DateTimeUtil.year(20250101),
    )


    val currentTimeZone = timeZoneMonitor.currentTimeZone
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            TimeZone.currentSystemDefault(),
        )
    val zi = ZoneId.of(currentTimeZone.value.id)
    val time = ZonedDateTime.now(zi)
    private val newDate = time.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInt()

    private val _zone = MutableStateFlow<ZoneId>(ZoneId.of(currentTimeZone.value.id))
    private val _zoneDate = MutableStateFlow<ZonedDateTime>(ZonedDateTime.now(_zone.value))
    private val newDatee = _zoneDate.value.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInt()
    val newD: StateFlow<ZonedDateTime> = _zoneDate

    private var selectedDate = savedStateHandle.getStateFlow(
        key = "date",
        initialValue = route.initialDate
        //initialValue = newD.value.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInt(),
    )
    val d = DateTimeUtil.isPasqua(selectedDate.value)
    private val _timer =
        MutableStateFlow<Int>(time.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInt())
    val timer: StateFlow<Int> = _timer
    val uiState: StateFlow<HomeUiState> = combine(
        selectedTopicId,
        selectedDate,
        getHomeUseCase.invoke(
            date = selectedDate.value,
        ),
        HomeUiState::HomeData,
    ).catch<HomeUiState> {
        val error = HomeUiState.HomeError(
            date = selectedDate.value,
            message = it.message!!
        )
        analyticsHelper.logHomeErrorEvent(error)
        emit(error)
    }.distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading,
        )

    fun onTopicClick(topicId: String?) {
        //savedStateHandle[selectedTopicIdKey] = topicId
    }

}*/

sealed interface HomeNavigationEvent {
    data class NavigateToUniversalis(val topicIdForNavigation: String, val dateAsInt: Int) :
        HomeNavigationEvent
}

sealed interface HomeScreenUiState {
    data object Idle : HomeScreenUiState
    data object Loading : HomeScreenUiState // Si tienes una fase de carga inicial
}

/**
 * Representa los diferentes estados posibles para la pantalla de inicio (HomeScreen).
 * Esta interfaz sellada define los estados que la UI de HomeScreen puede observar
 * para renderizar la información apropiada o indicadores de carga/error.
 */
sealed interface HomeScreenUiStateee {
    /**
     * Indica que la lista de temas/botones para HomeScreen se está cargando.
     * Durante este estado, la UI típicamente mostraría un indicador de progreso general,
     * como un CircularProgressIndicator.
     */
    data object LoadingTopics : HomeScreenUiStateee

    /**
     * Indica que la lista de temas/botones se ha cargado correctamente y está lista
     * para ser mostrada en la UI.
     *
     * @property topics La lista de [TopicInfo] que contiene los datos para cada
     *                  botón que se mostrará en HomeScreen.
     */
    data class TopicsLoaded(val topics: List<TopicInfo>) : HomeScreenUiStateee

    /**
     * Indica que ocurrió un error al intentar cargar la lista de temas/botones
     * para HomeScreen.
     *
     * @property message Un mensaje descriptivo del error, que es opcional y puede
     *                   ser nulo. La UI puede usar este mensaje para informar al usuario.
     */
    data class ErrorLoadingTopics(val message: String?) : HomeScreenUiStateee
}

/**
 * Modelo de datos simple para representar la información de cada botón o tema
 * que se muestra en la HomeScreen. Cada instancia de TopicInfo corresponde
 * a un elemento interactivo (botón) en la pantalla de inicio.
 *
 * @property id El identificador único del tema. Este ID se utiliza internamente
 *              para la lógica de navegación (por ejemplo, para construir la ruta
 *              hacia la pantalla de detalle del tema) y potencialmente para
 *              identificar el tema en otras partes de la aplicación.
 * @property displayName El texto legible por humanos que se mostrará en el botón
 *                     en la interfaz de usuario.
 */
data class TopicInfo(
    val id: String,
    val displayName: String
    // Ejemplo de campo adicional que podrías necesitar:
    // val iconResId: Int? = null // Para un icono opcional en el botón
)

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class HomeData(
        val selectedTopicId: String?,
        val selectedDate: Int,
        val topics: HomeResource,
    ) : HomeUiState

    data class HomeError(
        val date: Int,
        val message: String
    ) : HomeUiState {

        override fun toString(): String {
            return "Date: $date Msg: $message"
        }
    }

    data object Empty : HomeUiState

}

private fun AnalyticsHelper.logHomeErrorEvent(error: HomeUiState.HomeError) =
    logEvent(
        event = AnalyticsEvent(
            type = ERROR_EVENT,
            extras = listOf(
                element = AnalyticsEvent.Param(
                    key = ERROR_EVENT,
                    value = error.toString()
                )
            ),
        ),

        )

private const val ERROR_EVENT = "errorEvent"