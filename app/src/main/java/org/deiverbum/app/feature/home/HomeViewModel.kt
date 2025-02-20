package org.deiverbum.app.feature.home

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
import org.deiverbum.app.core.domain.GetHomeUseCase
import org.deiverbum.app.core.model.data.HomeResource
import org.deiverbum.app.feature.universalis.navigation.UniversalisRoute
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val analyticsHelper: AnalyticsHelper,

    val userDataRepository: UserDataRepository,
    getHomeUseCase: GetHomeUseCase,
) : ViewModel() {

    // Key used to save and retrieve the currently selected topic id from saved state.
    private val selectedTopicIdKey = "selectedTopicIdKey"
    private val route: UniversalisRoute = savedStateHandle.toRoute()

    private val selectedTopicId = savedStateHandle.getStateFlow(
        key = selectedTopicIdKey,
        initialValue = route.initialTopicId.toString(),
    )
    private val selectedDate = savedStateHandle.getStateFlow(
        key = "date",
        initialValue = 20251010//Utils.hoy.toInt(),
    )

    val uiState: StateFlow<HomeUiState> = combine(
        selectedTopicId,
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
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading,
        )

    fun onTopicClick(topicId: String?) {
        savedStateHandle[selectedTopicIdKey] = topicId
    }
}

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class HomeData(
        val selectedTopicId: String?,
        val topics: HomeResource,
    ) : HomeUiState

    data class HomeError(
        val date: Int,
        val message: String
    ) : HomeUiState {

        override fun toString(): String {
            return "Date: ${date} Msg: ${message}"
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