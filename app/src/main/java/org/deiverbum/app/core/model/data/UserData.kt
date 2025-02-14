package org.deiverbum.app.core.model.data

import org.deiverbum.app.core.model.data.configuration.DarkThemeConfig
import org.deiverbum.app.core.model.data.configuration.FontSizeConfig
import org.deiverbum.app.core.model.data.configuration.RubricColorConfig
import org.deiverbum.app.core.model.data.configuration.ThemeBrand
import org.deiverbum.app.core.model.data.configuration.VoiceReaderConfig

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
    val fontSize: FontSizeConfig
)