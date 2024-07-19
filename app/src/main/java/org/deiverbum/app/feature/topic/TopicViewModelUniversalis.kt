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
package org.deiverbum.app.feature.topic


import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.sp
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
import org.deiverbum.app.core.data.repository.UniversalisRepositoryy
import org.deiverbum.app.core.data.repository.UniversalisResourceQuery
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.data.repository.UserUniversalisResourceRepository
import org.deiverbum.app.core.model.data.FollowableUniversalis
import org.deiverbum.app.core.model.data.Universalis
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
    topicsRepository: UniversalisRepositoryy,
    userNewsResourceRepository: UserUniversalisResourceRepository,
) : ViewModel() {

    private val topicArgs: UniversalisArgs = UniversalisArgs(savedStateHandle)

    val topicId = topicArgs.topicId

    val topicUiState: StateFlow<TopicUiStateUniversalis> = topicUiState(
        topicId = topicArgs.topicId,
        userDataRepository = userDataRepository,
        topicsRepository = topicsRepository,
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TopicUiStateUniversalis.Loading,
        )

    val newsUiState: StateFlow<NewsUiStateUniversalis> = newsUiState(
        topicId = topicArgs.topicId,
        userDataRepository = userDataRepository,
        userNewsResourceRepository = userNewsResourceRepository,
    )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NewsUiStateUniversalis.Loading,
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
    topicsRepository: UniversalisRepositoryy,
): Flow<TopicUiStateUniversalis> {
    // Observe the followed topics, as they could change over time.
    val followedTopicIds: Flow<Set<String>> =
        userDataRepository.userData
            .map { it.followedTopics }

    // Observe topic information
    val topicStream: Flow<Universalis> = topicsRepository.getUniversalisById(
        //Utils.hoy.toInt()
        id = Utils.hoy,//topicId,
    )
    val tmpContent = AnnotatedString(
        text = "Hello World",
        // make "Hello" italic.
        spanStyles = listOf(
            AnnotatedString.Range(SpanStyle(fontStyle = FontStyle.Italic), 0, 5)
        ),
        // create two paragraphs with different alignment and indent settings.
        paragraphStyles = listOf(
            AnnotatedString.Range(ParagraphStyle(textAlign = TextAlign.Center), 0, 6),
            AnnotatedString.Range(ParagraphStyle(textIndent = TextIndent(5.sp)), 6, 11)
        )
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
                    TopicUiStateUniversalis.Success(
                        followableTopic = FollowableUniversalis(
                            topic = topic,
                            content = tmpContent,//topic.getAllForView(),
                            isFollowed = topicId in followedTopics,
                        ),
                    )
                }

                is Result.Loading -> TopicUiStateUniversalis.Loading
                is Result.Error -> TopicUiStateUniversalis.Error
            }
        }
}

private fun newsUiState(
    topicId: String,
    userNewsResourceRepository: UserUniversalisResourceRepository,
    userDataRepository: UserDataRepository,
): Flow<NewsUiStateUniversalis> {
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
                is Result.Success -> NewsUiStateUniversalis.Success(newsToBookmarksResult.data.first)
                is Result.Loading -> NewsUiStateUniversalis.Loading
                is Result.Error -> NewsUiStateUniversalis.Error
            }
        }
}

sealed interface TopicUiStateUniversalis {
    data class Success(val followableTopic: FollowableUniversalis) : TopicUiStateUniversalis
    data object Error : TopicUiStateUniversalis
    data object Loading : TopicUiStateUniversalis
}

sealed interface NewsUiStateUniversalis {
    data class Success(val news: List<UserUniversalisResource>) : NewsUiStateUniversalis
    data object Error : NewsUiStateUniversalis
    data object Loading : NewsUiStateUniversalis
}
