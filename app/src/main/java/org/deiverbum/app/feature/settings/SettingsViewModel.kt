package org.deiverbum.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.core.model.data.configuration.DarkThemeConfig
import org.deiverbum.app.core.model.data.configuration.FontSizeConfig
import org.deiverbum.app.core.model.data.configuration.ThemeBrand
import org.deiverbum.app.core.model.data.configuration.VoiceReaderConfig
import org.deiverbum.app.feature.settings.SettingsUiState.Success
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,

    ) : ViewModel() {
    val settingsUiState: StateFlow<SettingsUiState> =
        userDataRepository.userData
            .map { userData ->
                Success(
                    settings = UserEditableSettings(
                        brand = userData.themeBrand,
                        dynamic = userData.dynamic
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
                initialValue = SettingsUiState.Loading,
            )

    fun updateThemeBrand(themeBrand: ThemeBrand) {
        viewModelScope.launch {
            userDataRepository.setThemeBrand(themeBrand)
        }
    }

    fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            userDataRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    fun updateDynamicColorPreference(useDynamicColor: Boolean) {
        viewModelScope.launch {
            userDataRepository.setDynamicColorPreference(useDynamicColor)
        }
    }

    fun updateVoiceReaderPreference(useVoiceReader: VoiceReaderConfig) {
        viewModelScope.launch {
            userDataRepository.setVoiceReaderPreference(useVoiceReader)
        }
    }

    fun updateMultipleInvitatoryPreference(useMultipleInvitatory: Boolean) {
        viewModelScope.launch {
            userDataRepository.setMultipleInvitatoryPreference(useMultipleInvitatory)
        }
    }

    fun updateFontSizePreference(fontSize: FontSizeConfig) {
        viewModelScope.launch {
            userDataRepository.setFontSizePreference(fontSize)
        }
    }
}

/**
 * Represents the settings which the user can edit within the app.
 */
data class UserEditableSettings(
    val brand: ThemeBrand,
    val dynamic: UserDataDynamic
    /*val useDynamicColor: Boolean,
    val darkThemeConfig: DarkThemeConfig,
    val useVoiceReader:Boolean,
    val useMultipleInvitatory:Boolean,*/
)

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(val settings: UserEditableSettings) : SettingsUiState
}
