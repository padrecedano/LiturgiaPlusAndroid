package org.deiverbum.app.feature.home

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import org.deiverbum.app.core.designsystem.component.scrollbar.scrollbarState
import org.deiverbum.app.core.ui.TrackScreenViewEvent
import org.deiverbum.app.core.ui.TrackScrollJank
import org.deiverbum.app.feature.interests.InterestsRoute
import org.deiverbum.app.feature.interests.navigation.INTERESTS_ROUTE
import org.deiverbum.app.feature.interests.navigation.TOPIC_ID_ARG
import org.deiverbum.app.feature.topic.TopicDetailPlaceholder
import org.deiverbum.app.feature.topic.navigation.TOPIC_ROUTE
import org.deiverbum.app.feature.topic.navigation.navigateToTopic
import org.deiverbum.app.feature.topic.navigation.topicScreen
import org.deiverbum.app.ui.home.HomeScreenBody

private const val DETAIL_PANE_NAVHOST_ROUTE = "detail_pane_route"

@Composable
internal fun HomeRouteOld(
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val onboardingUiState by viewModel.onboardingUiState.collectAsStateWithLifecycle()
    //val feedState by viewModel.feedState.collectAsStateWithLifecycle()
    val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()
    val deepLinkedUserNewsResource by viewModel.deepLinkedNewsResource.collectAsStateWithLifecycle()
//viewModel.updateTopicSelection("1",true)
    val selectedTopicId by viewModel.selectedTopicId.collectAsStateWithLifecycle()
    HomeScreenBody(onNextButtonClicked = {
        viewModel.onTopicClick(it)
        onTopicClick(it)
    })

    InterestsListDetailScreenNew(
        selectedTopicId = selectedTopicId,
        onTopicClick = viewModel::onTopicClick,
    )

    HomeScreen(
        isSyncing = isSyncing,
        onboardingUiState = onboardingUiState,
        //feedState = feedState,
        deepLinkedUserNewsResource = deepLinkedUserNewsResource,
        onTopicCheckedChanged = viewModel::updateTopicSelection,
        onDeepLinkOpened = viewModel::onDeepLinkOpened,
        onTopicClick = onTopicClick,
        saveFollowedTopics = viewModel::dismissOnboarding,
        onNewsResourcesCheckedChanged = viewModel::updateNewsResourceSaved,
        onNewsResourceViewed = { viewModel.setNewsResourceViewed(it, true) },
        modifier = modifier,
    )
}


@Composable
internal fun HomeScreen(
    isSyncing: Boolean,
    onboardingUiState: OnboardingUiState,
    //feedState: UniversalisFeedUiState,
    deepLinkedUserNewsResource: String?,
    onTopicCheckedChanged: (String, Boolean) -> Unit,
    onTopicClick: (String) -> Unit,
    onDeepLinkOpened: (String) -> Unit,
    saveFollowedTopics: () -> Unit,
    onNewsResourcesCheckedChanged: (String, Boolean) -> Unit,
    onNewsResourceViewed: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isOnboardingLoading = onboardingUiState is OnboardingUiState.Loading
    //val isFeedLoading = feedState is UniversalisFeedUiState.Loading

    //HomeScreenBody(onNextButtonClicked = ::onTopicClickShowDetailPane)
    //HomeScreenBody(onNextButtonClicked = ::onTopicClickShowDetailPane)

    // This code should be called when the UI is ready for use and relates to Time To Full Display.
    ReportDrawnWhen { !isSyncing && !isOnboardingLoading /*&& !isFeedLoading*/ }

    val itemsAvailable = 0// feedItemsSize(feedState, onboardingUiState)

    val state = rememberLazyStaggeredGridState()
    val scrollbarState = state.scrollbarState(
        itemsAvailable = itemsAvailable,
    )
    TrackScrollJank(scrollableState = state, stateName = "home:feed")

    TrackScreenViewEvent(screenName = "Home")
    NotificationPermissionEffect()
    DeepLinkEffect(
        deepLinkedUserNewsResource,
        onDeepLinkOpened,
    )
}


@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun NotificationPermissionEffect() {
    // Permission requests should only be made from an Activity Context, which is not present
    // in previews
    if (LocalInspectionMode.current) return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    val notificationsPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS,
    )
    LaunchedEffect(notificationsPermissionState) {
        val status = notificationsPermissionState.status
        if (status is PermissionStatus.Denied && !status.shouldShowRationale) {
            notificationsPermissionState.launchPermissionRequest()
        }
    }
}

