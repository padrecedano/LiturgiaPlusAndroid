package org.deiverbum.app.core.presentation.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.network.di.IODispatcher
import org.deiverbum.app.domain.GetInitialSyncUseCase
import javax.inject.Inject

/**
 * ViewModel para la sincronización inicial.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
@HiltViewModel
class InitialSyncViewModel @Inject constructor(
    private val getInitialSyncUseCase: GetInitialSyncUseCase,
    //private val coroutineDispatcherProvider: CoroutineDispatcherProvider
    @IODispatcher private val dispatcherIO: CoroutineDispatcher

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
        viewModelScope.launch(dispatcherIO) {
            try {
                val result = getInitialSyncUseCase.execute(syncRequest)
                //val resultt = getSyncUseCase.executee(syncRequest)
                /*resultt.syncStatus.collect { model ->
                    //Log.d("bbb",model.tableName+"Lorem ipsum")
                _uiState.value = TodayUiState.Loaded(TodayItemUiState(result))

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