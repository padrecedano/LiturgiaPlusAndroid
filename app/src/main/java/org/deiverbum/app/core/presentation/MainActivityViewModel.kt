package org.deiverbum.app.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.data.UserData
import org.deiverbum.app.core.model.data.configuration.FontSizeConfig
import org.deiverbum.app.core.presentation.MainActivityUiState.Loading
import org.deiverbum.app.core.presentation.MainActivityUiState.Success
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = userDataRepository.userData.map {
        Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )

    // This variable is used to determine if the user is clicked on a radio station to play at least once
    private val _isPlayerSetUp = MutableStateFlow(false)
    val isPlayerSetUp = _isPlayerSetUp.asStateFlow()

    fun setupPlayer() {
        _isPlayerSetUp.update {
            true
        }
    }


    private val _fontSizeChoice =
        MutableStateFlow(FontSizeConfig.getFontPrefFromKey("L"))
    val fontSizeChoices: StateFlow<FontSizeConfig> = _fontSizeChoice


}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
