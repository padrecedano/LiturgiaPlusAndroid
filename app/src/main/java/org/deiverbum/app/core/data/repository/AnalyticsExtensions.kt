package org.deiverbum.app.core.data.repository

import org.deiverbum.app.core.analytics.AnalyticsEvent
import org.deiverbum.app.core.analytics.AnalyticsHelper

fun AnalyticsHelper.logUniversalisResourceOpened(universalisResourceId: String) {
    logEvent(
        event = AnalyticsEvent(
            type = "universalis_resource_opened",
            extras = listOf(
                AnalyticsEvent.Param("opened_universalis_resource", universalisResourceId),
            ),
        ),
    )
}

fun AnalyticsHelper.logUniversalisTtsEvent(universalisResourceId: String) {
    logEvent(
        event = AnalyticsEvent(
            type = "universalis_tts_event",
            extras = listOf(
                AnalyticsEvent.Param("opened_universalis_tts", universalisResourceId),
            ),
        ),
    )
}

fun AnalyticsHelper.logMainMenuOptionOpened(menuOption: String) {
    logEvent(
        event = AnalyticsEvent(
            type = "main_menu_option_opened",
            extras = listOf(
                AnalyticsEvent.Param("opened_main_menu_option", menuOption),
            ),
        ),
    )
}

internal fun AnalyticsHelper.logThemeChanged(themeName: String) =
    logEvent(
        AnalyticsEvent(
            type = "theme_changed",
            extras = listOf(
                AnalyticsEvent.Param(key = "theme_name", value = themeName),
            ),
        ),
    )

internal fun AnalyticsHelper.logUseVoiceReaderChanged(themeName: String) =
    logEvent(
        AnalyticsEvent(
            type = "voice_reader_changed",
            extras = listOf(
                AnalyticsEvent.Param(key = "status", value = themeName),
            ),
        ),
    )

internal fun AnalyticsHelper.logFontSizeChanged(themeName: String) =
    logEvent(
        AnalyticsEvent(
            type = "font_size_changed",
            extras = listOf(
                AnalyticsEvent.Param(key = "font_size_preference", value = themeName),
            ),
        ),
    )

internal fun AnalyticsHelper.logDarkThemeConfigChanged(darkThemeConfigName: String) =
    logEvent(
        AnalyticsEvent(
            type = "dark_theme_config_changed",
            extras = listOf(
                AnalyticsEvent.Param(key = "dark_theme_config", value = darkThemeConfigName),
            ),
        ),
    )

internal fun AnalyticsHelper.logDynamicColorPreferenceChanged(useDynamicColor: Boolean) =
    logEvent(
        AnalyticsEvent(
            type = "dynamic_color_preference_changed",
            extras = listOf(
                AnalyticsEvent.Param(
                    key = "dynamic_color_preference",
                    value = useDynamicColor.toString()
                ),
            ),
        ),
    )

internal fun AnalyticsHelper.logOnboardingStateChanged(shouldHideOnboarding: Boolean) {
    val eventType = if (shouldHideOnboarding) "onboarding_complete" else "onboarding_reset"
    logEvent(
        AnalyticsEvent(type = eventType),
    )

}

internal fun AnalyticsHelper.logRosariumConfigChanged(rosariumConfigName: String) =
    logEvent(
        AnalyticsEvent(
            type = "rosarium_mysterium_config_changed",
            extras = listOf(
                AnalyticsEvent.Param(key = "rosarium_mysterium_config", value = rosariumConfigName),
            ),
        ),
    )

