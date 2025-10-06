package org.deiverbum.app.feature.universalis

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.deiverbum.app.core.analytics.AnalyticsEvent
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.core.data.repository.TtsRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetUniversalisUseCase
import org.deiverbum.app.core.model.tts.TtsProgressData
import org.deiverbum.app.core.model.universalis.UniversalisResource
import org.deiverbum.app.feature.universalis.UniversalisUiState.Loading
import org.deiverbum.app.feature.universalis.navigation.UniversalisRoute
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Utils
import javax.inject.Inject

@HiltViewModel
@kotlinx.coroutines.ExperimentalCoroutinesApi
class UniversalisViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val analyticsHelper: AnalyticsHelper,
    private val ttsRepository: TtsRepository, // Ahora UniversalisViewModel usa TtsRepository
    val userDataRepository: UserDataRepository,
    getTopicWithDate: GetUniversalisUseCase,
) : ViewModel() {

    // Key used to save and retrieve the currently selected topic id from saved state.
    private val selectedTopicIdKey = "selectedTopicIdKey"
    private var _isReading = mutableStateOf(false)
    private val route: UniversalisRoute = savedStateHandle.toRoute()
    private var a: String = LiturgyHelper.liturgyByName(route.initialTopicId!!)
        .toString()

    // Para mantener el texto del artículo actual una vez cargado
    private var currentArticleTextToSpeak: String? = null
    private val selectedTopicId = savedStateHandle.getStateFlow(
        key = selectedTopicIdKey,
        initialValue = route.initialTopicId.toString(),
    )
    private val selectedDate = savedStateHandle.getStateFlow(
        key = "date",
        //initialValue = route.initialDate//,Utils.hoy.toInt(),
        initialValue = Utils.hoy.toInt(),

        )

    // Flujos para topicId y date (como los tienes)
    private val selectedTopicIdFlow = savedStateHandle.getStateFlow(
        key = selectedTopicIdKey,
        initialValue = route.initialTopicId.toString(),
    )
    private val selectedDateFlow = savedStateHandle.getStateFlow(
        key = "date",
        //initialValue = route.initialDate
        initialValue = Utils.hoy.toInt(),

        )

    // Estado interno mutable para uiState, para poder actualizar ttsControlsState
    private val _uiState = MutableStateFlow<UniversalisUiState>(Loading)
    val uiState: StateFlow<UniversalisUiState> = _uiState.asStateFlow()

    init {
        // 1. Combinar flujos de parámetros y luego cargar datos del artículo
        combine(
            selectedTopicIdFlow,
            selectedDateFlow
        ) { topicId, date ->
            // Simplemente crea un objeto o par con los parámetros para flatMapLatest
            Pair(topicId, date)
        }
            .flatMapLatest { (topicId, date) -> // Usa flatMapLatest para manejar el Flow interno
                // Llama a tu use case que devuelve un Flow<UniversalisResource>
                getTopicWithDate.invoke(
                    date = date,
                    title = topicId, // O el título que corresponda
                    selectedTopicId = LiturgyHelper.liturgyByName(topicId).toString()
                )
                // Si getTopicWithDate.invoke puede fallar, el catch debe estar DENTRO o DESPUÉS de flatMapLatest
                // para el flujo del recurso, o aquí si es para el combine en sí.
                // Por ahora, asumimos que el catch principal al final es suficiente o que
                // getTopicWithDate.invoke maneja sus propios errores y emite un estado de error.
            }
            .onEach { universalisResource -> // Ahora universalisResource es UniversalisResource, no Flow
                currentArticleTextToSpeak = extractMainTextFromResource(universalisResource)

                _uiState.update { currentState ->
                    val currentTtsState =
                        (currentState as? UniversalisUiState.UniversalisData)?.ttsControlsState
                            ?: TtsControlsState()
                    UniversalisUiState.UniversalisData(
                        selectedTopicId = selectedTopicIdFlow.value, // O el que corresponda al recurso
                        topics = universalisResource,
                        ttsControlsState = currentTtsState
                    )
                }
            }
            .catch { e -> // Este catch ahora es para errores en el flujo combinado o en getTopicWithDate
                val error = UniversalisUiState.UniversalisError(
                    selectedTopicIdFlow.value, // Podrías necesitar obtener el último topicId/date de otra forma si el error es en flatMapLatest
                    selectedDateFlow.value,
                    e.message ?: "Error in data loading or processing"
                )
                analyticsHelper.logUniversalisErrorEvent(error)
                _uiState.value = error
            }
            .launchIn(viewModelScope)


        // 2. Observar el progreso del TTS desde ttsRepository (esto se mantiene igual)
        ttsRepository.ttsProgress
            .onEach { progressData ->
                _uiState.update { currentState ->
                    if (currentState is UniversalisUiState.UniversalisData) {
                        currentState.copy(
                            ttsControlsState = currentState.ttsControlsState.copy(
                                progressData = progressData
                            )
                        )
                    } else {
                        currentState
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    // Función helper para extraer el texto principal (debes implementarla según tu modelo)
    private fun extractMainTextFromResource(resource: UniversalisResource): String {
        // Ejemplo: si UniversalisResource tiene una propiedad `content` o una lista de párrafos
        // return resource.content ?: ""
        // o
        // return resource.paragraphs.joinToString("\n") { it.text }
        // Necesitas adaptar esto a la estructura de tu `UniversalisResource`
        // Por ahora, un placeholder:
        return resource.data.getAllForRead().toString() // ¡DEBES CAMBIAR ESTO!
    }

    val uiStatee: StateFlow<UniversalisUiState> = combine(
        selectedTopicId,
        getTopicWithDate.invoke(
            date = selectedDate.value,
            title = route.initialTopicId!!,
            selectedTopicId = LiturgyHelper.liturgyByName(route.initialTopicId!!)
                .toString()
        ),
        UniversalisUiState::UniversalisData,
    )
        .catch<UniversalisUiState> {
            val error = UniversalisUiState.UniversalisError(
                route.initialTopicId!!,
                selectedDate.value,
                it.message!!
            )
            analyticsHelper.logUniversalisErrorEvent(error)
            emit(error)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Loading,
        )

    fun onTopicClick(topicId: String?) {
        savedStateHandle[selectedTopicIdKey] = topicId
        savedStateHandle["date"] = 20250313

    }
}

data class TtsControlsState(
    val isVisible: Boolean = false,
    val progressData: TtsProgressData = TtsProgressData(), // De tu modelo existente
    // Puedes añadir más estados específicos del control si es necesario
)
sealed interface UniversalisUiState {
    data object Loading : UniversalisUiState

    data class UniversalisData(
        val selectedTopicId: String,
        val topics: UniversalisResource,
        val ttsControlsState: TtsControlsState = TtsControlsState() // Estado para el BottomSheet

    ) : UniversalisUiState

    data class UniversalisError(
        val topic: String,
        val date: Int,
        val message: String
    ) : UniversalisUiState {

        override fun toString(): String {
            return "Topic: ${topic}, Date: ${date} Msg: ${message}"
        }
    }


}

private fun AnalyticsHelper.logUniversalisErrorEvent(error: UniversalisUiState.UniversalisError) =
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