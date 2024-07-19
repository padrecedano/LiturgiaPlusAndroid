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

import NiaIcons
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.core.designsystem.component.NiaFilterChip
import org.deiverbum.app.core.designsystem.component.NiaIconToggleButton
import org.deiverbum.app.core.designsystem.component.NiaLoadingWheel
import org.deiverbum.app.core.designsystem.component.scrollbar.DraggableScrollbar
import org.deiverbum.app.core.designsystem.component.scrollbar.rememberDraggableScroller
import org.deiverbum.app.core.designsystem.component.scrollbar.scrollbarState
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.model.data.UserUniversalisResource
import org.deiverbum.app.core.ui.InterestsItem
import org.deiverbum.app.core.ui.TrackScreenViewEvent
import org.deiverbum.app.core.ui.TrackScrollJank
import org.deiverbum.app.feature.interests.InterestsUiState
import org.deiverbum.app.feature.interests.InterestsViewModel
import org.deiverbum.app.ui.home.HomeScreenBody


@Composable
internal fun UniversalisRoute(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UniversalisNewViewModel = hiltViewModel(),
    viewModelInterests: InterestsViewModel = hiltViewModel(),

    ) {
    val topicUiState: TodayNewUiState by viewModel.todayNewUiState.collectAsStateWithLifecycle()
    val newsUiState: UniversalisNewUiState by viewModel.universalisNewUiState.collectAsStateWithLifecycle()
    val uiState by viewModelInterests.uiState.collectAsStateWithLifecycle()

    TrackScreenViewEvent(screenName = "Topic: ${viewModel.topicId}")

    //HomeScreenBody(onNextButtonClicked= onTopicClick)
    HomeScreenBody(onNextButtonClicked = {
        viewModel.onTopicClick(it)
        onTopicClick(it)
    })
    UniversalisScreen(
        topicUiState = topicUiState,
        newsUiState = newsUiState,
        uiState = uiState,
        modifier = modifier.testTag("topic:${viewModel.topicId}"),
        showBackButton = showBackButton,
        onBackClick = onBackClick,
        onFollowClick = viewModel::followTopicToggle,
        onBookmarkChanged = viewModel::bookmarkNews,
        onNewsResourceViewed = { viewModel.setNewsResourceViewed(it, true) },
        onTopicClick = onTopicClick,
    )
}


@VisibleForTesting
@Composable
internal fun UniversalisScreen(
    topicUiState: TodayNewUiState,
    newsUiState: UniversalisNewUiState,
    uiState: InterestsUiState,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onFollowClick: (Boolean) -> Unit,
    onTopicClick: (String) -> Unit,
    onBookmarkChanged: (String, Boolean) -> Unit,
    onNewsResourceViewed: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    val state = rememberLazyListState()
    TrackScrollJank(scrollableState = state, stateName = "topic:screen")
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {

        when (uiState) {
            InterestsUiState.Loading ->
                Log.d("a", "a")

            is InterestsUiState.Interests ->
                //Text(uiState.topics[0].topic.longDescription)
                //Log.d("a","aaaaaaa")
//Text("Lorem")
                TODO()
            /*TopicsTabContent(
                topics = uiState.topics,
                onTopicClick = onTopicClick,
                onFollowButtonClick = onBookmarkChanged,
                selectedTopicId = uiState.selectedTopicId,
                highlightSelectedTopic = true,
                modifier = modifier,
            )*/



            is InterestsUiState.Empty ->
                //Log.d("a","a")

                InterestsEmptyScreen()
        }

        when (newsUiState) {


            UniversalisNewUiState.Loading -> {
                NiaLoadingWheel(
                    modifier = modifier,
                    contentDesc = "stringResource(id = string.feature_topic_loading)",
                )
            }

            UniversalisNewUiState.Error ->
                Log.e("ERRR", "UniversalisNewUiState.Error")

            is UniversalisNewUiState.Success ->
                UniversalisTabContent(topics = newsUiState.news)
            /*UniversalisToolbar(
                showBackButton = showBackButton,
                onBackClick = onBackClick,
                //onFollowClick = onFollowClick,
                //uiState = newsUiState.news,
            )*/
            //UniversalisContent(topics =newsUiState.news )

        }
        //universalisContent(topics =newsUiState.news )
        /*universalisBody(
            //name = topicUiState.followableTopic.topic.todayDate.toString(),
            //content =topicUiState.followableTopic.topic.getAllForView(TodayRequest(1,1,true,true)),
            //content=topicUiState.followableTopic.topic.
            name="newsUiState.news[0].todayDate.toString()",
            content=newsUiState.news[0].followableTopics[0].topic.getAllForView(
                TodayRequest(1,1,true,true)
            ),
            //content= SpannableStringBuilder("Lorem ipsum"),
            news = newsUiState,

            /*description = topicUiState.followableTopic.topic.longDescription,
            news = newsUiState,
            imageUrl = topicUiState.followableTopic.topic.imageUrl,
            onBookmarkChanged = onBookmarkChanged,
            onNewsResourceViewed = onNewsResourceViewed,*/
            onTopicClick = onTopicClick,
        )*/
    }

}
/*item {
    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
}*/
//}
//val itemsAvailable = universalisItemsSize(topicUiState, newsUiState)
/*val scrollbarState = state.scrollbarState(
    itemsAvailable = itemsAvailable as Int,
)*/
/*state.DraggableScrollbar(
    modifier = Modifier
        .fillMaxHeight()
        .windowInsetsPadding(WindowInsets.systemBars)
        .padding(horizontal = 2.dp)
        .align(Alignment.CenterEnd),
    state = scrollbarState,
    orientation = Orientation.Vertical,
    onThumbMoved = state.rememberDraggableScroller(
        itemsAvailable = itemsAvailable,
    ),
)*/
//}
//}


