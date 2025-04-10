package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.model.configuration.DarkThemeConfig
import org.deiverbum.app.core.model.configuration.FontSizeConfig
import org.deiverbum.app.core.model.configuration.RosariumConfig
import org.deiverbum.app.core.model.configuration.ThemeBrand
import org.deiverbum.app.core.model.configuration.UserData
import org.deiverbum.app.core.model.configuration.VoiceReaderConfig

interface UserDataRepository {

    /**
     * Stream of [UserData]
     */
    val userData: Flow<UserData>

    /**
     * Sets the desired theme brand.
     */
    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    /**
     * Sets the desired dark theme config.
     */
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    /**
     * Sets the preferred dynamic color config.
     */
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setVoiceReaderPreference(useVoiceReader: VoiceReaderConfig)

    suspend fun setMultipleInvitatoryPreference(useMultipleInvitatory: Boolean)
    suspend fun setRosariumPreference(
        rosariumConfig: RosariumConfig,
        useAnalytics: Boolean
    )

    suspend fun setFontSizePreference(fontSize: FontSizeConfig)

    /**
     * Sets whether the user has completed the onboarding process.
     */
    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean)
}
