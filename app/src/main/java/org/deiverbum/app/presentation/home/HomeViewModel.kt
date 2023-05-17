package org.deiverbum.app.presentation.home

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.data.networking.CoroutineDispatcherProvider
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.usecase.GetTodayUseCase
import org.deiverbum.app.util.ExceptionParser
import org.deiverbum.app.util.TimeUtil
import java.util.*
import javax.inject.Inject
/**
 * <p>
 *     ViewModel para el Home.
 * </p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBibleUseCase: GetTodayUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val uiState: StateFlow<HomeUiState> = _uiState

    fun getData() {
        _uiState.value = HomeUiState.Loading

        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val requestParam = TodayRequest(0, getTodayDate().toInt(),false,false,false)
                //val result = getBibleUseCase.execute(requestParam)

                _uiState.value = HomeUiState.Loaded(HomeItemUiState(""))
            } catch (error: Exception) {
                _uiState.value = HomeUiState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

    private fun getTodayDate() = TimeUtil.getDateFormatted(Date())

    sealed class HomeUiState {
        object Empty : HomeUiState()
        object Loading : HomeUiState()
        class Loaded(val itemState: HomeItemUiState) : HomeUiState()
        class Error(@StringRes val message: Int) : HomeUiState()
    }
}