package org.deiverbum.app.core.model.configuration

/**
 * Clase para guardar las preferencias del usuario.
 */
data class UserData(
    val themeBrand: ThemeBrand,
    val shouldHideOnboarding: Boolean,
    val dynamic: UserDataDynamic
)

/**
 * Clase para guardar las preferencias dinámicas del usuario.
 */
data class UserDataDynamic(
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val useVoiceReader: VoiceReaderConfig,
    val useMultipleInvitatory: Boolean,
    val useMysteriumBrevis: RosariumConfig,
    val useAnalytics: AnalyticsConfig,
    val rubricColor: RubricColorConfig,
    val fontSize: FontSizeConfig
)