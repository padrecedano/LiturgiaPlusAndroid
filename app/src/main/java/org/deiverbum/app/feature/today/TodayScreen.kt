package org.deiverbum.app.feature.today


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.LPlusBackground
import org.deiverbum.app.core.designsystem.component.NiaLoadingWheel
import org.deiverbum.app.core.designsystem.theme.LPlusTheme
import org.deiverbum.app.core.ui.DevicePreviews
import org.deiverbum.app.core.ui.TrackScreenViewEvent

@Composable
fun TodayRouteNew(
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedTopic: Boolean = false,
    viewModel: TodayViewModel = hiltViewModel(),

    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TodayScreenNew(
        uiState = uiState,
        //followTopic = {},//viewModel::followTopic,
        onTopicClick = {
            //viewModel.onTopicClick(it)
            onTopicClick(it)
        },
        highlightSelectedTopic = highlightSelectedTopic,
        modifier = modifier,
    )
}


@Composable
fun TodayRoute(
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedTopic: Boolean = false,
    viewModel: TodayViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TodayScreen(
        uiState = uiState,
        //followTopic = {},//viewModel::followTopic,
        onTopicClick = {
            //viewModel.onTopicClick(it)
            onTopicClick(it)
        },
        highlightSelectedTopic = highlightSelectedTopic,
        modifier = modifier,
    )
}


@Composable
internal fun TodayScreenNew(
    uiState: TodayUiState,
    //followTopic: (String, Boolean) -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedTopic: Boolean = false,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (uiState) {
            TodayUiState.Loading ->
                NiaLoadingWheel(
                    modifier = modifier,
                    contentDesc = stringResource(id = R.string.feature_interests_loading),
                )

            is TodayUiState.Todays ->
                TopicsTabContent(
                    topics = uiState.topics,
                    onTopicClick = onTopicClick,
                    //onFollowButtonClick = followTopic,
                    selectedTopicId = uiState.selectedTopicId,
                    highlightSelectedTopic = highlightSelectedTopic,
                    modifier = modifier,
                )

            is TodayUiState.Empty -> TodayEmptyScreen()
        }
    }
    TrackScreenViewEvent(screenName = "Today")
}
@Composable
internal fun TodayScreen(
    uiState: TodayUiState,
    //followTopic: (String, Boolean) -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    highlightSelectedTopic: Boolean = false,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (uiState) {
            TodayUiState.Loading ->
                NiaLoadingWheel(
                    modifier = modifier,
                    contentDesc = stringResource(id = R.string.feature_interests_loading),
                )

            is TodayUiState.Todays ->
                TopicsTabContent(
                    topics = uiState.topics,
                    onTopicClick = onTopicClick,
                    //onFollowButtonClick = followTopic,
                    selectedTopicId = uiState.selectedTopicId,
                    highlightSelectedTopic = highlightSelectedTopic,
                    modifier = modifier,
                )

            is TodayUiState.Empty -> TodayEmptyScreen()
        }
    }
    TrackScreenViewEvent(screenName = "Today")
}

@Composable
private fun TodayEmptyScreen() {
    Text(text = stringResource(id = R.string.feature_interests_empty_header))
}


@DevicePreviews
@Composable
fun TodayScreenLoading() {
    LPlusTheme {
        LPlusBackground {
            TodayScreen(
                uiState = TodayUiState.Loading,
                //followTopic = { _, _ -> },
                onTopicClick = {},
            )
        }
    }
}

@DevicePreviews
@Composable
fun TodayScreenEmpty() {
    LPlusTheme {
        LPlusBackground {
            TodayScreen(
                uiState = TodayUiState.Empty,
                //followTopic = { _, _ -> },
                onTopicClick = {},
            )
        }
    }
}
