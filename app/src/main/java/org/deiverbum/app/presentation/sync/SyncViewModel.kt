package org.deiverbum.app.presentation.sync

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.data.networking.CoroutineDispatcherProvider
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.usecase.GetSyncUseCase
import timber.log.Timber
import javax.inject.Inject

/**
 * <p>ViewModel para la sincronización.</p>
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




    fun initialSync(syncRequest: SyncRequest) {
        _uiState.value = SyncUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val result = getSyncUseCase.execute(syncRequest)
                _uiState.value = SyncUiState.Loaded(SyncItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = SyncUiState.Error(error.message.toString())
            }
        }
    }

    fun launchSyncWorker() {
        Timber.d("launchSyncWorker desde VM")
    }
    /*
        fun loadData(syncRequest: SyncRequest) {
        _uiState.value = SyncUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val result = getSyncUseCase.execute(syncRequest)
                _uiState.value = SyncUiState.Loaded(SyncItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = SyncUiState.Error(error.message.toString())

            }
        }
    }
*/
    sealed class SyncUiState {
        object Empty : SyncUiState()
        object Loading : SyncUiState()
        class Loaded(val itemState: SyncItemUiState) : SyncUiState()
        class Error(val message: String) : SyncUiState()
    }
}