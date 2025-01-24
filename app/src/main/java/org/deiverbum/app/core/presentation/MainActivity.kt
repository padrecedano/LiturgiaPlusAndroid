@file:OptIn(ExperimentalLayoutApi::class)

package org.deiverbum.app.core.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.metrics.performance.JankStats
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.core.analytics.LocalAnalyticsHelper
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.util.NetworkMonitor
import org.deiverbum.app.core.data.util.TimeZoneMonitor
import org.deiverbum.app.core.designsystem.theme.LPlusTheme
import org.deiverbum.app.core.media.service.SimpleMediaService
import org.deiverbum.app.core.media.service.TtsMediaService
import org.deiverbum.app.core.model.data.DarkThemeConfig
import org.deiverbum.app.core.model.data.ThemeBrand
import org.deiverbum.app.core.ui.LocalTimeZone
import org.deiverbum.app.feature.tts.TtsMediaViewModel
import org.deiverbum.app.ui.NiaApp
import org.deiverbum.app.ui.rememberNiaAppState
import javax.inject.Inject
import kotlin.system.exitProcess

private const val TAG = "MainActivity"


@ExperimentalStdlibApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Lazily inject [JankStats], which is used to track jank throughout the app.
     */
    @Inject
    lateinit var lazyStats: dagger.Lazy<JankStats>

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var timeZoneMonitor: TimeZoneMonitor

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var universalisRepository: UniversalisRepository

    //val universalisViewModel: UniversalisViewModel  by viewModels()

    val viewModel: MainActivityViewModel by viewModels()
    private val viewModelTts: TtsMediaViewModel by viewModels()

    private var isServiceRunning = false


    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(
        ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class,
        ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        enableEdgeToEdge()

        setContent {
            //HomeScreenSong(viewModelSong, ::startService)
            //startService()
            //HomeScreenParent(viewModelPlayer)
            val navController = rememberNavController()
            /*
                        SimpleMediaScreen(
                            vm = viewModelSimpleMedia,
                            navController = navController,
                            startService = ::startService
                        )
            */
            //startServiceTts()
            /*TtsMediaScreen(
                vm = viewModelTts,
                navController = navController,
                startServiceTts = ::startServiceTts
            )*/
            val darkTheme = shouldUseDarkTheme(uiState)

            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { darkTheme },
                )
                onDispose {}
            }

            val appState = rememberNiaAppState(
                networkMonitor = networkMonitor,
                //userNewsResourceRepository = userNewsResourceRepository,
                timeZoneMonitor = timeZoneMonitor,
            )

            val currentTimeZone by appState.currentTimeZone.collectAsStateWithLifecycle()

            CompositionLocalProvider(
                LocalAnalyticsHelper provides analyticsHelper,
                LocalTimeZone provides currentTimeZone,
            ) {
                LPlusTheme(
                    darkTheme = darkTheme,
                    androidTheme = shouldUseAndroidTheme(uiState),
                    disableDynamicTheming = shouldDisableDynamicTheming(uiState),
                ) {
                    NiaApp(appState = appState, startServiceTts = ::startServiceTts)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lazyStats.get().isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
        lazyStats.get().isTrackingEnabled = false
    }

    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(this, SimpleMediaService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            }
            isServiceRunning = true
        }
    }


    @androidx.annotation.OptIn(UnstableApi::class)
    private fun stopService() {
        if (isServiceRunning) {
            val intent = Intent(this, SimpleMediaService::class.java)
            stopService(intent)
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(1)
        }
    }


    @androidx.annotation.OptIn(UnstableApi::class)
    private fun startServiceTts() {
        if (!isServiceRunning) {
            val intent = Intent(this, TtsMediaService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            }
            isServiceRunning = true
        }
    }

    @androidx.annotation.OptIn(UnstableApi::class)
    private fun stopServiceTts() {
        if (isServiceRunning) {
            val intent = Intent(this, TtsMediaService::class.java)
            stopService(intent)
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(1)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //stopService(Intent(this, SimpleMediaService::class.java))
        isServiceRunning = false
//TODO: Compare this
        stopServiceTts()
    }


}

/**
 * Returns `true` if the Android theme should be used, as a function of the [uiState].
 */
@Composable
private fun shouldUseAndroidTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> false
    is MainActivityUiState.Success -> when (uiState.userData.themeBrand) {
        ThemeBrand.DEFAULT -> false
        ThemeBrand.ANDROID -> true
    }
}

/**
 * Returns `true` if the dynamic color is disabled, as a function of the [uiState].
 */
@Composable
private fun shouldDisableDynamicTheming(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> false
    is MainActivityUiState.Success -> !uiState.userData.dynamic.useDynamicColor
}

/**
 * Returns `true` if dark theme should be used, as a function of the [uiState] and the
 * current system context.
 */
@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> when (uiState.userData.dynamic.darkThemeConfig) {
        DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        DarkThemeConfig.LIGHT -> false
        DarkThemeConfig.DARK -> true
    }
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)