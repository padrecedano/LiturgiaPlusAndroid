package org.deiverbum.app.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetHomeTopicsUseCase
import org.deiverbum.app.feature.interests.navigation.TOPIC_ID_ARG
import javax.inject.Inject

@HiltViewModel
class InterestsViewModelInHomeCopy @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    getFollowableTopics: GetHomeTopicsUseCase,
) : ViewModel() {

    //var property = selectedTopicId.
    private var topicIdd = savedStateHandle.get<String>("topicId") ?: ""

    val selectedTopicId: StateFlow<String?> =
        savedStateHandle.getStateFlow(TOPIC_ID_ARG, "1") //TODO: Poner null como initialValue

    val uiState: StateFlow<InterestsUiStateInHome> = TODO() /*combine(
        selectedTopicId,
        getFollowableTopics(sortBy = HomeSortField.ID),
        InterestsUiStateInHome::InterestsInHome,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = InterestsUiStateInHome.Loading,
    )*/

    fun followTopic(followedTopicId: String, followed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setTopicIdFollowed(followedTopicId, followed)
        }
    }

    fun onTopicClick(topicId: String?) {
        savedStateHandle[TOPIC_ID_ARG] = topicId
        topicIdd = topicId!!
    }
}