@Composable
fun UniversalisItem(
    name: String,
    following: Boolean,
    topicImageUrl: String,
    onClick: () -> Unit,
    onFollowButtonClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    description: String = "",
    isSelected: Boolean = false,
) {
    ListItem(
        leadingContent = {
            //InterestsIcon(topicImageUrl, iconModifier.size(64.dp))
        },
        headlineContent = {
            Text(text = name)
        },
        supportingContent = {
            Text(text = description)
        },
        trailingContent = {
            NiaIconToggleButton(
                checked = following,
                onCheckedChange = onFollowButtonClick,
                icon = {
                    Icon(
                        imageVector = NiaIcons.Add,
                        contentDescription = "stringResource(string.core_ui_interests_card_follow_button_content_desc)",
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = NiaIcons.Check,
                        contentDescription = "stringResource(string.core_ui_interests_card_unfollow_button_content_desc,)",
                    )
                },
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                Color.Transparent
            },
        ),
        modifier = modifier
            .semantics(mergeDescendants = true) {
                selected = isSelected
            }
            .clickable(enabled = true, onClick = onClick),
    )
}


@Composable
fun UniversalisTabContent(
    topics: List<UserUniversalisResource>,
    modifier: Modifier = Modifier,

    ) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        val scrollableState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .testTag("interests:topics"),
            contentPadding = PaddingValues(vertical = 16.dp),
            state = scrollableState,
        ) {
            //Text("lll")

            topics.forEach { item ->
                item.followableTopics.forEach { followableTopic ->
                    val currentTopic = followableTopic.topic
                    val topicId = followableTopic.topic.todayDate
                    item(key = topicId) {
                        val isSelected = true//highlightSelectedTopic && topicId == selectedTopicId
                        InterestsItem(
                            name = currentTopic.todayDate.toString(),
                            following = followableTopic.isFollowed,
                            description = followableTopic.topic.fecha,
                            topicImageUrl = followableTopic.topic.fecha,
                            onClick = { /*onTopicClick(topicId)*/ },
                            onFollowButtonClick = { /*onFollowButtonClick(topicId, it)*/ },
                            isSelected = isSelected,
                        )
                    }
                }

                /*if (withBottomSpacer) {
                    item {
                        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                    }
                }*/
            }


        }
        val scrollbarState = scrollableState.scrollbarState(
            itemsAvailable = topics.size,
        )
        scrollableState.DraggableScrollbar(
            modifier = Modifier
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(horizontal = 2.dp)
                .align(Alignment.CenterEnd),
            state = scrollbarState,
            orientation = Orientation.Vertical,
            onThumbMoved = scrollableState.rememberDraggableScroller(
                itemsAvailable = topics.size,
            ),
        )
    }
}


