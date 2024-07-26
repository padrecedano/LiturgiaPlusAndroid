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
import org.deiverbum.app.core.analytics.AnalyticsEvent
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.data.repository.UserUniversalisResourceRepository
import org.deiverbum.app.core.data.util.SyncManager
import org.deiverbum.app.domain.GetFollowableTopicsUseCase
import org.deiverbum.app.feature.home.navigation.LINKED_NEWS_RESOURCE_ID
import org.deiverbum.app.feature.universalis.navigation.TOPIC_ID_ARG
import org.deiverbum.app.feature.universalis.navigation.UniversalisArgs
import javax.inject.Inject


@HiltViewModel
class HomeViewModelForDelete @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    syncManager: SyncManager,
    private val analyticsHelper: AnalyticsHelper,
    private val userDataRepository: UserDataRepository,
    //userNewsResourceRepository: UserNewsResourceRepository,
    userNewsResourceRepository: UserUniversalisResourceRepository,

    getFollowableTopics: GetFollowableTopicsUseCase,
) : ViewModel() {

    private val shouldShowOnboarding: Flow<Boolean> =
        userDataRepository.userData.map { !it.shouldHideOnboarding }

    private val topicArgs: UniversalisArgs = UniversalisArgs(savedStateHandle)


    val topicId = topicArgs.topicId

    val selectedTopicId: StateFlow<String?> = savedStateHandle.getStateFlow(TOPIC_ID_ARG, null)

    val deepLinkedNewsResource = savedStateHandle.getStateFlow<String?>(
        key = LINKED_NEWS_RESOURCE_ID,
        null,
    )
        /* .flatMapLatest { newsResourceId ->
             if (newsResourceId == null) {
                 flowOf(emptyList())
             } else {
                 userNewsResourceRepository.observeAll(
                     UniversalisResourceQuery(
                         filterNewsIds = setOf(newsResourceId),
                     ),
                 )
             }
         }
         .map { it.firstOrNull() }*/
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    val isSyncing = syncManager.isSyncing
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    /*
        val feedState: StateFlow<UniversalisFeedUiState> =
            userNewsResourceRepository.observeAllForFollowedTopics()
                .map(UniversalisFeedUiState::Success)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = UniversalisFeedUiState.Loading,
                )
    */
    val onboardingUiState: StateFlow<OnboardingUiState> =
        combine(
            shouldShowOnboarding,
            getFollowableTopics(),
        ) { shouldShowOnboarding, topics ->
            if (shouldShowOnboarding) {
                OnboardingUiState.Shown(topics = topics)
            } else {
                OnboardingUiState.NotShown
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = OnboardingUiState.Loading,
            )

    fun updateTopicSelection(topicId: String, isChecked: Boolean) {
        viewModelScope.launch {
            userDataRepository.setTopicIdFollowed(topicId, isChecked)
            //userDataRepository.setTopicIdFollowed("1", true)

        }
    }

    fun updateNewsResourceSaved(newsResourceId: String, isChecked: Boolean) {
        viewModelScope.launch {
            userDataRepository.setNewsResourceBookmarked(newsResourceId, isChecked)
        }
    }

    fun setNewsResourceViewed(newsResourceId: String, viewed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setNewsResourceViewed(newsResourceId, viewed)
        }
    }

    fun onDeepLinkOpened(newsResourceId: String) {
        /*if (newsResourceId == deepLinkedNewsResource.value?.id) {
            savedStateHandle[LINKED_NEWS_RESOURCE_ID] = null
        }*/
        analyticsHelper.logNewsDeepLinkOpen(newsResourceId = newsResourceId)
        viewModelScope.launch {
            userDataRepository.setNewsResourceViewed(
                newsResourceId = newsResourceId,
                viewed = true,
            )
        }
    }

    fun dismissOnboarding() {
        viewModelScope.launch {
            userDataRepository.setShouldHideOnboarding(true)
        }
    }

    fun onTopicClick(topicId: String?) {
        savedStateHandle[TOPIC_ID_ARG] = topicId
    }
}

private fun AnalyticsHelper.logNewsDeepLinkOpen(newsResourceId: String) =
    logEvent(
        AnalyticsEvent(
            type = "news_deep_link_opened",
            extras = listOf(
                AnalyticsEvent.Param(
                    key = LINKED_NEWS_RESOURCE_ID,
                    value = newsResourceId,
                ),
            ),
        ),
    )
