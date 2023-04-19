package org.deiverbum.app.presentation.homily

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.data.networking.CoroutineDispatcherProvider
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.usecase.GetHomily
import org.deiverbum.app.util.ExceptionParser
import javax.inject.Inject

@HiltViewModel
class HomilyViewModel @Inject constructor(
    private val getHomilyUseCase: GetHomily,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomilyUiState>(HomilyUiState.Empty)
    val uiState: StateFlow<HomilyUiState> = _uiState

    fun loadData() {
        _uiState.value = HomilyUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val requestParam = HomilyRequest("getTodayDate()")
                val result = getHomilyUseCase.execute(requestParam)
                _uiState.value = HomilyUiState.Loaded(BiblicalCommentItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = HomilyUiState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

    sealed class HomilyUiState {
        object Empty : HomilyUiState()
        object Loading : HomilyUiState()
        class Loaded(val itemState: BiblicalCommentItemUiState) : HomilyUiState()
        class Error(@StringRes val message: Int) : HomilyUiState()
    }
}