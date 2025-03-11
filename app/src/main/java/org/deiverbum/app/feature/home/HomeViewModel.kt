package org.deiverbum.app.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone
import org.deiverbum.app.core.analytics.AnalyticsEvent
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.data.util.TimeZoneMonitor
import org.deiverbum.app.core.domain.GetHomeUseCase
import org.deiverbum.app.core.model.universalis.HomeResource
import org.deiverbum.app.feature.universalis.navigation.UniversalisRoute
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val analyticsHelper: AnalyticsHelper,
    timeZoneMonitor: TimeZoneMonitor,
    val userDataRepository: UserDataRepository,
    getHomeUseCase: GetHomeUseCase,
) : ViewModel() {

    private val selectedTopicIdKey = "selectedTopicIdKey"
    private val route: UniversalisRoute = savedStateHandle.toRoute()

    private val selectedTopicId = savedStateHandle.getStateFlow(
        key = selectedTopicIdKey,
        initialValue = route.initialTopicId.toString(),
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
        initialValue = newD.value.format(DateTimeFormatter.ofPattern("yyyyMMdd")).toInt(),
    )

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

}

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