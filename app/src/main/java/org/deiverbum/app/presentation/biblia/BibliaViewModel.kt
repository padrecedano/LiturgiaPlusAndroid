package org.deiverbum.app.presentation.biblia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.data.networking.CoroutineDispatcherProvider
import org.deiverbum.app.domain.model.BibleBookRequest
import org.deiverbum.app.domain.usecase.GetBibleBookUseCase
import javax.inject.Inject

/**
 * ViewModel para la sincronización.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.2
 */
@HiltViewModel
class BibliaViewModel @Inject constructor(
    private val getBibleBookUseCase: GetBibleBookUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<BibliaUiState>(BibliaUiState.Empty)
    val uiState: StateFlow<BibliaUiState> = _uiState

    /**
     * Este método se ocupa de buscar el libro de la Biblia.
     *
     * @param bookRequest Es un objeto [BibleBookRequest] con toda la información de la sincronización que se está solicitando.
     */
    fun loadData(bookRequest: BibleBookRequest) {
        _uiState.value = BibliaUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val result = getBibleBookUseCase.execute(bookRequest)
                _uiState.value = BibliaUiState.Loaded(BibliaItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = BibliaUiState.Error(error.message.toString())
            }
        }
    }



    sealed class BibliaUiState {
        object Empty : BibliaUiState()
        object Loading : BibliaUiState()
        class Loaded(val itemState: BibliaItemUiState) : BibliaUiState()
        class Error(val message: String) : BibliaUiState()
    }
}