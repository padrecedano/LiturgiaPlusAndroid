package org.deiverbum.app.feature.home


import NiaIcons
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.R
import org.deiverbum.app.core.analytics.LocalAnalyticsHelper
import org.deiverbum.app.core.designsystem.component.NiaFilterChip
import org.deiverbum.app.core.designsystem.component.NiaLoadingWheel
import org.deiverbum.app.core.designsystem.component.scrollbar.DraggableScrollbar
import org.deiverbum.app.core.designsystem.component.scrollbar.rememberDraggableScroller
import org.deiverbum.app.core.designsystem.component.scrollbar.scrollbarState
import org.deiverbum.app.core.model.data.TopicRequest
import org.deiverbum.app.core.ui.BookmarkButton
import org.deiverbum.app.core.ui.NewsResourceTitle
import org.deiverbum.app.core.ui.NotificationDot
import org.deiverbum.app.core.ui.UniversalisResourceMetaData
import org.deiverbum.app.core.ui.logNewsResourceOpened
import org.deiverbum.app.feature.universalis.UniversalisScreenView
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Utils

@ExperimentalMaterial3AdaptiveApi
@Composable
fun InterestsRouteInHome(
    showBackButton: Boolean,
    onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InterestsViewModelInHome = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    InterestsScreenInHome(
        uiState = uiState,
        modifier = modifier,
        followTopic = viewModel::followTopic,
        onBackClick = onBackClick,//listDetailNavigator::navigateBack,
        //onTopicClick=onTopicClick
        onTopicClick = {
            viewModel.onTopicClick(it)
            onTopicClick(it)
        },
    )
}


