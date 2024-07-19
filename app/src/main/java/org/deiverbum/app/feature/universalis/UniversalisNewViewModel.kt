/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.deiverbum.app.feature.universalis


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
import org.deiverbum.app.core.data.repository.TodaysRepository
import org.deiverbum.app.core.data.repository.UniversalisResourceQuery
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.data.repository.UserUniversalisResourceRepository
import org.deiverbum.app.core.model.data.FollowableUniversalis
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UserUniversalisResource
import org.deiverbum.app.core.result.Result
import org.deiverbum.app.core.result.asResult
import org.deiverbum.app.feature.interests.navigation.TOPIC_ID_ARG
import org.deiverbum.app.feature.universalis.navigation.UniversalisArgs
import javax.inject.Inject

@HiltViewModel
class UniversalisNewViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,

    private val userDataRepository: UserDataRepository,
    topicsRepository: TodaysRepository,
    userNewsResourceRepository: UserUniversalisResourceRepository,
) : ViewModel() {


    private val topicArgs: UniversalisArgs = UniversalisArgs(savedStateHandle)


    val topicId = topicArgs.topicId


    val todayNewUiState: StateFlow<TodayNewUiState> = todayNewUiState(
        topicId = topicArgs.topicId,
        userDataRepository = userDataRepository,
        topicsRepository = topicsRepository,
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TodayNewUiState.Loading,
        )


    val universalisNewUiState: StateFlow<UniversalisNewUiState> = universalisNewUiState(
        topicId = topicArgs.topicId,
        userDataRepository = userDataRepository,
        userNewsResourceRepository = userNewsResourceRepository,
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UniversalisNewUiState.Loading,
        )


    fun followTopicToggle(followed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setTopicIdFollowed(topicArgs.topicId, followed)
        }
    }

    fun bookmarkNews(newsResourceId: String, bookmarked: Boolean) {
        viewModelScope.launch {
            userDataRepository.setNewsResourceBookmarked(newsResourceId, bookmarked)
        }
    }

    fun onTopicClick(topicId: String?) {
        savedStateHandle[TOPIC_ID_ARG] = topicId
    }

    fun setNewsResourceViewed(newsResourceId: String, viewed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setNewsResourceViewed(newsResourceId, viewed)
        }
    }
}

private fun todayNewUiState(
    topicId: String,
    userDataRepository: UserDataRepository,
    topicsRepository: TodaysRepository,
): Flow<TodayNewUiState> {
    // Observe the followed topics, as they could change over time.
    val followedTopicIds: Flow<Set<String>> =
        userDataRepository.userData
            .map { it.followedTopics }

    // Observe topic information
    val topicStream: Flow<Universalis> = topicsRepository.getTopic(
        id = 20240716// topicId.toInt(),
    )

    return combine(
        followedTopicIds,
        topicStream,
        ::Pair,
    )
        .asResult()
        .map { followedTopicToTopicResult ->
            when (followedTopicToTopicResult) {
                is Result.Success -> {
                    val (followedTopics, topic) = followedTopicToTopicResult.data
                    TodayNewUiState.Success(
                        followableTopic = FollowableUniversalis(
                            topic = topic,
                            content = topic.getAllForView(),
                            isFollowed = false,//topicId in followedTopics,
                        ),
                    )
                }

                is Result.Loading -> TodayNewUiState.Loading
                is Result.Error -> TodayNewUiState.Error
            }
        }
}

private fun universalisNewUiState(
    topicId: String,
    userNewsResourceRepository: UserUniversalisResourceRepository,
    userDataRepository: UserDataRepository,
): Flow<UniversalisNewUiState> {
    // Observe news
    val newsStream: Flow<List<UserUniversalisResource>> = userNewsResourceRepository.observeAll(
        UniversalisResourceQuery(filterTopicIds = setOf(element = topicId)),
    )

    // Observe bookmarks
    val bookmark: Flow<Set<String>> = userDataRepository.userData
        .map { it.bookmarkedNewsResources }

    return combine(newsStream, bookmark, ::Pair)
        .asResult()
        .map { newsToBookmarksResult ->
            when (newsToBookmarksResult) {
                is Result.Success -> UniversalisNewUiState.Success(newsToBookmarksResult.data.first)
                is Result.Loading -> UniversalisNewUiState.Loading
                is Result.Error -> UniversalisNewUiState.Error
                //is Result.Success -> TODO()
            }
        }
}

sealed interface TodayNewUiState {
    data class Success(
        val followableTopic: FollowableUniversalis
    ) : TodayNewUiState

    data object Error : TodayNewUiState
    data object Loading : TodayNewUiState
}

sealed interface UniversalisNewUiState {
    data class Success(val news: List<UserUniversalisResource>) : UniversalisNewUiState
    data object Error : UniversalisNewUiState
    data object Loading : UniversalisNewUiState
}
