package org.deiverbum.app.core.presentation.today

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.domain.GetTodayUseCase
import org.deiverbum.app.utils.ErrorMessage
import javax.inject.Inject


/**
 * UI state for the Home route.
 *
 * This is derived from [HomeViewModelState], but split into two possible subclasses to more
 * precisely represent the state available to render the UI.
 */
sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>
    val searchInput: String

    /**
     * There are no posts to render.
     *
     * This could either be because they are still loading or they failed to load, and we are
     * waiting to reload them.
     */
    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiState


}



/**
 * <p>ViewModel para el contenido litúrgico de la fecha dada.</p>
 *
 * @author A. Cedano
 * @since 2024.1
 */
@HiltViewModel
class TodayViewModel @Inject constructor(
    private val getTodayUseCase: GetTodayUseCase,
    //private val coroutineDispatcherProvider: CoroutineDispatcher
    //@IODispatcher private val dispatcherIO: CoroutineDispatcher

) : ViewModel() {
    private val _counter = MutableStateFlow("")
    //private val _request = MutableStateFlow(TodayRequest())

    val counter = _counter.asStateFlow()

    /*
        init {
            changeCounterValue("")
        }
    */
    private fun changeCounterValue(cnt: String) {
        _counter.value = cnt
    }


    /**
     * Selects the given article to view more information about it.
     */
    fun selectArticle(postId: String) {
        // Treat selecting a detail as simply interacting with it
    }

    private val _uiState = MutableStateFlow<TodayUiState>(TodayUiState.Empty)
    val uiState: StateFlow<TodayUiState> = _uiState

    fun loadData(todayRequest: TodayRequest) {
        _uiState.value = TodayUiState.Loading

    }

    fun getDetailText(): String {
        return counter.value

    }

    sealed class TodayUiState {
        object Empty : TodayUiState()
        object Loading : TodayUiState()

        //object Universalis
        class Loaded(val itemState: TodayItemUiState) : TodayUiState()
        class Error(val message: String) : TodayUiState()
    }
}

