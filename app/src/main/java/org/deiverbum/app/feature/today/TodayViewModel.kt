package org.deiverbum.app.feature.today

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetHomeTopicsUseCase
import org.deiverbum.app.core.domain.HomeSortField
import org.deiverbum.app.core.model.data.FollowableUITopic
import org.deiverbum.app.feature.today.navigation.TOPIC_ID_ARG
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    getFollowableTopics: GetHomeTopicsUseCase,
) : ViewModel() {
    private var topicIdd = savedStateHandle.get<String>("topicId") ?: ""

    val selectedTopicId: StateFlow<String?> = savedStateHandle.getStateFlow(TOPIC_ID_ARG, null)

    val uiState: StateFlow<TodayUiState> = combine(
        selectedTopicId,
        getFollowableTopics(sortBy = HomeSortField.ID),
        TodayUiState::Todays,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TodayUiState.Loading,
    )
    /*
        fun followTopic(followedTopicId: String, followed: Boolean) {
            viewModelScope.launch {
                userDataRepository.setTopicIdFollowed(followedTopicId, followed)
            }
        }
    */
    /*fun onTopicClick(topicId: String?) {
        //savedStateHandle[TOPIC_ID_ARG] = topicId
    }*/
}

sealed interface TodayUiState {
    data object Loading : TodayUiState

    data class Todays(
        val selectedTopicId: String?,
        val topics: List<FollowableUITopic>,
    ) : TodayUiState

    data object Empty : TodayUiState
}
