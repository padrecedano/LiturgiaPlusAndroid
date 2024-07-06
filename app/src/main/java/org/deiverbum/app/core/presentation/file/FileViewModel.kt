package org.deiverbum.app.core.presentation.file

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.deiverbum.app.core.model.FileRequest
import org.deiverbum.app.domain.GetFileUseCase
import javax.inject.Inject

/**
 * <p>ViewModel para el contenido proveniente de archivos locales.</p>
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */
@HiltViewModel
class FileViewModel @Inject constructor(
    private val getFileUseCase: GetFileUseCase,
    //private val coroutineDispatcherProvider: CoroutineDispatcherProvider
    //@IODispatcher private val dispatcherIO: CoroutineDispatcher

) : ViewModel() {

    private val _uiState = MutableStateFlow<FileUiState>(FileUiState.Empty)
    val uiState: StateFlow<FileUiState> = _uiState

    fun loadData(fileRequest: FileRequest) {
        _uiState.value = FileUiState.Loading
        /*viewModelScope.launch(dispatcherIO) {
            try {
                val result = getFileUseCase.execute(fileRequest)
                _uiState.value = FileUiState.Loaded(FileItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = FileUiState.Error(ExceptionParser.getMessage(error))
            }
        }*/
    }

    sealed class FileUiState {
        data object Empty : FileUiState()
        data object Loading : FileUiState()
        class Loaded(val itemState: FileItemUiState) : FileUiState()
        class Error(val message: String) : FileUiState()
    }
}
