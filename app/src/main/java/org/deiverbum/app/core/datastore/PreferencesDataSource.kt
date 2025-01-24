package org.deiverbum.app.core.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import org.deiverbum.app.core.datastore.VoiceReaderProto.UNRECOGNIZED
import org.deiverbum.app.core.datastore.VoiceReaderProto.VOICE_OFF
import org.deiverbum.app.core.datastore.VoiceReaderProto.VOICE_ON
import org.deiverbum.app.core.model.data.DarkThemeConfig
import org.deiverbum.app.core.model.data.RubricColorConfig
import org.deiverbum.app.core.model.data.ThemeBrand
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.core.model.data.VoiceReaderConfig
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data
        .map {
            UserData(
                //bookmarkedNewsResources = it.bookmarkedNewsResourceIdsMap.keys,
                //viewedNewsResources = it.viewedNewsResourceIdsMap.keys,
                //followedTopics = it.followedTopicIdsMap.keys,
                themeBrand = when (it.themeBrand) {
                    null,
                    ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
                    ThemeBrandProto.UNRECOGNIZED,
                    ThemeBrandProto.THEME_BRAND_DEFAULT,
                        -> ThemeBrand.DEFAULT

                    ThemeBrandProto.THEME_BRAND_ANDROID -> ThemeBrand.ANDROID
                },
                shouldHideOnboarding = it.shouldHideOnboarding,

                dynamic = UserDataDynamic(
                    darkThemeConfig = when (it.darkThemeConfig) {
                        null,
                        DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                        DarkThemeConfigProto.UNRECOGNIZED,
                        DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                            ->
                            DarkThemeConfig.FOLLOW_SYSTEM

                        DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
                            DarkThemeConfig.LIGHT

                        DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
                    },
                    rubricColor = when (it.rubricColor) {
                        null,
                        RubricColorProto.RUBRIC_COLOR_LIGHT,
                            ->
                            RubricColorConfig.LIGHT

                        RubricColorProto.RUBRIC_COLOR_DARK,
                            ->
                            RubricColorConfig.DARK

                        RubricColorProto.UNRECOGNIZED -> RubricColorConfig.LIGHT
                    },

                    useDynamicColor = it.useDynamicColor,
                    useVoiceReader = when (it.useVoiceReader) {
                        null,
                        VOICE_ON,
                            ->
                            VoiceReaderConfig.ON

                        VOICE_OFF,
                            ->
                            VoiceReaderConfig.OFF

                        UNRECOGNIZED -> VoiceReaderConfig.ON
                    },

                    //useVoiceReader = VoiceReaderConfig.ON,//it.useVoiceReader,
                    useMultipleInvitatory = it.useMultipleInvitatory
                )
            )
        }



    suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        userPreferences.updateData {
            it.copy {
                this.themeBrand = when (themeBrand) {
                    ThemeBrand.DEFAULT -> ThemeBrandProto.THEME_BRAND_DEFAULT
                    ThemeBrand.ANDROID -> ThemeBrandProto.THEME_BRAND_ANDROID
                }
            }
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy { this.useDynamicColor = useDynamicColor }
        }
    }

    suspend fun setVoiceReaderPreference(useVoiceReader: VoiceReaderConfig) {
        userPreferences.updateData {
            it.copy {
                this.useVoiceReader = when (useVoiceReader) {
                    VoiceReaderConfig.ON -> VOICE_ON
                    VoiceReaderConfig.OFF -> VOICE_OFF
                }

            }
        }
    }

    suspend fun setMultipleInvitatoryPreference(useMultipleInvitatory: Boolean) {
        userPreferences.updateData {
            it.copy { this.useMultipleInvitatory = useMultipleInvitatory }
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM ->
                        DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM

                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
        setRubricColorConfig(darkThemeConfig)
    }

    private suspend fun setRubricColorConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.rubricColor = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM ->
                        RubricColorProto.RUBRIC_COLOR_LIGHT

                    DarkThemeConfig.LIGHT -> RubricColorProto.RUBRIC_COLOR_LIGHT
                    DarkThemeConfig.DARK -> RubricColorProto.RUBRIC_COLOR_DARK
                }
            }
        }

    }







    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        userPreferences.updateData {
            it.copy { this.shouldHideOnboarding = shouldHideOnboarding }
        }
    }
}


