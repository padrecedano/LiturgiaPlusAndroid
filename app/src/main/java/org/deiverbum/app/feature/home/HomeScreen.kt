package org.deiverbum.app.feature.home

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
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.NiaFilterChip
import org.deiverbum.app.core.designsystem.component.NiaLoadingWheel
import org.deiverbum.app.core.designsystem.component.scrollbar.DraggableScrollbar
import org.deiverbum.app.core.designsystem.component.scrollbar.rememberDraggableScroller
import org.deiverbum.app.core.designsystem.component.scrollbar.scrollbarState
import org.deiverbum.app.core.designsystem.theme.NiaTheme
import org.deiverbum.app.core.model.data.TopicRequest
import org.deiverbum.app.core.ui.TrackScreenViewEvent
import org.deiverbum.app.core.ui.TrackScrollJank
import org.deiverbum.app.core.ui.userUniversalisResourceCardItems
import org.deiverbum.app.feature.topic.NewsUiState
import org.deiverbum.app.feature.topic.topicBody
import timber.log.Timber

@Composable
internal fun HomeRoute(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TopicViewModelUniversalis = hiltViewModel(),
) {
    val topicUiState: TopicUiState by viewModel.topicUiState.collectAsStateWithLifecycle()
    val universalisUiState: UniversalisUiState by viewModel.newsUiState.collectAsStateWithLifecycle()

    TrackScreenViewEvent(screenName = "Topic: ${viewModel.topicId}")
    HomeScreen(
        topicUiState = topicUiState,
        newsUiState = universalisUiState,
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
internal fun HomeScreen(
    topicUiState: TopicUiState,
    newsUiState: UniversalisUiState,
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
    //val icons: TypedArray = resources.obtainTypedArray(R.array.icons)
    val listy: Array<String> = stringArrayResource(R.array.ui_items)

    val listyy: Array<Array<String>> = arrayOf(stringArrayResource(R.array.ui_items))
    //val icons: TypedArray = stringArrayResource(R.array.ui_items)
    //val sensorIcons = resources.obtainTypedArray(R.array.sensor_icon_values)
    //Greetings(listy = listy)
    //Text("Lorem ipsum")
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
                TopicUiState.Loading -> item {
                    NiaLoadingWheel(
                        modifier = modifier,
                        contentDesc = "stringResource(id = string.feature_topic_loading)",
                    )
                }

                TopicUiState.Error -> Timber.e("ERRR TopicScreenUniversalis")
                is TopicUiState.Success -> {
                    item {
                        TopicToolbarUniversalis(
                            showBackButton = showBackButton,
                            onBackClick = onBackClick,
                            onFollowClick = onFollowClick,
                            uiState = topicUiState.topic,
                        )
                    }
                    topicBody(
                        name = "Laudes",
                        description = topicUiState.topic.date.toString(),
                        news = newsUiState,
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
    topicUiState: TopicUiState,
    newsUiState: UniversalisUiState,
) = when (topicUiState) {
    TopicUiState.Error -> 0 // Nothing
    TopicUiState.Loading -> 1 // Loading bar
    is TopicUiState.Success -> when (newsUiState) {
        UniversalisUiState.Error -> 0 // Nothing
        UniversalisUiState.Loading -> 1 // Loading bar
        is UniversalisUiState.Success -> 2 + newsUiState.data.size // Toolbar, header
    }
}

fun LazyListScope.topicBody(
    name: String,
    description: String,
    news: UniversalisUiState,
    onBookmarkChanged: (String, Boolean) -> Unit,
    onNewsResourceViewed: (String) -> Unit,
    onTopicClick: (String) -> Unit,
) {
    item {
        UniversalisHeader(name, description)
    }

    userUniversalisResourceCards(news, onBookmarkChanged, onNewsResourceViewed, onTopicClick)
}

@Composable
private fun UniversalisHeader(name: String, description: String) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
    ) {
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

private fun LazyListScope.userUniversalisResourceCards(
    universalis: UniversalisUiState,
    onBookmarkChanged: (String, Boolean) -> Unit,
    onNewsResourceViewed: (String) -> Unit,
    onTopicClick: (String) -> Unit,
) {
    when (universalis) {
        is UniversalisUiState.Success -> {

            userUniversalisResourceCardItems(
                items = universalis.data,
                onToggleBookmark = { /*onBookmarkChanged(it.id, !it.isSaved)*/ },
                itemModifier = Modifier.padding(24.dp),
            )
        }

        is UniversalisUiState.Loading -> item {
            NiaLoadingWheel(contentDesc = "Loading news") // TODO
        }

        else -> item {
            Text("Error") // TODO
        }
    }
}

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
    uiState: TopicRequest,
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
        val selected = true//uiState.isFollowed
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
