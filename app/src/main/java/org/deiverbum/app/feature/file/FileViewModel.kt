package org.deiverbum.app.feature.file

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetFileUseCase
import org.deiverbum.app.core.model.data.FileItem
import org.deiverbum.app.core.model.data.FileRequestt
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
    savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    private val getFileUseCase: GetFileUseCase,
    ) : ViewModel() {

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

    @OptIn(ExperimentalCoroutinesApi::class)
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

    sealed class FileUiState {
        data object Empty : FileUiState()
        data object Loading : FileUiState()
        class Loaded(val itemState: FileItemUiState) : FileUiState()
        class Error(val message: String) : FileUiState()
    }
}
