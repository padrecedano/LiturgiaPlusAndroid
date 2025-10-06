@file:OptIn(ExperimentalLayoutApi::class)

package org.deiverbum.app

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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.trace
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.metrics.performance.JankStats
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.core.analytics.LocalAnalyticsHelper
import org.deiverbum.app.core.data.repository.UniversalisRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.data.util.NetworkMonitor
import org.deiverbum.app.core.data.util.TimeZoneMonitor
import org.deiverbum.app.core.designsystem.theme.LPlusTheme
import org.deiverbum.app.core.media.service.SimpleMediaService
import org.deiverbum.app.core.media.service.TtsMediaService
import org.deiverbum.app.core.ui.LocalTimeZone
import org.deiverbum.app.feature.tts.TtsMediaViewModel
import org.deiverbum.app.ui.LPlusApp
import org.deiverbum.app.ui.rememberNiaAppState
import org.deiverbum.app.util.isSystemInDarkTheme
import javax.inject.Inject
import kotlin.system.exitProcess

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

    @Inject
    lateinit var userDataRepository: UserDataRepository

    //val universalisViewModel: UniversalisViewModel  by viewModels()

    val viewModel: MainActivityViewModel by viewModels()
    private val viewModelTts: TtsMediaViewModel by viewModels()

    private var isServiceRunning = false

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(
        ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class,
        ExperimentalLayoutApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var themeSettings by mutableStateOf(
            ThemeSettings(
                darkTheme = resources.configuration.isSystemInDarkTheme,
                androidTheme = MainActivityUiState.Loading.shouldUseAndroidTheme,
                disableDynamicTheming = MainActivityUiState.Loading.shouldDisableDynamicTheming,
            ),
        )

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    isSystemInDarkTheme(),
                    viewModel.uiState,
                ) { systemDark, uiState ->
                    ThemeSettings(
                        darkTheme = uiState.shouldUseDarkTheme(systemDark),
                        androidTheme = uiState.shouldUseAndroidTheme,
                        disableDynamicTheming = uiState.shouldDisableDynamicTheming,
                    )
                }
                    .onEach { themeSettings = it }
                    .map { it.darkTheme }
                    .distinctUntilChanged()
                    .collect { darkTheme ->
                        trace("niaEdgeToEdge") {
                            // Turn off the decor fitting system windows, which allows us to handle insets,
                            // including IME animations, and go edge-to-edge.
                            // This is the same parameters as the default enableEdgeToEdge call, but we manually
                            // resolve whether or not to show dark theme using uiState, since it can be different
                            // than the configuration's dark theme value based on the user preference.
                            enableEdgeToEdge(
                                statusBarStyle = SystemBarStyle.auto(
                                    lightScrim = android.graphics.Color.TRANSPARENT,
                                    darkScrim = android.graphics.Color.TRANSPARENT,
                                ) { darkTheme },
                                navigationBarStyle = SystemBarStyle.auto(
                                    lightScrim = lightScrim,
                                    darkScrim = darkScrim,
                                ) { darkTheme },
                            )
                        }
                    }
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value.shouldKeepSplashScreen() }

        setContent {
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
                    darkTheme = themeSettings.darkTheme,
                    androidTheme = themeSettings.androidTheme,
                    disableDynamicTheming = themeSettings.disableDynamicTheming,
                ) {
                    LPlusApp(appState, startServiceTts = ::startServiceTts)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //val s=timeZoneMonitor.currentTimeZone.conflate()
        lazyStats.get().isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
        lazyStats.get().isTrackingEnabled = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(this, SimpleMediaService::class.java)
            startForegroundService(intent)
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

    @RequiresApi(Build.VERSION_CODES.O)
    @androidx.annotation.OptIn(UnstableApi::class)
    private fun startServiceTts() {
        if (!isServiceRunning) {
            val intent = Intent(this, TtsMediaService::class.java)
            //TODO("What about API < 26?")
            startForegroundService(intent)
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
        //stopService()
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

/**
 * Class for the system theme settings.
 * This wrapping class allows us to combine all the changes and prevent unnecessary recompositions.
 */
data class ThemeSettings(
    val darkTheme: Boolean,
    val androidTheme: Boolean,
    val disableDynamicTheming: Boolean,
)