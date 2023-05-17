package org.deiverbum.app.presentation.legal
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.data.networking.CoroutineDispatcherProvider
import org.deiverbum.app.domain.model.FileRequest
import org.deiverbum.app.domain.usecase.GetFileUseCase
import org.deiverbum.app.util.ExceptionParser
import javax.inject.Inject

/**
 * <p>ViewModel para el contenido proveniente de archivos del módulo legal.</p>
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
@HiltViewModel
class LegalViewModel @Inject constructor(
    private val getFileUseCase: GetFileUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<LegalUiState>(LegalUiState.Empty)
    val uiState: StateFlow<LegalUiState> = _uiState

    fun loadData(fileRequest: FileRequest) {
        _uiState.value = LegalUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                //val requestParam = FileRequest(fileRequest)
                val result = getFileUseCase.execute(fileRequest)
                _uiState.value = LegalUiState.Loaded(LegalItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = LegalUiState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

    sealed class LegalUiState {
        object Empty : LegalUiState()
        object Loading : LegalUiState()
        class Loaded(val itemState: LegalItemUiState) : LegalUiState()
        class Error(@StringRes val message: Int) : LegalUiState()
    }
}