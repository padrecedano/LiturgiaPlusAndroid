package org.deiverbum.app.presentation.homily

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.data.networking.CoroutineDispatcherProvider
import org.deiverbum.app.domain.model.BiblicalCommentRequest
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.usecase.GetBiblicalComment
import org.deiverbum.app.domain.usecase.GetHomily
import org.deiverbum.app.util.ExceptionParser
import javax.inject.Inject

@HiltViewModel
class BiblicalCommentViewModel @Inject constructor(
    private val getBiblicalCommentUseCase: GetBiblicalComment,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<BiblicalCommentUiState>(BiblicalCommentUiState.Empty)
    val uiState: StateFlow<BiblicalCommentUiState> = _uiState

    fun loadData() {
        _uiState.value = BiblicalCommentUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val requestParam = BiblicalCommentRequest("getTodayDate()")
                val result = getBiblicalCommentUseCase.execute(requestParam)
                _uiState.value = BiblicalCommentUiState.Loaded(BiblicalCommentItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = BiblicalCommentUiState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

    sealed class BiblicalCommentUiState {
        object Empty : BiblicalCommentUiState()
        object Loading : BiblicalCommentUiState()
        class Loaded(val itemState: BiblicalCommentItemUiState) : BiblicalCommentUiState()
        class Error(@StringRes val message: Int) : BiblicalCommentUiState()
    }
}