package org.deiverbum.app.ui

import LPlusIcons
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import kotlinx.coroutines.launch
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.LPlusBackground
import org.deiverbum.app.core.designsystem.component.LPlusGradientBackground
import org.deiverbum.app.core.designsystem.component.LPlusNavigationRail
import org.deiverbum.app.core.designsystem.component.LPlusNavigationRailItem
import org.deiverbum.app.core.designsystem.component.LPlusTopAppBar
import org.deiverbum.app.core.designsystem.component.NiaNavigationBar
import org.deiverbum.app.core.designsystem.component.NiaNavigationBarItem
import org.deiverbum.app.core.designsystem.component.PlayButton
import org.deiverbum.app.core.designsystem.theme.GradientColors
import org.deiverbum.app.core.designsystem.theme.LocalGradientColors
import org.deiverbum.app.core.presentation.ui.LPlusAppState
import org.deiverbum.app.feature.settings.SettingsDialog
import org.deiverbum.app.feature.tts.TextToSpeechScreen
import org.deiverbum.app.feature.tts.TextToSpeechScreenn
import org.deiverbum.app.navigation.LPlusNavHost
import org.deiverbum.app.navigation.TopLevelDestination


@ExperimentalMaterial3AdaptiveApi
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class,
)

@Composable
fun LPlusApp(appState: LPlusAppState, onReaderClick: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val (fabOnClick, setFabOnClick) = remember { mutableStateOf<(() -> Unit)?>(null) }

    val shouldShowGradientBackground =
        appState.currentTopLevelDestination == TopLevelDestination.HOME
    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

    LPlusBackground {
        LPlusGradientBackground(
            gradientColors = if (shouldShowGradientBackground) {
                LocalGradientColors.current
            } else {
                GradientColors()
            },
        ) {
            val snackbarHostState = remember { SnackbarHostState() }

            val isOffline by appState.isOffline.collectAsStateWithLifecycle()

            // If user is not connected to the internet show a snack bar to inform them.
            val notConnectedMessage = stringResource(R.string.not_connected)
            LaunchedEffect(isOffline) {
                if (isOffline) {
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = SnackbarDuration.Indefinite,
                    )
                }
            }

            if (showSettingsDialog) {
                SettingsDialog(
                    onDismiss = { showSettingsDialog = false },
                )
            }

            Scaffold(
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                },
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = { SnackbarHost(snackbarHostState) },
                floatingActionButton =
                {
                    SmallFloatingActionButton(
                        onClick = onReaderClick
                    )
                    { Icon(Icons.Filled.PlayArrow, contentDescription = "") }

                },
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        LPlusBottomBar(
                            destinations = appState.topLevelDestinations,
                            //destinationsWithUnreadResources = unreadDestinations,
                            onNavigateToDestination = appState::navigateToTopLevelDestination,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier.testTag("LPlusBottomBar"),
                        )
                    }
                },

                ) { padding ->

                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        sheetState = sheetState
                    ) {
                        // Sheet content
                        //SliderReader()
                        TextToSpeechScreen()
                        TextToSpeechScreenn(text = StringBuilder("Lorem ipsum."))
                        PlayButton(isBookmarked = true, onClick = { /*TODO*/ })
                        Button(onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }) {
                            //Text("Hide bottom sheet")
                        }
                    }
                }

                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    if (appState.shouldShowNavRail) {
                        LPlusNavRail(
                            destinations = appState.topLevelDestinations,
                            //destinationsWithUnreadResources = unreadDestinations,
                            onNavigateToDestination = appState::navigateToTopLevelDestination,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier
                                .testTag("LPlusNavRail")
                                .safeDrawingPadding(),
                        )
                    }

                    Column(Modifier.fillMaxSize()) {
                        // Show the top app bar on top level destinations.
                        val destination = appState.currentTopLevelDestination
                        if (destination != null) {
                            LPlusTopAppBar(
                                onReaderClick = onReaderClick,
                                titleRes = destination.titleTextId,
                                navigationIcon = LPlusIcons.Search,
                                navigationIconContentDescription = stringResource(
                                    id = R.string.feature_settings_top_app_bar_navigation_icon_description,
                                ),
                                actionIcon = LPlusIcons.Settings,
                                actionIconContentDescription = stringResource(
                                    id = R.string.feature_settings_top_app_bar_action_icon_description,
                                ),
                                readerIcon = LPlusIcons.Reader,
                                calendarIcon = LPlusIcons.Calendar,

                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = Color.Transparent,
                                ),
                                onActionClick = {
                                    showSettingsDialog = true
                                },
                                //onReaderClick = onReaderClick,
                                /*onReaderClick = {
                                    //showBottomSheet = true
                                                appState.shouldRead
                                                },*/

                                onNavigationClick = { /*appState.navigateToSearch()*/ },

                                )
                        }

                        LPlusNavHost(
                            appState = appState,
                            onReaderClick = onReaderClick,
                            onShowSnackbar = { message, action ->
                                snackbarHostState.showSnackbar(
                                    message = message,
                                    actionLabel = action,
                                    duration = SnackbarDuration.Short,
                                ) == SnackbarResult.ActionPerformed
                            },
                        )
                    }

                    // TODO: We may want to add padding or spacer when the snackbar is shown so that
                    //  content doesn't display behind it.
                }
            }
        }
    }
}

@Composable
private fun LPlusNavRail(
    destinations: List<TopLevelDestination>,
    //destinationsWithUnreadResources: Set<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    LPlusNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            //val hasUnread = destinationsWithUnreadResources.contains(destination)
            LPlusNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = /*if (hasUnread) Modifier.notificationDot() else*/ Modifier,
            )
        }
    }
}

@Composable
private fun LPlusBottomBar(
    destinations: List<TopLevelDestination>,
    //destinationsWithUnreadResources: Set<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NiaNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            //val hasUnread = destinationsWithUnreadResources.contains(destination)
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NiaNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = /*if (hasUnread) Modifier.notificationDot() else*/ Modifier,
            )
        }
    }
}

private fun Modifier.notificationDot(): Modifier =
    composed {
        val tertiaryColor = MaterialTheme.colorScheme.tertiary
        drawWithContent {
            drawContent()
            drawCircle(
                tertiaryColor,
                radius = 5.dp.toPx(),
                // This is based on the dimensions of the NavigationBar's "indicator pill";
                // however, its parameters are private, so we must depend on them implicitly
                // (NavigationBarTokens.ActiveIndicatorWidth = 64.dp)
                center = center + Offset(
                    64.dp.toPx() * .45f,
                    32.dp.toPx() * -.45f - 6.dp.toPx(),
                ),
            )
        }
    }

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
