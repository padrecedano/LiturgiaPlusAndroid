package org.deiverbum.app.feature.interests

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
import org.deiverbum.app.core.model.data.FollowableUniversalis
import org.deiverbum.app.domain.GetFollowableUniversalisUseCase
import org.deiverbum.app.domain.UniversalisSortField
import org.deiverbum.app.feature.interests.navigation.TOPIC_ID_ARG
import javax.inject.Inject

@HiltViewModel
class InterestsViewModelNew @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    getFollowableTopics: GetFollowableUniversalisUseCase,
) : ViewModel() {

    val selectedTopicId: StateFlow<String?> = savedStateHandle.getStateFlow(TOPIC_ID_ARG, null)

    val uiState: StateFlow<InterestsUiStateNew> = combine(
        selectedTopicId,
        getFollowableTopics(sortBy = UniversalisSortField.NAME),
        InterestsUiStateNew::Interests,
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = InterestsUiStateNew.Loading,
    )

    fun followTopic(followedTopicId: String, followed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setTopicIdFollowed(followedTopicId, followed)
        }
    }

    fun onTopicClick(topicId: String?) {
        savedStateHandle[TOPIC_ID_ARG] = topicId
    }
}

sealed interface InterestsUiStateNew {
    data object Loading : InterestsUiStateNew

    data class Interests(
        val selectedTopicId: String?,
        val topics: List<FollowableUniversalis>,
    ) : InterestsUiStateNew

    data object Empty : InterestsUiStateNew
}