@Composable
internal fun InterestsScreenInHome(
    uiState: InterestsUiStateInHome,
    modifier: Modifier = Modifier,
    followTopic: (String, Boolean) -> Unit,
    onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,

    ) {
    val state = rememberLazyListState()

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
            when (uiState) {
                InterestsUiStateInHome.Loading -> item {
                    NiaLoadingWheel(
                        modifier = modifier,
                        contentDesc = stringResource(id = R.string.generic_loading),
                    )
                }

                InterestsUiStateInHome.Error -> TODO()
                is InterestsUiStateInHome.InterestsInHome -> {
                    item {
                        TopicToolbar(
                            showBackButton = true,
                            onBackClick = onBackClick,
                            onFollowClick = {},
                            uiState = uiState.topics,
                            modifier = modifier,
                        )
                    }
                    if (uiState.topics.isNotEmpty()) {

                        topicBody(
                            name = Utils.capitalize(LiturgyHelper.liturgyByType(uiState.selectedTopicId!!.toInt())),
                            description = uiState.topics[0].data[0].fecha,
                            news = uiState,
                            onTopicClick = onTopicClick,

                            )
                    } else {
                        //TODO
                        //InterestsEmptyScreen()
                    }


                }


                InterestsUiStateInHome.Empty -> TODO()
            }

            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
        val itemsAvailable = 1//topicItemsSize(topicUiState, newsUiState)
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


private fun LazyListScope.topicBody(
    name: String,
    description: String,
    news: InterestsUiStateInHome.InterestsInHome,

    onTopicClick: (String) -> Unit,

    ) {
    item {
        UniversalisHeaderNew(name, description)
    }
    //userUniversalisResourceCardsNew(news.topics, {},{})
    userNewsResourceCards(news)

    //userNewsResourceCardsNew(news, onBookmarkChanged, onNewsResourceViewed, onTopicClick)

}


@Composable
fun TopicToolbar(
    //showBackButton: Boolean, onBackClick: () -> Unit, onFollowClick: () -> Unit, uiState: List<FollowableTopic>, modifier: Modifier) {
    uiState: List<TopicRequest>,
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
                    contentDescription = stringResource(
                        id = R.string.core_ui_back,
                    ),
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




// TODO: Could/should this be replaced with [LazyGridScope.newsFeed]?
private fun LazyListScope.userNewsResourceCards(
    news: InterestsUiStateInHome,
    //onBookmarkChanged: (String, Boolean) -> Unit,
    //onNewsResourceViewed: (String) -> Unit,
    //onTopicClick: (String) -> Unit,
) {
    when (news) {
        is InterestsUiStateInHome.InterestsInHome -> {
            userNewsResourceCardItemsNew(
                items = news.topics,
                topicId = news.selectedTopicId,
                //userData=news.userData,
                /*onToggleBookmark = { onBookmarkChanged(it.id, !it.isSaved) },
                onNewsResourceViewed = onNewsResourceViewed,
                onTopicClick = onTopicClick,*/
                itemModifier = Modifier.padding(24.dp),
            )
        }

        is InterestsUiStateInHome.Loading -> item {
            NiaLoadingWheel(contentDesc = "Loading news") // TODO
        }

        else -> item {
            Text("Error") // TODO
        }
    }
}


fun LazyListScope.userNewsResourceCardItemsNew(
    items: List<TopicRequest>,
    /*onToggleBookmark: (item: UserNewsResource) -> Unit,
    onNewsResourceViewed: (String) -> Unit,
    onTopicClick: (String) -> Unit,*/
    itemModifier: Modifier = Modifier,
    topicId: String?,
) = items(
    items = items,
    key = { it.data[0].liturgyFK },
    itemContent = { userNewsResource ->
        val resourceUrl = "Uri.parse(userNewsResource.url)"
        val backgroundColor = MaterialTheme.colorScheme.background.toArgb()
        val context = LocalContext.current
        val analyticsHelper = LocalAnalyticsHelper.current

        NewsResourceCardExpandedNew(
            topicId = topicId,
            userNewsResource = items,
            isBookmarked = true,//userNewsResource.isSaved,
            hasBeenViewed = true,//userNewsResource.hasBeenViewed,
            onToggleBookmark = { /*onToggleBookmark(userNewsResource)*/ },
            onClick = {
                analyticsHelper.logNewsResourceOpened(
                    newsResourceId = userNewsResource.data[0].fecha,
                )
                //launchCustomChromeTab(context, resourceUrl, backgroundColor)
                //onNewsResourceViewed(userNewsResource.id)
            },
            onTopicClick = {},
            modifier = itemModifier,
        )
    },
)

@Composable
fun NewsResourceCardExpandedNew(
    userNewsResource: List<TopicRequest>,
    isBookmarked: Boolean,
    hasBeenViewed: Boolean,
    onToggleBookmark: () -> Unit,
    onClick: () -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    topicId: String?,
) {
    val clickActionLabel = "stringResource(R.string.core_ui_card_tap_action)"
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        // Use custom label for accessibility services to communicate button's action to user.
        // Pass null for action to only override the label and not the actual action.
        modifier = modifier.semantics {
            onClick(label = clickActionLabel, action = null)
        },
    ) {
        Column {
            /*if (!userNewsResource.headerImageUrl.isNullOrEmpty()) {
                Row {
                    //NewsResourceHeaderImage(userNewsResource.headerImageUrl)
                }
            }*/
            Box(
                modifier = Modifier.padding(16.dp),
            ) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        NewsResourceTitle(
                            userNewsResource[0].data[0].liturgia!!.tempus!!.externus.toString(),
                            modifier = Modifier.fillMaxWidth((.8f)),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        BookmarkButton(isBookmarked, onToggleBookmark)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (!hasBeenViewed) {
                            NotificationDot(
                                color = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(8.dp),
                            )
                            Spacer(modifier = Modifier.size(6.dp))
                        }
                        UniversalisResourceMetaData(userNewsResource[0].data[0].liturgia!!.nomen)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    var data = userNewsResource[0].data[0]

                    UniversalisScreenView(
                        data = data,
                        topicId = topicId,
                        userNewsResource[0].dynamic,
                        modifier = modifier
                    )

                    /*NewsResourceShortDescriptionNew(
                        UniversalisScreenView(flower = data)
                        //data.getAllForVieww(userNewsResource[0].dynamic)

                    )*/
                    //Spacer(modifier = Modifier.height(12.dp))
                    /*NewsResourceTopicsNew(
                        topics = userNewsResource,
                        onTopicClick = onTopicClick,
                    )*/
                }
            }
        }
    }
}

@Composable
private fun UniversalisHeaderNew(name: String, description: String) {
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

