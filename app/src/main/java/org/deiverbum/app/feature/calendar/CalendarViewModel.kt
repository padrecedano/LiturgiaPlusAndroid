package org.deiverbum.app.feature.calendar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetUniversalisFromCalendarUseCase
import org.deiverbum.app.core.domain.GetUniversalisUseCase
import org.deiverbum.app.core.model.data.CalendarResource
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisRequest
import org.deiverbum.app.feature.interests.navigation.InterestsRoute
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    val universalisRepository: UniversalisRepository,
    getOnlyDate: GetUniversalisFromCalendarUseCase,
    getTopicWithDate: GetUniversalisUseCase,

    ) : ViewModel() {
    private val selectedTopicIdKey = "selectedTopicIdKey"
    private val selectedDateKey = "selectedDateKey"

    //private val route: CalendarRoute = savedStateHandle.toRoute()
    private val route: InterestsRoute = savedStateHandle.toRoute()

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
        initialValue = -1//interestsRoute.topicId,
    )

    private val topics: Flow<Universalis> = combine(
        selectedDate,
        selectedTopicId,
    ) { date, topicId ->
        date to topicId
    }.flatMapLatest {
        if (it.first == -1) {
            emptyFlow()

        } else {

            getOnlyDate(
                date = it.first,
                //title = "TODO:Title",
                topicId = it.second
            )
        }
    }
    val uiState: StateFlow<CalendarUiState> = combine(
        selectedTopicId,
        topics,
        CalendarUiState::CalendarData,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CalendarUiState.Loading,
    )

    fun followTopic(followedTopicId: String, followed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setTopicIdFollowed(followedTopicId, followed)
        }
    }

    fun onTopicClick(topicId: String?) {
        Timber.d("axy")
        //savedStateHandle[selectedTopicIdKey] = topicId
        // topicIdd = topicId!!
        //selectedTopicId.value= topicId!!.toInt()
    }

    fun onTopicClickk(topicId: String?, date: Int?) {
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
}

sealed interface CalendarUiState {
    data object Loading : CalendarUiState
    data object Error : CalendarUiState

    data class CalendarData(
        val selectedTopicId: String?,
        //val isReader: Boolean=true,
        //val topicId:Int,
        val topics: Universalis,
    ) : CalendarUiState

    data object Empty : CalendarUiState
}

sealed interface NewsCalendarUiState {
    data class Success(val news: List<CalendarResource>) : NewsCalendarUiState
    data object Error : NewsCalendarUiState
    data object Loading : NewsCalendarUiState
}

sealed interface UniversalisUiStatee {
    data object Loading : UniversalisUiStatee
    data object Error : UniversalisUiStatee

    data class UniversalisDataa(
        val selectedTopicId: String?,
        //val isReader: Boolean=true,
        //val topicId:Int,
        val topics: List<UniversalisRequest>,
    ) : UniversalisUiStatee

    data object Empty : UniversalisUiStatee
}
