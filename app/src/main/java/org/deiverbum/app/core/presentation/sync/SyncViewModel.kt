package org.deiverbum.app.core.presentation.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.network.CoroutineDispatcherProvider
import org.deiverbum.app.domain.GetSyncUseCase
import javax.inject.Inject

/**
 * ViewModel para la sincronización.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.2
 */
@HiltViewModel
class SyncViewModel @Inject constructor(
    private val getSyncUseCase: GetSyncUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<SyncUiState>(SyncUiState.Empty)
    val uiState: StateFlow<SyncUiState> = _uiState

    /**
     * Este método se ocupa de lanzar la sincronización.
     *
     * @param syncRequest Es un objeto [SyncRequest] con toda la información de la sincronización que se está solicitando.
     */
    fun launchSync(syncRequest: SyncRequest) {
        _uiState.value = SyncUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val result = getSyncUseCase.execute(syncRequest)
                //val resultt = getSyncUseCase.executee(syncRequest)
                /*resultt.syncStatus.collect { model ->
                    //Log.d("bbb",model.tableName+"Lorem ipsum")

                }*/
                _uiState.value = SyncUiState.Loaded(SyncItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = SyncUiState.Error(error.message.toString())
            }
        }
    }


    sealed class SyncUiState {
        object Empty : SyncUiState()
        object Loading : SyncUiState()
        class Loaded(val itemState: SyncItemUiState) : SyncUiState()
        class Error(val message: String) : SyncUiState()
    }
}