@VisibleForTesting
@Composable
internal fun UniversalisScreenn(
    topicUiState: TodayNewUiState,
    newsUiState: UniversalisNewUiState,
    uiState: InterestsUiState,
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onFollowClick: (Boolean) -> Unit,
    onTopicClick: (String) -> Unit,
    onBookmarkChanged: (String, Boolean) -> Unit,
    onNewsResourceViewed: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyListState()
    TrackScrollJank(scrollableState = state, stateName = "topic:screen")
    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            /*item {
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            }*/


            when (uiState) {
                InterestsUiState.Loading ->
                    Log.d("a", "a")

                is InterestsUiState.Interests ->
                    Log.d("a", "aaaaaaa")

                is InterestsUiState.Empty ->
                    Log.d("a", "a")

                //InterestsEmptyScreen()
            }

            when (newsUiState) {


                UniversalisNewUiState.Loading -> item {
                    NiaLoadingWheel(
                        modifier = modifier,
                        contentDesc = "stringResource(id = string.feature_topic_loading)",
                    )
                }

                UniversalisNewUiState.Error ->
                    Log.e("ERRR", "UniversalisScreen UniversalisNewUiState.Error")

                is UniversalisNewUiState.Success -> {
                    item {
                        /*UniversalisToolbar(
                            showBackButton = showBackButton,
                            onBackClick = onBackClick,
                            //onFollowClick = onFollowClick,
                            //uiState = newsUiState.news,
                        )*/
                        universalisContent(topics = newsUiState.news)

                    }
                    //universalisContent(topics =newsUiState.news )
                    /*universalisBody(
                        //name = topicUiState.followableTopic.topic.todayDate.toString(),
                        //content =topicUiState.followableTopic.topic.getAllForView(TodayRequest(1,1,true,true)),
                        //content=topicUiState.followableTopic.topic.
                        name="newsUiState.news[0].todayDate.toString()",
                        content=newsUiState.news[0].followableTopics[0].topic.getAllForView(
                            TodayRequest(1,1,true,true)
                        ),
                        //content= SpannableStringBuilder("Lorem ipsum"),
                        news = newsUiState,

                        /*description = topicUiState.followableTopic.topic.longDescription,
                        news = newsUiState,
                        imageUrl = topicUiState.followableTopic.topic.imageUrl,
                        onBookmarkChanged = onBookmarkChanged,
                        onNewsResourceViewed = onNewsResourceViewed,*/
                        onTopicClick = onTopicClick,
                    )*/
                }

            }
            /*item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }*/
        }
        val itemsAvailable = universalisItemsSize(topicUiState, newsUiState)
        val scrollbarState = state.scrollbarState(
            itemsAvailable = itemsAvailable as Int,
        )
        state.DraggableScrollbar(
            modifier = Modifier
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(horizontal = 2.dp)
                .align(Alignment.CenterEnd),
            state = scrollbarState,
            orientation = Orientation.Vertical,
            onThumbMoved = state.rememberDraggableScroller(
                itemsAvailable = itemsAvailable,
            ),
        )
    }
}

private fun universalisItemsSize(
    topicUiState: TodayNewUiState,
    newsUiState: UniversalisNewUiState,
) = when (topicUiState) {
    TodayNewUiState.Error -> 0 // Nothing
    TodayNewUiState.Loading -> 1 // Loading bar
    is TodayNewUiState.Success -> when (newsUiState) {
        UniversalisNewUiState.Error -> 0 // Nothing
        UniversalisNewUiState.Loading -> 1 // Loading bar
        is UniversalisNewUiState.Success -> 2 + newsUiState.news.size // Toolbar, header
        else -> {}
    }

    else -> {}
}


private fun LazyListScope.universalisBody(
    name: String,
    content: SpannableStringBuilder,
    /*description: String,*/
    news: UniversalisNewUiState,
    /*imageUrl: String,
    onBookmarkChanged: (String, Boolean) -> Unit,
    onNewsResourceViewed: (String) -> Unit,*/
    onTopicClick: (String) -> Unit,
) {
    item {
        UniversalisHeader(name, "description", "imageUrl")
        Text(text = content.toString())
    }

    /*userNewsResourceCards(news,
       // onBookmarkChanged, onNewsResourceViewed,
        onTopicClick)*/
}

