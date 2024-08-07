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
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UniversalisResourceQuery
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.data.repository.UserUniversalisResourceRepository
import org.deiverbum.app.core.model.data.TopicRequest
import org.deiverbum.app.core.model.data.UniversalisResource
import org.deiverbum.app.core.model.data.UserUniversalisResource
import org.deiverbum.app.core.result.Result
import org.deiverbum.app.core.result.asResult
import org.deiverbum.app.feature.universalis.navigation.UniversalisArgs
import org.deiverbum.app.util.Utils
import javax.inject.Inject

@HiltViewModel
class TopicViewModelUniversalis @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userDataRepository: UserDataRepository,
    topicsRepository: UniversalisRepository,
    userNewsResourceRepository: UserUniversalisResourceRepository,
) : ViewModel() {

    private val topicArgs: UniversalisArgs = UniversalisArgs(savedStateHandle)

    val topicId = topicArgs.topicId

    val topicUiState: StateFlow<TopicUiState> = topicUiState(
        topicId = topicArgs.topicId,
        userDataRepository = userDataRepository,
        topicsRepository = topicsRepository,
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TopicUiState.Loading,
        )

    val newsUiState: StateFlow<UniversalisUiState> = newsUiState(
        topicId = topicArgs.topicId,
        userDataRepository = userDataRepository,
        userNewsResourceRepository = userNewsResourceRepository,
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UniversalisUiState.Loading,
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

    fun setNewsResourceViewed(newsResourceId: String, viewed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setNewsResourceViewed(newsResourceId, viewed)
        }
    }
}

private fun topicUiState(
    topicId: String,
    userDataRepository: UserDataRepository,
    topicsRepository: UniversalisRepository,
): Flow<TopicUiState> {
    // Observe the followed topics, as they could change over time.
    val followedTopicIds: Flow<Set<String>> =
        userDataRepository.userData
            .map { it.followedTopics }

    // Observe topic information
    val topicStream: Flow<List<UniversalisResource>> = topicsRepository.getUniversalisByDate(
        //Utils.hoy.toInt()
        UniversalisResourceQuery(filterDates = setOf(Utils.hoy.toInt())),//topicId,
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
                    TODO()
                    /*val (followedTopics, topic) = followedTopicToTopicResult.data
                    TopicUiState.Success(
                        topic = TopicRequest(
                            date = 1,"topic.todayDate",
                            //resource = "",//topic.getAllForView(),
                            data= listOf(),
                            dynamic = UserDataDynamic(),
                        ),
                    )
                */
                }

                is Result.Loading -> TopicUiState.Loading
                is Result.Error -> TopicUiState.Error
            }
        }
}

private fun newsUiState(
    topicId: String,
    userNewsResourceRepository: UserUniversalisResourceRepository,
    userDataRepository: UserDataRepository,
): Flow<UniversalisUiState> {
    // Observe news
    val newsStream: Flow<List<UserUniversalisResource>> = userNewsResourceRepository.observeAll(
        UniversalisResourceQuery(filterDates = setOf(element = topicId.length)),
    )

    // Observe bookmarks
    val bookmark: Flow<Set<String>> = userDataRepository.userData
        .map { it.bookmarkedNewsResources }

    return combine(newsStream, bookmark, ::Pair)
        .asResult()
        .map { newsToBookmarksResult ->
            when (newsToBookmarksResult) {
                is Result.Success -> UniversalisUiState.Success(newsToBookmarksResult.data.first)
                is Result.Loading -> UniversalisUiState.Loading
                is Result.Error -> UniversalisUiState.Error
            }
        }
}

sealed interface TopicUiState {
    data class Success(val topic: TopicRequest) : TopicUiState
    data object Error : TopicUiState
    data object Loading : TopicUiState
}

sealed interface UniversalisUiState {
    data class Success(val data: List<UserUniversalisResource>) : UniversalisUiState
    data object Error : UniversalisUiState
    data object Loading : UniversalisUiState
}
