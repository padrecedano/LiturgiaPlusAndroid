package org.deiverbum.app.core.presentation.today

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.deiverbum.app.core.model.TodayRequest
import javax.inject.Inject


/**
 * <p>ViewModel para el contenido litúrgico de la fecha dada.</p>
 *
 * @author A. Cedano
 * @since 2024.1
 */
@HiltViewModel
class TodayViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow<TodayUiStateOldVersion>(TodayUiStateOldVersion.Empty)
    val uiState: StateFlow<TodayUiStateOldVersion> = _uiState

    fun loadData(todayRequest: TodayRequest) {
        _uiState.value = TodayUiStateOldVersion.Loading
    }

    sealed class TodayUiStateOldVersion {
        object Empty : TodayUiStateOldVersion()
        object Loading : TodayUiStateOldVersion()
        class Loaded(val itemState: TodayItemUiState) : TodayUiStateOldVersion()
        class Error(val message: String) : TodayUiStateOldVersion()
    }
}

