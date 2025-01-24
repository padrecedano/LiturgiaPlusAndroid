package org.deiverbum.app.core.model.data

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
    val useVoiceReader: VoiceReaderConfig,//Boolean = true,
    val useMultipleInvitatory: Boolean,
    val rubricColor: RubricColorConfig,
)