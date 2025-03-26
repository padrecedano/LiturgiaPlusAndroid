package org.deiverbum.app.feature.mas

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetFileUseCase
import org.deiverbum.app.core.domain.GetUniversalisFromCalendarUseCase
import org.deiverbum.app.core.domain.GetUniversalisUseCase
import org.deiverbum.app.core.model.data.FileItem
import org.deiverbum.app.core.model.data.FileRequestt
import org.deiverbum.app.core.model.universalis.UniversalisResource
import org.deiverbum.app.feature.file.FileItemUiState
import org.deiverbum.app.feature.file.FileViewModel.FileUiState
import org.deiverbum.app.feature.file.navigation.FileRoute
import org.deiverbum.app.feature.universalis.navigation.UniversalisRoute
import org.deiverbum.app.util.ExceptionParser
import org.deiverbum.app.util.FileNameUtil
import org.deiverbum.app.util.Utils
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    val universalisRepository: UniversalisRepository,
    getOnlyDate: GetUniversalisFromCalendarUseCase,
    getTopicWithDate: GetUniversalisUseCase,
    private val getFileUseCase: GetFileUseCase,

    ) : ViewModel() {

    private val fileTitleKey = "fileTitleKey"
    private val fileNameKey = "fileNameKey"
    private val fileRoute: FileRoute = savedStateHandle.toRoute()

    private val fileName = savedStateHandle.getStateFlow(
        key = fileNameKey,
        initialValue = "Bautismo",
    )
    private val fileTitle = savedStateHandle.getStateFlow(
        key = fileTitleKey,
        initialValue = FileNameUtil.fileMap["Bautismo"],
    )
    private val fileRequest = MutableStateFlow<FileRequestt?>(
        FileRequestt(FileItem(fileName.value, fileTitle.value!!), 1, 1, true, true, true)
    )


    private fun loadDataFlow(fileRequest: FileRequestt): Flow<FileUiState> = flow {
        emit(FileUiState.Loading)
        try {
            val result = getFileUseCase(fileRequest)
            emit(FileUiState.Loaded(FileItemUiState(result)))
        } catch (error: Exception) {
            emit(FileUiState.Error(ExceptionParser.getMessage(error)))
        }
    }

    private val selectedTopicIdKey = "selectedTopicIdKey"
    private val selectedDateKey = "selectedDateKey"

    //private val route: CalendarRoute = savedStateHandle.toRoute()
    //private val routee: CalendarRoute = savedStateHandle.toRoute()
    private val route: UniversalisRoute = savedStateHandle.toRoute()

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


    fun onTopicClick(topicId: String?, date: Int) {
        Timber.d("axy")
        //route.copy(initialDate =20250313)
        savedStateHandle[selectedTopicIdKey] = topicId
        savedStateHandle[selectedDateKey] = date

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

sealed interface MoreUiState {
    data object Loading : MoreUiState
    data object Error : MoreUiState

    data class UniversalisData(
        val selectedTopicId: String?,
        //val isReader: Boolean=true,
        //val topicId:Int,
        val topics: UniversalisResource,
    ) : MoreUiState

    data object Empty : MoreUiState
}

sealed interface UniversalisUiStatee {
    data object Loading : UniversalisUiStatee

    data class UniversalisData(
        val selectedTopicId: String,
        val topics: UniversalisResource,
    ) : UniversalisUiStatee

    data class UniversalisError(
        val topic: String,
        val date: Int,
        val message: String
    ) : UniversalisUiStatee {

        override fun toString(): String {
            return "Topic: ${topic}, Date: ${date} Msg: ${message}"
        }
    }


}