package org.deiverbum.app.core.data.repository

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.core.datastore.PreferencesDataSource
import org.deiverbum.app.core.model.data.DarkThemeConfig
import org.deiverbum.app.core.model.data.RubricColorConfig
import org.deiverbum.app.core.model.data.ThemeBrand
import org.deiverbum.app.core.model.data.UserData
import javax.inject.Inject

class OfflineFirstUserDataRepository @Inject constructor(
    private val niaPreferencesDataSource: PreferencesDataSource,
    private val analyticsHelper: AnalyticsHelper,
) : UserDataRepository {

    override val userData: Flow<UserData> =
        niaPreferencesDataSource.userData

    @VisibleForTesting
    override suspend fun setFollowedTopicIds(followedTopicIds: Set<String>) =
        niaPreferencesDataSource.setFollowedTopicIds(followedTopicIds)

    override suspend fun setTopicIdFollowed(followedTopicId: String, followed: Boolean) {
        niaPreferencesDataSource.setTopicIdFollowed(followedTopicId, followed)
        analyticsHelper.logTopicFollowToggled(followedTopicId, followed)
    }

    override suspend fun setNewsResourceBookmarked(newsResourceId: String, bookmarked: Boolean) {
        niaPreferencesDataSource.setNewsResourceBookmarked(newsResourceId, bookmarked)
        analyticsHelper.logNewsResourceBookmarkToggled(
            newsResourceId = newsResourceId,
            isBookmarked = bookmarked,
        )
    }

    override suspend fun setNewsResourceViewed(newsResourceId: String, viewed: Boolean) =
        niaPreferencesDataSource.setNewsResourceViewed(newsResourceId, viewed)

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        niaPreferencesDataSource.setThemeBrand(themeBrand)
        analyticsHelper.logThemeChanged(themeBrand.name)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        var rubricColor = RubricColorConfig.LIGHT
        if (darkThemeConfig.name.equals(RubricColorConfig.DARK)) {
            rubricColor = RubricColorConfig.LIGHT
        }
        niaPreferencesDataSource.setDarkThemeConfig(darkThemeConfig)

        analyticsHelper.logDarkThemeConfigChanged(darkThemeConfig.name)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        niaPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
        analyticsHelper.logDynamicColorPreferenceChanged(useDynamicColor)
    }

    override suspend fun setVoiceReaderPreference(useVoiceReader: Boolean) {
        niaPreferencesDataSource.setVoiceReaderPreference(useVoiceReader)
        analyticsHelper.logDynamicColorPreferenceChanged(useVoiceReader)
    }

    override suspend fun setMultipleInvitatoryPreference(useMultipleInvitatory: Boolean) {
        niaPreferencesDataSource.setMultipleInvitatoryPreference(useMultipleInvitatory)
        analyticsHelper.logDynamicColorPreferenceChanged(useMultipleInvitatory)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        niaPreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)
        analyticsHelper.logOnboardingStateChanged(shouldHideOnboarding)
    }
}