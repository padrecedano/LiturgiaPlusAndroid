package org.deiverbum.app.feature.universalis

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.deiverbum.app.core.analytics.AnalyticsEvent
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetUniversalisUseCase
import org.deiverbum.app.core.model.data.UniversalisResource
import org.deiverbum.app.feature.universalis.UniversalisUiState.Loading
import org.deiverbum.app.feature.universalis.navigation.UniversalisRoute
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Utils
import javax.inject.Inject

@HiltViewModel
class UniversalisViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val analyticsHelper: AnalyticsHelper,

    val userDataRepository: UserDataRepository,
    getTopicWithDate: GetUniversalisUseCase,
) : ViewModel() {

    // Key used to save and retrieve the currently selected topic id from saved state.
    private val selectedTopicIdKey = "selectedTopicIdKey"
    private var _isReading = mutableStateOf(false)
    private val route: UniversalisRoute = savedStateHandle.toRoute()
    private var a: String = LiturgyHelper.liturgyByName(route.initialTopicId!!)
        .toString()

    private val selectedTopicId = savedStateHandle.getStateFlow(
        key = selectedTopicIdKey,
        initialValue = route.initialTopicId.toString(),
    )
    private val selectedDate = savedStateHandle.getStateFlow(
        key = "date",
        initialValue = Utils.hoy.toInt(),
    )


    val uiState: StateFlow<UniversalisUiState> = combine(
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
    }

}

sealed interface UniversalisUiState {
    data object Loading : UniversalisUiState

    data class UniversalisData(
        val selectedTopicId: String,
        val topics: UniversalisResource,
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