package org.deiverbum.app.feature.today

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import org.deiverbum.app.core.designsystem.component.scrollbar.DraggableScrollbar
import org.deiverbum.app.core.designsystem.component.scrollbar.rememberDraggableScroller
import org.deiverbum.app.core.designsystem.component.scrollbar.scrollbarState
import org.deiverbum.app.core.model.data.FollowableUITopic
import org.deiverbum.app.core.ui.AssistChipExample
import org.deiverbum.app.core.ui.TodayItem

@Composable
fun TopicsTabContent(
    topics: List<FollowableUITopic>,
    onTopicClick: (String) -> Unit,
    //onFollowButtonClick: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    withBottomSpacer: Boolean = true,
    selectedTopicId: String? = null,
    highlightSelectedTopic: Boolean = false,
) {
    val nestedNavController = rememberNavController()

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
            topics.forEach { followableTopic ->
                val topicId = followableTopic.topic.id
                item(key = topicId) {
                    val isSelected = highlightSelectedTopic && topicId.toString() == selectedTopicId
                    AssistChipExample(onTopicClick = topicId.toString())
                    //AssistChipExample()

                    TodayItem(
                        name = followableTopic.topic.name,
                        following = followableTopic.isFollowed,
                        description = followableTopic.topic.description,
                        topicImageUrl = "followableTopic.topic.imageUrl",
                        onClick = { onTopicClick(topicId.toString()) },
                        onFollowButtonClick = {
                            //Botón +  en Today
                            /*onFollowButtonClick(topicId.toString(), it) */
                        },
                        isSelected = isSelected,
                    )
                }
            }

            if (withBottomSpacer) {
                item {
                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                }
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
