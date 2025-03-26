package org.deiverbum.app.core.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT10XL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT11XL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT12XL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT2XL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT2XS
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT3XL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT4XL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT5XL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT6XL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT7XL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT8XL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXT9XL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXTLG
import org.deiverbum.app.core.datastore.FontSizeProto.TEXTMD
import org.deiverbum.app.core.datastore.FontSizeProto.TEXTSM
import org.deiverbum.app.core.datastore.FontSizeProto.TEXTXL
import org.deiverbum.app.core.datastore.FontSizeProto.TEXTXS
import org.deiverbum.app.core.datastore.VoiceReaderProto.UNRECOGNIZED
import org.deiverbum.app.core.datastore.VoiceReaderProto.VOICE_OFF
import org.deiverbum.app.core.datastore.VoiceReaderProto.VOICE_ON
import org.deiverbum.app.core.model.configuration.AnalyticsConfig
import org.deiverbum.app.core.model.configuration.DarkThemeConfig
import org.deiverbum.app.core.model.configuration.FontSizeConfig
import org.deiverbum.app.core.model.configuration.FontSizeConfig.DEFAULT
import org.deiverbum.app.core.model.configuration.FontSizeConfig.LG
import org.deiverbum.app.core.model.configuration.FontSizeConfig.MD
import org.deiverbum.app.core.model.configuration.FontSizeConfig.SM
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL10
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL11
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL12
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL2
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL3
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL4
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL5
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL6
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL7
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL8
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XL9
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XS
import org.deiverbum.app.core.model.configuration.FontSizeConfig.XS2
import org.deiverbum.app.core.model.configuration.RosariumConfig
import org.deiverbum.app.core.model.configuration.RubricColorConfig
import org.deiverbum.app.core.model.configuration.ThemeBrand
import org.deiverbum.app.core.model.configuration.UserData
import org.deiverbum.app.core.model.configuration.UserDataDynamic
import org.deiverbum.app.core.model.configuration.VoiceReaderConfig
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data
        .map {
            UserData(
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
                    useAnalytics = when (it.useAnalytics) {
                        null,
                        AnalyticsProto.UNRECOGNIZED,
                        AnalyticsProto.ANALYTICS_OFF -> AnalyticsConfig.OFF

                        AnalyticsProto.ANALYTICS_ON -> AnalyticsConfig.ON
                    },
                    useMultipleInvitatory = it.useMultipleInvitatory,
                    useMysteriumBrevis = when (it.rosariumConfig) {
                        null,
                        RosariumConfigProto.UNRECOGNIZED,
                        RosariumConfigProto.BREVIS -> RosariumConfig.ON

                        RosariumConfigProto.OMNIS -> RosariumConfig.OFF
                    },
                    fontSize = when (it.fontSize) {
                        null,
                        FontSizeProto.UNRECOGNIZED -> SM
                        TEXT2XS -> XS2
                        TEXTXS -> XS
                        TEXTSM -> SM
                        TEXTMD -> MD
                        TEXTLG -> LG
                        TEXTXL -> SM
                        TEXT2XL -> XL2
                        TEXT3XL -> XL3
                        TEXT4XL -> XL4
                        TEXT5XL -> XL5
                        TEXT6XL -> XL6
                        TEXT7XL -> XL7
                        TEXT8XL -> XL8
                        TEXT9XL -> XL9
                        TEXT10XL -> XL10
                        TEXT11XL -> XL11
                        TEXT12XL -> XL12
                        FontSizeProto.DEFAULT -> SM
                    },
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

    suspend fun setFontSizePreference(fontSize: FontSizeConfig) {
        userPreferences.updateData {
            it.copy {
                this.fontSize = when (fontSize) {
                    XS2 -> TEXT2XS
                    XS -> TEXTXS
                    SM -> TEXTSM
                    MD -> TEXTMD
                    LG -> TEXTLG
                    XL -> TEXTXL
                    XL2 -> TEXT2XL
                    XL3 -> TEXT3XL
                    XL4 -> TEXT4XL
                    XL5 -> TEXT4XL
                    XL6 -> TEXT4XL
                    XL7 -> TEXT4XL
                    XL8 -> TEXT4XL
                    XL9 -> TEXT4XL
                    XL10 -> TEXT10XL
                    XL11 -> TEXT11XL
                    XL12 -> TEXT12XL
                    DEFAULT -> TEXTLG
                }
            }
        }
    }

    suspend fun setMultipleInvitatoryPreference(useMultipleInvitatory: Boolean) {
        userPreferences.updateData {
            it.copy { this.useMultipleInvitatory = useMultipleInvitatory }
        }
    }

    suspend fun setRosariumPreference(useRosariumBrevis: RosariumConfig) {
        userPreferences.updateData {
            it.copy {
                this.rosariumConfig = when (useRosariumBrevis) {
                    RosariumConfig.ON -> RosariumConfigProto.BREVIS
                    RosariumConfig.OFF -> RosariumConfigProto.OMNIS
                }
            }
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