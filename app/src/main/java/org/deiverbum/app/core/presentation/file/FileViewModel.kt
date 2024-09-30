package org.deiverbum.app.core.presentation.file

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.core.domain.GetFileUseCase
import org.deiverbum.app.core.model.FileRequest
import org.deiverbum.app.core.network.Dispatcher
import org.deiverbum.app.core.network.NiaDispatchers
import org.deiverbum.app.util.ExceptionParser
import javax.inject.Inject

/**
 * ViewModel para el contenido proveniente de archivos locales.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */
@HiltViewModel
class FileViewModel @Inject constructor(
    private val getFileUseCase: GetFileUseCase,
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,

    ) : ViewModel() {

    private val _uiState = MutableStateFlow<FileUiState>(FileUiState.Loading)
    val uiState: StateFlow<FileUiState> = _uiState.asStateFlow()


    fun loadData(fileRequest: FileRequest) {
        _uiState.value = FileUiState.Loading
        viewModelScope.launch {
            try {
                val result = getFileUseCase(fileRequest)
                _uiState.value = FileUiState.Loaded(FileItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = FileUiState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

    sealed class FileUiState {
        data object Empty : FileUiState()
        data object Loading : FileUiState()
        class Loaded(val itemState: FileItemUiState) : FileUiState()
        class Error(val message: String) : FileUiState()
    }
}
