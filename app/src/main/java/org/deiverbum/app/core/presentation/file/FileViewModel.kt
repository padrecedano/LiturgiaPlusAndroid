package org.deiverbum.app.core.presentation.file

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import org.deiverbum.app.core.domain.GetFileUseCase
import org.deiverbum.app.core.model.FileItem
import org.deiverbum.app.core.model.FileRequestt
import org.deiverbum.app.feature.file.navigation.FileRoute
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
    private val savedStateHandle: SavedStateHandle,
    private val getFileUseCase: GetFileUseCase,
    //@Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    ) : ViewModel() {

    //private val file : FileRoute = savedStateHandle.toRoute()

    private val fileTitleKey = "fileTitleKey"
    private val fileNameKey = "fileNameKey"

    private val route: FileRoute = savedStateHandle.toRoute()

    private val fileName = savedStateHandle.getStateFlow(
        key = fileNameKey,
        initialValue = route.fileName,
    )
    private val fileTitle = savedStateHandle.getStateFlow(
        key = fileTitleKey,
        initialValue = route.fileTitle,
    )
    private val fileRequest = MutableStateFlow<FileRequestt?>(
        FileRequestt(listOf(FileItem(fileName.value!!, fileTitle.value!!)), 1, 1, true, true, true)
    )


    val uiState: StateFlow<FileUiState> = fileRequest
        .flatMapLatest {
            it?.let(::loadDataFlow) ?: flowOf(FileUiState.Empty)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FileUiState.Empty,
        )

    private fun loadDataFlow(fileRequest: FileRequestt): Flow<FileUiState> = flow {
        emit(FileUiState.Loading)
        try {
            val result = getFileUseCase(fileRequest)
            emit(FileUiState.Loaded(FileItemUiState(result)))
        } catch (error: Exception) {
            emit(FileUiState.Error(ExceptionParser.getMessage(error)))
        }
    }

    fun setFileRequest(fileRequest: FileRequestt?) {
        this.fileRequest.value = fileRequest
    }

    fun setFileRequestt(fileName: String?) {
        this.fileRequest.value =
            FileRequestt(listOf(FileItem(fileName!!, "")), 1, 1, true, true, true)
    }

    private val _uiState = MutableStateFlow<FileUiState>(FileUiState.Loading)
    val uiStatee: StateFlow<FileUiState> = _uiState.asStateFlow()

    /*
        fun loadData(fileRequest: FileRequest) {
            _uiState.value = FileUiState.Loading
            viewModelScope.launch(ioDispatcher) {
                try {
                    val result = getFileUseCase(fileRequest)
                    _uiState.value = FileUiState.Loaded(FileItemUiState(result))
                } catch (error: Exception) {
                    _uiState.value = FileUiState.Error(ExceptionParser.getMessage(error))
                }
            }
        }
    */
    sealed class FileUiState {
        data object Empty : FileUiState()
        data object Loading : FileUiState()
        class Loaded(val itemState: FileItemUiState) : FileUiState()
        class Error(val message: String) : FileUiState()
    }
}
