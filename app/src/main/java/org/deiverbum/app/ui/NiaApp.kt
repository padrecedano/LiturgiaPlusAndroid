package org.deiverbum.app.ui

import NiaNavigationSuiteScaffold
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.deiverbum.app.R
import org.deiverbum.app.core.designsystem.component.LPlusBackground
import org.deiverbum.app.core.designsystem.component.LPlusGradientBackground
import org.deiverbum.app.core.designsystem.component.NiaTopAppBar
import org.deiverbum.app.core.designsystem.icon.NiaIcons
import org.deiverbum.app.core.designsystem.theme.GradientColors
import org.deiverbum.app.core.designsystem.theme.LocalGradientColors
import org.deiverbum.app.feature.menu.MainMenuDialog
import org.deiverbum.app.feature.menu.navigation.navigateToMenu
import org.deiverbum.app.feature.settings.SettingsDialog
import org.deiverbum.app.navigation.NiaNavHost
import org.deiverbum.app.navigation.TopLevelDestination
import kotlin.reflect.KClass

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalLayoutApi
@Composable
fun NiaApp(
    appState: NiaAppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    startServiceTts: () -> Unit,
) {
    val shouldShowGradientBackground =
        appState.currentTopLevelDestination == TopLevelDestination.HOME
    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }
    var showMainMenu by rememberSaveable { mutableStateOf(false) }

    LPlusBackground(modifier = modifier) {
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
                startServiceTts()
                if (isOffline) {
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = Indefinite,
                    )
                }
            }

            NiaApp(
                appState = appState,
                snackbarHostState = snackbarHostState,
                showSettingsDialog = showSettingsDialog,
                showMainMenu = showMainMenu,

                onSettingsDismissed = { showSettingsDialog = false },
                onMainMenuDismissed = { showMainMenu = false },
                onTopAppBarActionClick = { showSettingsDialog = true },
                onMainMenuActionClick = { showMainMenu = true },
                windowAdaptiveInfo = windowAdaptiveInfo,
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalLayoutApi
@Composable
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class,
    ExperimentalMaterial3AdaptiveApi::class,
)
internal fun NiaApp(
    appState: NiaAppState,
    snackbarHostState: SnackbarHostState,
    showSettingsDialog: Boolean,
    onSettingsDismissed: () -> Unit,
    showMainMenu: Boolean,
    onMainMenuDismissed: () -> Unit,
    onTopAppBarActionClick: () -> Unit,
    onMainMenuActionClick: () -> Unit,

    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    /*val unreadDestinations by appState.topLevelDestinationsWithUnreadResources
        .collectAsStateWithLifecycle()*/
    val currentDestination = appState.currentDestination

    if (showSettingsDialog) {
        SettingsDialog(
            onDismiss = { onSettingsDismissed() },
        )
    }

    if (showMainMenu) {
        MainMenuDialog(
            onDismiss = {
                onMainMenuDismissed()
            },
            onClick = appState.navController::navigateToMenu
        )
        /*SettingsDialog(
            onDismiss = { onSettingsDismissed() },
        )*/
        /*ModalNavigationDrawerSample(
            //onDismiss = { onMainMenuDismissed() },
        )*/
    }

    NiaNavigationSuiteScaffold(
        navigationSuiteItems = {
            appState.topLevelDestinations.forEach { destination ->
                val hasUnread = false//unreadDestinations.contains(destination)
                val selected = currentDestination
                    .isRouteInHierarchy(destination.route)
                item(
                    selected = selected,
                    onClick = { appState.navigateToTopLevelDestination(destination) },
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
                    modifier =
                    Modifier
                        .testTag("NiaNavItem")
                        .then(if (hasUnread) Modifier.notificationDot() else Modifier),
                )
            }
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
    ) {
        Scaffold(
            modifier = modifier.semantics {
                testTagsAsResourceId = true
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { padding ->
            Column(
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
                // Show the top app bar on top level destinations.
                val destination = appState.currentTopLevelDestination
                var shouldShowTopAppBar = false

                if (destination != null) {
                    shouldShowTopAppBar = true
                    NiaTopAppBar(
                        titleRes = destination.titleTextId,
                        navigationIcon = NiaIcons.Menu,
                        navigationIconContentDescription = stringResource(
                            id = R.string.feature_settings_top_app_bar_navigation_icon_description,
                        ),
                        actionIcon = NiaIcons.Settings,
                        actionIconContentDescription = stringResource(
                            id = R.string.feature_settings_top_app_bar_action_icon_description,
                        ),
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent,
                        ),
                        onActionClick = { onTopAppBarActionClick() },
                        onNavigationClick = {
                            onMainMenuActionClick()
                            /*appState.navigateToSearch()*/
                        },
                    )
                }

                Box(
                    // Workaround for https://issuetracker.google.com/338478720
                    modifier = Modifier.consumeWindowInsets(
                        if (shouldShowTopAppBar) {
                            WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                        } else {
                            WindowInsets(0, 0, 0, 0)
                        },
                    ),
                ) {
                    NiaNavHost(
                        appState = appState,
                        onShowSnackbar = { message, action ->
                            snackbarHostState.showSnackbar(
                                message = message,
                                actionLabel = action,
                                duration = Short,
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

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false