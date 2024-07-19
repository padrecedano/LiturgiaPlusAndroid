package org.deiverbum.app.feature.topic

import NiaIcons
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.core.designsystem.component.NiaFilterChip
import org.deiverbum.app.core.designsystem.component.NiaLoadingWheel
import org.deiverbum.app.core.designsystem.component.scrollbar.DraggableScrollbar
import org.deiverbum.app.core.designsystem.component.scrollbar.rememberDraggableScroller
import org.deiverbum.app.core.designsystem.component.scrollbar.scrollbarState
import org.deiverbum.app.core.designsystem.theme.NiaTheme
import org.deiverbum.app.core.model.data.FollowableUniversalis
import org.deiverbum.app.core.ui.TrackScreenViewEvent
import org.deiverbum.app.core.ui.TrackScrollJank
import org.deiverbum.app.core.ui.userUniversalisResourceCardItems
import timber.log.Timber

@Composable
internal fun TopicRouteUniversalis(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TopicViewModelUniversalis = hiltViewModel(),
) {
    val topicUiState: TopicUiStateUniversalis by viewModel.topicUiState.collectAsStateWithLifecycle()
    val newsUiState: NewsUiStateUniversalis by viewModel.newsUiState.collectAsStateWithLifecycle()

    TrackScreenViewEvent(screenName = "Topic: ${viewModel.topicId}")
    TopicScreenUniversalis(
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
internal fun TopicScreenUniversalis(
    topicUiState: TopicUiStateUniversalis,
    newsUiState: NewsUiStateUniversalis,
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
                TopicUiStateUniversalis.Loading -> item {
                    NiaLoadingWheel(
                        modifier = modifier,
                        contentDesc = "stringResource(id = string.feature_topic_loading)",
                    )
                }

                TopicUiStateUniversalis.Error -> Timber.e("ERRR TopicScreenUniversalis")
                is TopicUiStateUniversalis.Success -> {
                    item {
                        TopicToolbarUniversalis(
                            showBackButton = showBackButton,
                            onBackClick = onBackClick,
                            onFollowClick = onFollowClick,
                            uiState = topicUiState.followableTopic,
                        )
                    }
                    topicBody(
                        //name = topicUiState.followableTopic.topic.getAllForView(TodayRequest(1,1,true,true)).toString(),
                        name = "Laudes",
                        description = topicUiState.followableTopic.topic.fecha,
//description=todayDate,
                        //description = topicUiState.followableTopic.topic.fecha,
                        news = newsUiState,
                        imageUrl = topicUiState.followableTopic.topic.fecha,
                        onBookmarkChanged = onBookmarkChanged,
                        onNewsResourceViewed = onNewsResourceViewed,
                        onTopicClick = onTopicClick,
                    )
                }
            }
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
        val itemsAvailable = topicItemsSize(topicUiState, newsUiState)
        val scrollbarState = state.scrollbarState(
            itemsAvailable = itemsAvailable,
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

private fun topicItemsSize(
    topicUiState: TopicUiStateUniversalis,
    newsUiState: NewsUiStateUniversalis,
) = when (topicUiState) {
    TopicUiStateUniversalis.Error -> 0 // Nothing
    TopicUiStateUniversalis.Loading -> 1 // Loading bar
    is TopicUiStateUniversalis.Success -> when (newsUiState) {
        NewsUiStateUniversalis.Error -> 0 // Nothing
        NewsUiStateUniversalis.Loading -> 1 // Loading bar
        is NewsUiStateUniversalis.Success -> 2 + newsUiState.news.size // Toolbar, header
    }
}


fun LazyListScope.topicBody(
    name: String,
    description: String,
    news: NewsUiStateUniversalis,
    imageUrl: String,
    onBookmarkChanged: (String, Boolean) -> Unit,
    onNewsResourceViewed: (String) -> Unit,
    onTopicClick: (String) -> Unit,
) {
    // TODO: Show icon if available
    item {
        TopicHeader(name, description, imageUrl)
        //Text(news.f)
    }

    userNewsResourceCards(news, onBookmarkChanged, onNewsResourceViewed, onTopicClick)
}

@Composable
private fun TopicHeader(name: String, description: String, imageUrl: String) {
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

// TODO: Could/should this be replaced with [LazyGridScope.newsFeed]?

private fun LazyListScope.userNewsResourceCards(
    news: NewsUiStateUniversalis,
    onBookmarkChanged: (String, Boolean) -> Unit,
    onNewsResourceViewed: (String) -> Unit,
    onTopicClick: (String) -> Unit,
) {
    when (news) {
        is NewsUiStateUniversalis.Success -> {
            userUniversalisResourceCardItems(
                items = news.news,
                onToggleBookmark = { /*onBookmarkChanged(it.id, !it.isSaved)*/ },
                itemModifier = Modifier.padding(24.dp),
            )
        }

        is NewsUiStateUniversalis.Loading -> item {
            NiaLoadingWheel(contentDesc = "Loading news") // TODO
        }

        else -> item {
            Text("Error") // TODO
        }
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
    // TODO: Show icon if available
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

// TODO: Could/should this be replaced with [LazyGridScope.newsFeed]?

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
            NiaLoadingWheel(contentDesc = "Loading news") // TODO
        }

        else -> item {
            Text("Error") // TODO
        }
    }
}
*/

@Preview
@Composable
private fun TopicBodyPreview() {
    NiaTheme {
        LazyColumn {
            topicBody(
                name = "Jetpack Compose",
                description = "Lorem ipsum maximum",
                news = NewsUiState.Success(emptyList()),
                imageUrl = "",
                onBookmarkChanged = { _, _ -> },
                onNewsResourceViewed = {},
                onTopicClick = {},
            )
        }
    }
}

@Composable
private fun TopicToolbarUniversalis(
    uiState: FollowableUniversalis,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = true,
    onBackClick: () -> Unit = {},
    onFollowClick: (Boolean) -> Unit = {},
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
        val selected = uiState.isFollowed
        NiaFilterChip(
            selected = selected,
            onSelectedChange = onFollowClick,
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