@Composable
private fun UniversalisHeader(name: String, description: String, imageUrl: String) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
    ) {
        /*DynamicAsyncImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(216.dp)
                .padding(bottom = 12.dp),
        )*/
        Text(name, style = MaterialTheme.typography.displayMedium)
        if (description.isNotEmpty()) {
            Text(
                description,
                modifier = Modifier.padding(top = 24.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable

private fun BoxScope.userNewsResourceCards(
    news: UniversalisNewUiState,
    //onBookmarkChanged: (String, Boolean) -> Unit,
    //onNewsResourceViewed: (String) -> Unit,
    onTopicClick: (String) -> Unit,
) {
    when (news) {
        is UniversalisNewUiState.Success -> {
            topicsSingleContent(
                topics = news.news,
                //onTopicClick = onTopicClick,
                //onFollowButtonClick = followTopic,
                //selectedTopicId = uiState.selectedTopicId,
                //highlightSelectedTopic = highlightSelectedTopic,
                //modifier = modifier,
            )


            /*userUniversalisResourceCardItems(
                items = news.news,
                //onToggleBookmark = { onBookmarkChanged(it.id, !it.isSaved) },
                //onNewsResourceViewed = onNewsResourceViewed,
                //onTopicClick = onTopicClick,
                itemModifier = Modifier.padding(24.dp)
            )*/
        }

        is UniversalisNewUiState.Loading -> {
            //NiaLoadingWheel(contentDesc = "Loading news")
        }

        else -> {
            //Text("Error")
        }
    }
}


@Composable
fun BoxScope.topicsSingleContent(
    topics: List<UserUniversalisResource>,
    //onTopicClick: (String) -> Unit,
    //onFollowButtonClick: (String, Boolean) -> Unit,
    //modifier: Modifier = Modifier,
    /*withBottomSpacer: Boolean = true,
    selectedTopicId: String? = null,
    highlightSelectedTopic: Boolean = false,*/
) {
    Box {
        val scrollableState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .testTag("interests:topics"),
            contentPadding = PaddingValues(vertical = 16.dp),
            state = scrollableState,
        ) {
            topics.forEach { item ->
                item.followableTopics.forEach { followableTopic ->
                    val currentTopic = followableTopic.topic
                    val topicId = followableTopic.topic.todayDate
                    item(key = topicId) {
                        val isSelected = true//highlightSelectedTopic && topicId == selectedTopicId
                        InterestsItem(
                            name = currentTopic.todayDate.toString(),
                            following = followableTopic.isFollowed,
                            description = followableTopic.topic.fecha,
                            topicImageUrl = followableTopic.topic.fecha,
                            onClick = { /*onTopicClick(topicId)*/ },
                            onFollowButtonClick = { /*onFollowButtonClick(topicId, it)*/ },
                            isSelected = isSelected,
                        )
                    }
                }

                /*if (withBottomSpacer) {
                    item {
                        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                    }
                }*/
            }
            /*val scrollbarState = scrollableState.scrollbarState(
                itemsAvailable = topics.size,
            )
            scrollableState.DraggableScrollbar(
                modifier = Modifier
                    .fillMaxHeight()
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .padding(horizontal = 2.dp)
                    .align(Alignment.CenterEnd),
                state = scrollbarState,
                orientation = Orientation.Vertical,
                onThumbMoved = scrollableState.rememberDraggableScroller(
                    itemsAvailable = topics.size,
                ),
            )*/
        }
    }



    /*







    @Composable
    internal fun TopicRoute(
        showBackButton: Boolean,
        onBackClick: () -> Unit,
        onTopicClick: (String) -> Unit,
        modifier: Modifier = Modifier,
        viewModel: UniversalisNewViewModel = hiltViewModel(),
    ) {
        val topicUiState: TodayNewUiState by viewModel.todayNewUiState.collectAsStateWithLifecycle()
        val newsUiState: UniversalisNewUiState by viewModel.universalisNewUiState.collectAsStateWithLifecycle()

        TrackScreenViewEvent(screenName = "Topic: ${viewModel.topicId}")
        TopicScreen(
            topicUiState = topicUiState,
            newsUiState = newsUiState,
            modifier = modifier.testTag("topic:${viewModel.topicId}"),
            showBackButton = showBackButton,
            onBackClick = onBackClick,
            onFollowClick = viewModel::followTopicToggle,
            onBookmarkChanged = viewModel::bookmarkNews,
            onNewsResourceViewed = { viewModel.setNewsResourceViewed(it, true) },
            onTopicClick = onTopicClick,
        )
    }


    @VisibleForTesting
    @Composable
    internal fun TopicScreen(
        topicUiState: TodayNewUiState,
        newsUiState: UniversalisNewUiState,
        showBackButton: Boolean,
        onBackClick: () -> Unit,
        onFollowClick: (Boolean) -> Unit,
        onTopicClick: (String) -> Unit,
        onBookmarkChanged: (String, Boolean) -> Unit,
        onNewsResourceViewed: (String) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val state = rememberLazyListState()
        TrackScrollJank(scrollableState = state, stateName = "topic:screen")
        Box(
            modifier = modifier,
        ) {
            LazyColumn(
                state = state,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
                }
                when (topicUiState) {
                    TodayNewUiState.Loading -> item {
                        NiaLoadingWheel(
                            modifier = modifier,
                            contentDesc = "stringResource(id = string.feature_topic_loading)",
                        )
                    }

                    TodayNewUiState.Error ->
                        Log.d("ERRR", TodayNewUiState.Error.toString())

                    is TodayNewUiState.Success -> {
                        item {
                            TopicToolbar(
                                showBackButton = showBackButton,
                                onBackClick = onBackClick,
                                onFollowClick = onFollowClick,
                                uiState = topicUiState.followableTopic,
                            )
                        }
                        topicBody(
                            name = topicUiState.followableTopic.topic.fecha,
                            description = topicUiState.followableTopic.topic.getAllForView().text,
                            news = newsUiState,
                            imageUrl = topicUiState.followableTopic.topic.fecha,
                            onBookmarkChanged = onBookmarkChanged,
                            onNewsResourceViewed = onNewsResourceViewed,
                            onTopicClick = onTopicClick,
                        )
                    }

                    else -> {}
                }
                item {
                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                }
            }
            val itemsAvailable = topicItemsSize(topicUiState, newsUiState)
            val scrollbarState = state.scrollbarState(
                itemsAvailable = itemsAvailable as Int,
            )
            state.DraggableScrollbar(
                modifier = Modifier
                    .fillMaxHeight()
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .padding(horizontal = 2.dp)
                    .align(Alignment.CenterEnd),
                state = scrollbarState,
                orientation = Orientation.Vertical,
                onThumbMoved = state.rememberDraggableScroller(
                    itemsAvailable = itemsAvailable as Int,
                ),
            )
        }
    }

    private fun topicItemsSize(
        topicUiState: TodayNewUiState,
        newsUiState: UniversalisNewUiState,
    ) = when (topicUiState) {
        TodayNewUiState.Error -> 0 // Nothing
        TodayNewUiState.Loading -> 1 // Loading bar
        is TodayNewUiState.Success -> when (newsUiState) {
            UniversalisNewUiState.Error -> 0 // Nothing
            UniversalisNewUiState.Loading -> 1 // Loading bar
            is UniversalisNewUiState.Success -> 2 + newsUiState.news.size // Toolbar, header
            else -> {}
        }

        else -> {}
    }


    private fun LazyListScope.topicBody(
        name: String,
        description: String,
        news: UniversalisNewUiState,
        imageUrl: String,
        onBookmarkChanged: (String, Boolean) -> Unit,
        onNewsResourceViewed: (String) -> Unit,
        onTopicClick: (String) -> Unit,
    ) {
        item {
            TopicHeader(name, description, imageUrl)
        }

        userNewsResourceCards(news, onBookmarkChanged, onNewsResourceViewed, onTopicClick)
    }

    @Composable
    private fun TopicHeader(name: String, description: String, imageUrl: String) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
        ) {
            DynamicAsyncImage(
                imageUrl = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(216.dp)
                    .padding(bottom = 12.dp),
            )
            Text(name, style = MaterialTheme.typography.displayMedium)
            if (description.isNotEmpty()) {
                Text(
                    description,
                    modifier = Modifier.padding(top = 24.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }


    private fun LazyListScope.userNewsResourceCards(
        news: UniversalisNewUiState,
        onBookmarkChanged: (String, Boolean) -> Unit,
        onNewsResourceViewed: (String) -> Unit,
        onTopicClick: (String) -> Unit,
    ) {
        when (news) {
            is UniversalisNewUiState.Success -> {
                userNewsResourceCardItems(
                    items = news.news,
                    onToggleBookmark = { onBookmarkChanged(it.id, !it.isSaved) },
                    onNewsResourceViewed = onNewsResourceViewed,
                    onTopicClick = onTopicClick,
                    itemModifier = Modifier.padding(24.dp),
                )
            }

            is UniversalisNewUiState.Loading -> item {
                NiaLoadingWheel(contentDesc = "Loading news")
            }

            else -> item {
                Text("Error")
            }
        }
    }
    */
    /*

    @Preview
    @Composable
    private fun TopicBodyPreview() {
        NiaTheme {
            LazyColumn {
                universalisBody(
                    name = "Jetpack Compose",
                    //description = "Lorem ipsum maximum",
                    news = UniversalisNewUiState.Success(emptyList()),
                    /*imageUrl = "",
                    onBookmarkChanged = { _, _ -> },
                    onNewsResourceViewed = {},*/
                    onTopicClick = {},
                )
            }
        }
    }
    */
    @Composable
    fun UniversalisToolbar(
        //uiState: FollowableUniversalis,
        modifier: Modifier = Modifier,
        showBackButton: Boolean = true,
        onBackClick: () -> Unit = {},
        //onFollowClick: (Boolean) -> Unit = {},
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
        ) {
            if (showBackButton) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = NiaIcons.ArrowBack,
                        contentDescription = "com.google.samples.apps.nowinandroid.core.ui.R.string.core_ui_back",
                    )
                }
            } else {
                // Keeps the NiaFilterChip aligned to the end of the Row.
                Spacer(modifier = Modifier.width(1.dp))
            }
            val selected = true//uiState.isFollowed
            NiaFilterChip(
                selected = selected,
                onSelectedChange = {},//onFollowClick,
                modifier = Modifier.padding(end = 24.dp),
            ) {
                if (selected) {
                    Text("FOLLOWING")
                } else {
                    Text("NOT FOLLOWING")
                }
            }
        }
    }
}
/*

@DevicePreviews
@Composable
fun TopicScreenPopulated(
    @PreviewParameter(UserNewsResourcePreviewParameterProvider::class)
    userNewsResources: List<UserNewsResource>,
) {
    NiaTheme {
        NiaBackground {
            TopicScreen(
                topicUiState = TodayNewUiState.Success(userNewsResources[0].followableTopics[0]),
                newsUiState = UniversalisNewUiState.Success(userNewsResources),
                showBackButton = true,
                onBackClick = {},
                onFollowClick = {},
                onBookmarkChanged = { _, _ -> },
                onNewsResourceViewed = {},
                onTopicClick = {},
            )
        }
    }
}


@DevicePreviews
@Composable
fun TopicScreenLoading() {
    NiaTheme {
        NiaBackground {
            TopicScreen(
                topicUiState = TodayNewUiState.Loading,
                newsUiState = UniversalisNewUiState.Loading,
                showBackButton = true,
                onBackClick = {},
                onFollowClick = {},
                onBookmarkChanged = { _, _ -> },
                onNewsResourceViewed = {},
                onTopicClick = {},
            )
        }
    }
}
*/

