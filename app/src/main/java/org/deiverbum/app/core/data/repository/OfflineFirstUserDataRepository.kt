package org.deiverbum.app.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.core.datastore.PreferencesDataSource
import org.deiverbum.app.core.model.configuration.DarkThemeConfig
import org.deiverbum.app.core.model.configuration.FontSizeConfig
import org.deiverbum.app.core.model.configuration.RosariumConfig
import org.deiverbum.app.core.model.configuration.ThemeBrand
import org.deiverbum.app.core.model.configuration.UserData
import org.deiverbum.app.core.model.configuration.VoiceReaderConfig
import javax.inject.Inject

class OfflineFirstUserDataRepository @Inject constructor(
    private val niaPreferencesDataSource: PreferencesDataSource,
    private val analyticsHelper: AnalyticsHelper,
) : UserDataRepository {

    override val userData: Flow<UserData> =
        niaPreferencesDataSource.userData

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        niaPreferencesDataSource.setThemeBrand(themeBrand)
        analyticsHelper.logThemeChanged(themeBrand.name)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        niaPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)
        analyticsHelper.logDarkThemeConfigChanged(darkThemeConfig.name)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        niaPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
        analyticsHelper.logDynamicColorPreferenceChanged(useDynamicColor)
    }

    override suspend fun setVoiceReaderPreference(useVoiceReader: VoiceReaderConfig) {
        niaPreferencesDataSource.setVoiceReaderPreference(useVoiceReader)
        analyticsHelper.logUseVoiceReaderChanged(useVoiceReader.name)
    }

    override suspend fun setFontSizePreference(fontSize: FontSizeConfig) {
        niaPreferencesDataSource.setFontSizePreference(fontSize)
        analyticsHelper.logFontSizeChanged(fontSize.name)
    }

    override suspend fun setMultipleInvitatoryPreference(useMultipleInvitatory: Boolean) {
        niaPreferencesDataSource.setMultipleInvitatoryPreference(useMultipleInvitatory)
        analyticsHelper.logDynamicColorPreferenceChanged(useMultipleInvitatory)
    }

    override suspend fun setRosariumPreference(
        rosariumConfig: RosariumConfig,
        useAnalytics: Boolean
    ) {
        niaPreferencesDataSource.setRosariumPreference(rosariumConfig)
        if (useAnalytics) {
            analyticsHelper.logRosariumConfigChanged(rosariumConfig.name)
        }
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        niaPreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)
        analyticsHelper.logOnboardingStateChanged(shouldHideOnboarding)
    }
}