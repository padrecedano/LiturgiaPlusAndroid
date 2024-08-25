package org.deiverbum.app.feature.universalis

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import org.deiverbum.app.util.Utils
import javax.inject.Inject

@HiltViewModel
class UniversalisViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    getTopicWithDate: GetUniversalisUseCase,

) : ViewModel() {

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    private val selectedTopicId: StateFlow<String?> =
        savedStateHandle.getStateFlow(TOPIC_ID_ARG, null) //TODO: Poner null como initialValue

    val uiState: StateFlow<InterestsUiStateInHome> = combine(
        selectedTopicId,
        getTopicWithDate(
            sortBy = HomeSortField.ID,
            date = Utils.hoy.toInt(),
            topicId = selectedTopicId.value!!.toInt()
        ),
        InterestsUiStateInHome::InterestsInHome,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = InterestsUiStateInHome.Loading,
    )

    fun followTopic(followedTopicId: String, followed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setTopicIdFollowed(followedTopicId, followed)
        }
    }

    fun onTopicClick(topicId: String?) {
        savedStateHandle[TOPIC_ID_ARG] = topicId
        // topicIdd = topicId!!
    }
}

sealed interface InterestsUiStateInHome {
    data object Loading : InterestsUiStateInHome
    data object Error : InterestsUiStateInHome

    data class InterestsInHome(
        val selectedTopicId: String?,
        val topics: List<UniversalisRequest>,
    ) : InterestsUiStateInHome

    data object Empty : InterestsUiStateInHome
}

data class MainScreenState(
    val isButtonEnabled: Boolean = true,
    val text: String = ""
)