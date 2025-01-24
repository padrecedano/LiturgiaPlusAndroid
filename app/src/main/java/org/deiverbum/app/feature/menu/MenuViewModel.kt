package org.deiverbum.app.feature.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.data.DarkThemeConfig
import org.deiverbum.app.core.model.data.ThemeBrand
import org.deiverbum.app.core.model.data.UserDataDynamic
import org.deiverbum.app.core.model.data.VoiceReaderConfig
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {
    /*
    val settingsUiState: StateFlow<MenuUiState> =
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
                initialValue = MenuUiState.Loading,
            )
*/
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

sealed interface MenuUiState {
    data object Loading : MenuUiState
    data class Success(val settings: UserEditableSettings) : MenuUiState
}