@Composable
private fun DeepLinkEffect(
    userNewsResource: String?,
    onDeepLinkOpened: (String) -> Unit,
) {
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.background.toArgb()

    LaunchedEffect(userNewsResource) {
        if (userNewsResource == null) return@LaunchedEffect
        //if (!userNewsResource.hasBeenViewed) onDeepLinkOpened(userNewsResource.id)

        /*launchCustomChromeTab(
            context = context,
            uri = Uri.parse(userNewsResource.url),
            toolbarColor = backgroundColor,
        )*/
    }
}


//From interests


fun NavGraphBuilder.interestsListDetailScreen() {
    composable(
        route = INTERESTS_ROUTE,
        arguments = listOf(
            navArgument(TOPIC_ID_ARG) {
                type = NavType.StringType
                defaultValue = null
                nullable = true
            },
        ),
    ) {
        InterestsListDetailScreen()
    }
}


@Composable
internal fun InterestsListDetailScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val selectedTopicId by viewModel.selectedTopicId.collectAsStateWithLifecycle()
    InterestsListDetailScreen(
        selectedTopicId = selectedTopicId,
        onTopicClick = viewModel::onTopicClick,
    )
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun InterestsListDetailScreen(
    selectedTopicId: String?,
    onTopicClick: (String) -> Unit,
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<Nothing>()
    BackHandler(listDetailNavigator.canNavigateBack()) {
        listDetailNavigator.navigateBack()
    }

    val nestedNavController = rememberNavController()

    fun onTopicClickShowDetailPane(topicId: String) {
        onTopicClick(topicId)
        nestedNavController.navigateToTopic(topicId) {
            popUpTo(DETAIL_PANE_NAVHOST_ROUTE)
        }
        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
    }

    ListDetailPaneScaffold(
        value = listDetailNavigator.scaffoldValue,
        directive = listDetailNavigator.scaffoldDirective,
        listPane = {
            InterestsRoute(
                onTopicClick = ::onTopicClickShowDetailPane,
                highlightSelectedTopic = listDetailNavigator.isDetailPaneVisible(),
            )
        },
        detailPane = {
            NavHost(
                navController = nestedNavController,
                startDestination = TOPIC_ROUTE,
                route = DETAIL_PANE_NAVHOST_ROUTE,
            ) {
                topicScreen(
                    showBackButton = !listDetailNavigator.isListPaneVisible(),
                    onBackClick = listDetailNavigator::navigateBack,
                    onTopicClick = ::onTopicClickShowDetailPane,
                )
                composable(route = TOPIC_ROUTE) {
                    TopicDetailPlaceholder()
                }
            }
        },
    )
    LaunchedEffect(Unit) {
        if (selectedTopicId != null) {
            // Initial topic ID was provided when navigating to Interests, so show its details.
            onTopicClickShowDetailPane(selectedTopicId)
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isListPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun <T> ThreePaneScaffoldNavigator<T>.isDetailPaneVisible(): Boolean =
    scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun InterestsListDetailScreenNew(
    selectedTopicId: String?,
    onTopicClick: (String) -> Unit,
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<Nothing>()
    BackHandler(listDetailNavigator.canNavigateBack()) {
        listDetailNavigator.navigateBack()
    }

    val nestedNavController = rememberNavController()

    fun onTopicClickShowDetailPane(topicId: String) {
        onTopicClick(topicId)
        nestedNavController.navigateToTopic(topicId) {
            popUpTo(DETAIL_PANE_NAVHOST_ROUTE)
        }
        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
    }

    ListDetailPaneScaffold(
        value = listDetailNavigator.scaffoldValue,
        directive = listDetailNavigator.scaffoldDirective,
        listPane = {
            InterestsRoute(
                onTopicClick = ::onTopicClickShowDetailPane,
                highlightSelectedTopic = listDetailNavigator.isDetailPaneVisible(),
            )
        },
        detailPane = {
            NavHost(
                navController = nestedNavController,
                startDestination = TOPIC_ROUTE,
                route = DETAIL_PANE_NAVHOST_ROUTE,
            ) {
                topicScreen(
                    showBackButton = !listDetailNavigator.isListPaneVisible(),
                    onBackClick = listDetailNavigator::navigateBack,
                    onTopicClick = ::onTopicClickShowDetailPane,
                )
                composable(route = TOPIC_ROUTE) {
                    TopicDetailPlaceholder()
                }
            }
        },
    )
    LaunchedEffect(Unit) {
        if (selectedTopicId != null) {
            // Initial topic ID was provided when navigating to Interests, so show its details.
            onTopicClickShowDetailPane(selectedTopicId)
        }
    }
}
