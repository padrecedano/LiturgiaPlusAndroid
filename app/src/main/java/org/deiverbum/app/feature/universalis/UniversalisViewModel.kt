package org.deiverbum.app.feature.universalis

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
import org.deiverbum.app.feature.universalis.navigation.UniversalisRoute
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
    private val route: UniversalisRoute = savedStateHandle.toRoute()

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
            sortBy = HomeSortField.ID,
            date = selectedDate.value,
            title = route.initialTopicId!!,
            selectedTopicId = LiturgyHelper.liturgyByName(route.initialTopicId!!)
                .toString()//selectedTopicId.value!!,
        ),
        UniversalisUiState::UniversalisData,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UniversalisUiState.Loading,
    )

    fun followTopic(followedTopicId: String, followed: Boolean) {
        viewModelScope.launch {
            //userDataRepository.setTopicIdFollowed(followedTopicId, followed)
        }
    }


    fun onTopicClick(topicId: String?) {
        //savedStateHandle.toRoute<UniversalisRoute>().initialTopicId = topicId
        savedStateHandle[selectedTopicIdKey] = topicId
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

    data class UniversalisData(
        val selectedTopicId: String?,
        //val isReader: Boolean=true,
        //val topicId:Int,
        val topics: List<UniversalisRequest>,
    ) : UniversalisUiState
    data object Empty : UniversalisUiState

}
