package org.deiverbum.app.core.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.network.di.IODispatcher
import org.deiverbum.app.domain.GetTodayUseCase
import javax.inject.Inject

/**
 * <p>ViewModel para el contenido litúrgico de la fecha dada.</p>
 *
 * @author A. Cedano
 * @since 2024.1
 */
@HiltViewModel
class TodayViewModel @Inject constructor(
    private val getTodayUseCase: GetTodayUseCase,
    //private val coroutineDispatcherProvider: CoroutineDispatcher
    @IODispatcher private val dispatcherIO: CoroutineDispatcher

) : ViewModel() {

    private val _uiState = MutableStateFlow<TodayUiState>(TodayUiState.Empty)
    val uiState: StateFlow<TodayUiState> = _uiState

    fun loadData(todayRequest: TodayRequest) {
        _uiState.value = TodayUiState.Loading
        viewModelScope.launch(dispatcherIO) {
            try {
                val result = getTodayUseCase.execute(todayRequest)
                _uiState.value = TodayUiState.Loaded(TodayItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = TodayUiState.Error(error.message.toString())

            }
        }
    }

    sealed class TodayUiState {
        object Empty : TodayUiState()
        object Loading : TodayUiState()
        class Loaded(val itemState: TodayItemUiState) : TodayUiState()
        class Error(val message: String) : TodayUiState()
    }
}