package org.deiverbum.app.presentation.file
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
 * <p>ViewModel para el contenido proveniente de archivos locales.</p>
 *
 * @author A. Cedano
 * @since 2023.3
 */
@HiltViewModel
class FileViewModel @Inject constructor(
    private val getFileUseCase: GetFileUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<FileUiState>(FileUiState.Empty)
    val uiState: StateFlow<FileUiState> = _uiState

    fun loadData(fileRequest: FileRequest) {
        _uiState.value = FileUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                //val requestParam = FileRequest(fileRequest)
                val result = getFileUseCase.execute(fileRequest)
                _uiState.value = FileUiState.Loaded(FileItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = FileUiState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

    sealed class FileUiState {
        object Empty : FileUiState()
        object Loading : FileUiState()
        class Loaded(val itemState: FileItemUiState) : FileUiState()
        class Error(@StringRes val message: Int) : FileUiState()
    }
}