package org.deiverbum.app.feature.calendar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetUniversalisFromCalendarUseCase
import org.deiverbum.app.core.domain.GetUniversalisUseCase
import org.deiverbum.app.core.model.universalis.UniversalisResource
import org.deiverbum.app.feature.calendar.navigation.CalendarRoute
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Utils
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CalendarUniversalisViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    val universalisRepository: UniversalisRepository,
    getOnlyDate: GetUniversalisFromCalendarUseCase,
    getTopicWithDate: GetUniversalisUseCase,

    ) : ViewModel() {
    private val selectedTopicIdKey = "selectedTopicIdKey"
    private val selectedDateKey = "selectedDateKey"

    //private val route: CalendarRoute = savedStateHandle.toRoute()
    private val route: CalendarRoute = savedStateHandle.toRoute()

    val selectedTopicIdd: StateFlow<String?> = savedStateHandle.getStateFlow(
        key = selectedTopicIdKey,
        initialValue = route.initialTopicId,
    )

    private val selectedTopicId = savedStateHandle.getStateFlow(
        key = selectedTopicIdKey,
        initialValue = "0"//interestsRoute.topicId,
    )
    val selectedDate = savedStateHandle.getStateFlow(
        key = selectedDateKey,
        initialValue = Utils.hoy.toInt()//interestsRoute.topicId,
    )
    /*
    private val _counter = MutableStateFlow(UniversalisResourceQuery(setOf(selectedDate.value),
        selectedTopicId.value.toInt()))
*/
    //Expose immutable flow using asStateFlow()
    //var counter = _counter.asStateFlow()

    /*
    val employeeState: StateFlow<CalendarUiState> =
        universalisRepository.getUniversalisByDate(counter.value)
            .map(CalendarUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CalendarUiState.Loading,
            )
*/


    val universalisState: StateFlow<CalendarUniversalisUiState> = combine(
        selectedTopicId,
        getTopicWithDate.invoke(
            //sortBy = HomeSortField.ID,
            date = selectedDate.value,
            title = selectedTopicId.value,
            selectedTopicId = LiturgyHelper.liturgyByName(selectedTopicId.value)
                .toString()//selectedTopicId.value!!,
        ),
        CalendarUniversalisUiState::UniversalisData,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CalendarUniversalisUiState.Loading,
    )

    /*
    val feedState: StateFlow<CalendarFeedUiState> =
        userNewsResourceRepository.observeAllForFollowedTopics()
            .map(CalendarFeedUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CalendarFeedUiState.Loading,
            )
*/


    fun onTopicClick(topicId: String?) {
        Timber.d("axy")
        //savedStateHandle[selectedTopicIdKey] = topicId
        // topicIdd = topicId!!
        //selectedTopicId.value= topicId!!.toInt()
    }

    fun onTopicClickCalendar(topicId: String?, date: Int?) {
        savedStateHandle[selectedTopicIdKey] = topicId
        savedStateHandle[selectedDateKey] = date

        // topicIdd = topicId!!
        //selectedTopicId.value= topicId!!.toInt()
    }

    fun onDateSelected(date: Int?) {
        savedStateHandle[selectedDateKey] = date
        //counter.value.filterDates= setOf(20240920)
        // topicIdd = topicId!!
    }

    /*val feedUiState: StateFlow<CalendarFeedUiState> =
        universalisRepository.getUniversalisForTest(1)
            .map<Universalis, CalendarFeedUiState>(CalendarFeedUiState::Success)
            .onStart { emit(CalendarFeedUiState.Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CalendarFeedUiState.Loading,
            )*/
}

sealed interface CalendarUniversalisUiState {
    data object Loading : CalendarUniversalisUiState
    data object Error : CalendarUniversalisUiState

    data class UniversalisData(
        val selectedTopicId: String?,
        //val isReader: Boolean=true,
        //val topicId:Int,
        val topics: UniversalisResource,
    ) : CalendarUniversalisUiState

    data object Empty : CalendarUniversalisUiState
}