@Composable
private fun BoxScope.universalisContent(
    topics: List<UserUniversalisResource>,
    /*onTopicClick: (String) -> Unit,
    onFollowButtonClick: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    withBottomSpacer: Boolean = true,
    selectedTopicId: String? = null,
    highlightSelectedTopic: Boolean = false,*/
) {
    Box {
        rememberLazyListState()
        /*LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .testTag("interests:topics"),
            contentPadding = PaddingValues(vertical = 16.dp),
            state = scrollableState,
        ) {*/
        topics.forEach { items ->
            items.followableTopics.forEach { followableTopic ->

                val topicId = 1//followableTopic.topic.todayDate
                //item(key = topicId) {
                //val isSelected = highlightSelectedTopic && topicId == selectedTopicId
                Text(
                    followableTopic.topic.getAllForView(TodayRequest(1, 1, true, true)).toString()
                )
            }
        }
    }

    /*if (withBottomSpacer) {
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }*/
}

/*val scrollbarState = scrollableState.scrollbarState(
    itemsAvailable = topics.size,
)
scrollableState.DraggableScrollbar(
    modifier = Modifier
        .fillMaxHeight()
        .windowInsetsPadding(WindowInsets.systemBars)
        .padding(horizontal = 2.dp)
        .align(Alignment.CenterEnd),
    state = scrollbarState,
    orientation = Orientation.Vertical,
    onThumbMoved = scrollableState.rememberDraggableScroller(
        itemsAvailable = topics.size,
    ),
)*/
//}
//}
@Composable
private fun InterestsEmptyScreen() {
    Text(text = "stringResource(id = R.string.feature_interests_empty_header)")
}