package org.deiverbum.app.feature.universalis

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetUniversalisUseCase
import org.deiverbum.app.core.domain.HomeSortField
import org.deiverbum.app.core.model.data.UniversalisRequest
import org.deiverbum.app.feature.today.navigation.TOPIC_ID_ARG
import org.deiverbum.app.navigation.UniversalisRouteFromHome
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Utils
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UniversalisViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    getTopicWithDate: GetUniversalisUseCase,
) : ViewModel() {

    // Key used to save and retrieve the currently selected topic id from saved state.
    private val selectedTopicIdKey = "selectedTopicIdKey"
    private var _isReading = mutableStateOf(false)
    val isReading: State<Boolean> = _isReading
    private val interestsRoute: UniversalisRouteFromHome = savedStateHandle.toRoute()
    private val selectedTopicId = savedStateHandle.getStateFlow(
        key = selectedTopicIdKey,
        initialValue = interestsRoute.initialTopicId,
    )

    val uiState: StateFlow<UniversalisUiState> = combine(
        selectedTopicId,
        //selectedTopicId.value!!,
        //topicId = selectedTopicId.value!!,//LiturgyHelper.liturgyByName(selectedTopicId.value!!),
        //topicId = 11//selectedTopicId.value!!.toInt()
        getTopicWithDate.invoke(
            sortBy = HomeSortField.ID,
            date = Utils.hoy.toInt(),
            title = interestsRoute.initialTopicId!!,
            selectedTopicId = LiturgyHelper.liturgyByName(interestsRoute.initialTopicId)
                .toString()//selectedTopicId.value!!,
            //topicId = selectedTopicId.value!!,//LiturgyHelper.liturgyByName(selectedTopicId.value!!),
            //topicId = 11//selectedTopicId.value!!.toInt()

        ),
        UniversalisUiState::UniversalisData,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UniversalisUiState.Loading,
    )

    fun followTopic(followedTopicId: String, followed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setTopicIdFollowed(followedTopicId, followed)
        }
    }

    fun updatePlayer(status: Boolean) {
        _isReading.value = status
    }

    fun onTopicClick(topicId: String?) {
        savedStateHandle[TOPIC_ID_ARG] = topicId
        // topicIdd = topicId!!
    }
    fun onReaderClick(): String {
        Timber.d("aaa", "LEER")
        _isReading.value = true

        return "Lorem ipsum"
        //savedStateHandle[TOPIC_ID_ARG] = topicId
        // topicIdd = topicId!!
    }
}

sealed interface UniversalisUiState {
    data object Loading : UniversalisUiState
    data object Error : UniversalisUiState

    data class UniversalisData(
        val selectedTopicId: String?,
        //val isReader: Boolean=true,
        //val topicId:Int,
        val topics: List<UniversalisRequest>,
    ) : UniversalisUiState

    data object Empty : UniversalisUiState
}

data class MainScreenState(
    val isButtonEnabled: Boolean = true,
    val text: String = ""
)