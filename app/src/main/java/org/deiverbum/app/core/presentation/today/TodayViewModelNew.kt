package org.deiverbum.app.core.presentation.today

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.domain.GetTodayUseCase
import javax.inject.Inject

/**
 * <p>ViewModel para el contenido litúrgico de la fecha dada.</p>
 *
 * @author A. Cedano
 * @since 2024.1
 */
@HiltViewModel
class TodayViewModelNew @Inject constructor(
    private val getTodayUseCase: GetTodayUseCase,
    //private val coroutineDispatcherProvider: CoroutineDispatcher
    //@IODispatcher private val dispatcherIO: CoroutineDispatcher

) : ViewModel() {

    private val _uiStatee = MutableStateFlow<TodayUiStatee>(TodayUiStatee.Empty)
    val uiStatee: StateFlow<TodayUiStatee> = _uiStatee.asStateFlow()
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = mutableListOf<String>()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun loadData(todayRequest: TodayRequest) {
        //_uiState.value = TodayUiStatee.Loading
        /*
        viewModelScope.launch(dispatcherIO) {
            try {
                val result = getTodayUseCase.execute(todayRequest)
                setDate(result.dataModel.fecha)
                //_uiState.value = TodayUiStatee.Loaded(TodayItemUiState(result))
            } catch (error: Exception) {
                //_uiState.value = TodayUiStatee.Error(error.message.toString())

            }
        }
        */
    }

    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                date = pickupDate,
                //price = calculatePrice(pickupDate = pickupDate)
            )
        }
    }

    sealed class TodayUiStatee {
        object Empty : TodayUiStatee()
        object Loading : TodayUiStatee()
        class Loaded(val itemState: TodayItemUiState) : TodayUiStatee()
        class Error(val message: String) : TodayUiStatee()
    }
}