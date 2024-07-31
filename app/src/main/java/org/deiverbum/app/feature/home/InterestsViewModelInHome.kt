package org.deiverbum.app.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetFollowableUniversalisUseCase
import org.deiverbum.app.core.domain.HomeSortField
import org.deiverbum.app.core.model.data.TopicRequest
import org.deiverbum.app.feature.interests.navigation.TOPIC_ID_ARG
import org.deiverbum.app.util.Utils
import javax.inject.Inject

@HiltViewModel
class InterestsViewModelInHome @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    getFollowableTopicss: GetFollowableUniversalisUseCase,
    //getFollowableTopics: GetTodayDataUseCase,

) : ViewModel() {

    //var property = selectedTopicId.
    private var topicIdd = savedStateHandle.get<String>("topicId") ?: ""

    val selectedTopicId: StateFlow<String?> =
        savedStateHandle.getStateFlow(TOPIC_ID_ARG, null) //TODO: Poner null como initialValue

    val uiState: StateFlow<InterestsUiStateInHome> = combine(
        selectedTopicId,
        getFollowableTopicss(
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
    private val shouldShowOnboarding: Flow<Boolean> =
        userDataRepository.userData.map { !it.shouldHideOnboarding }

    /*
        val onboardingUiState: StateFlow<OnboardingUiState> =
            combine(
                shouldShowOnboarding,
                getFollowableTopics(20240729),
            ) { shouldShowOnboarding, topics ->
                if (shouldShowOnboarding && topics.isNotEmpty()) {
                    OnboardingUiState.Shownn(topics = topics)
                } else {
                    OnboardingUiState.NotShown
                }
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = OnboardingUiState.Loading,
                )
    */

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
    //data class Success(val followableTopic: FollowableTopic) : InterestsUiStateInHome

    data class InterestsInHome(
        val selectedTopicId: String?,
        val topics: List<TopicRequest>,
    ) : InterestsUiStateInHome

    data object Empty : InterestsUiStateInHome
}
