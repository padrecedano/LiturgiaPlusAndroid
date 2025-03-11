package org.deiverbum.app.feature.mas

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.deiverbum.app.core.data.repository.FileRepository
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.data.FileResponse
import org.deiverbum.app.core.model.data.book.BookTest
import org.deiverbum.app.feature.universalis.navigation.UniversalisRoute
import org.deiverbum.app.util.Utils
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MasViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userNewsResourceRepository: FileRepository,
    val userDataRepository: UserDataRepository,
    //getFileByName: GetLocalFileUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<LocalFileUiState>(LocalFileUiState.Loading)
    val uiState: StateFlow<LocalFileUiState> = _uiState.asStateFlow()


    // Key used to save and retrieve the currently selected topic id from saved state.
    private val selectedTopicIdKey = "selectedTopicIdKey"
    private var _isReading = mutableStateOf(false)
    private val route: UniversalisRoute = savedStateHandle.toRoute()

    private val selectedTopicId = savedStateHandle.getStateFlow(
        key = selectedTopicIdKey,
        initialValue = route.initialTopicId.toString(),
    )
    private val selectedDate = savedStateHandle.getStateFlow(
        key = "date",
        initialValue = Utils.hoy.toInt(),
    )

    /*fun getPlayer(files: FileRequest) {
        viewModelScope.launch {
            userNewsResourceRepository.getFile(files)
                .onStart {
                    LocalFileUiState.Loading
            }.catch { e ->

                    LocalFileUiState.Error(
                        e.message ?: "Error desconocido"
                    )

            }.collect { player ->
                    LocalFileUiState.Success(player)
            }
        }
    }*/


    // Observe news


    fun onTopicClick(topicId: String?) {
        savedStateHandle.toRoute<UniversalisRoute>().initialTopicId = topicId
        savedStateHandle[selectedTopicIdKey] = topicId
    }

    fun onReaderClick(): String {
        Timber.d("aaa", "LEER")
        _isReading.value = true

        return "Lorem ipsum"
        //savedStateHandle[TOPIC_ID_ARG] = topicId
        // topicIdd = topicId!!
    }
}

sealed interface LocalFileUiState {
    data object Loading : LocalFileUiState
    data class Error(val message: String) : LocalFileUiState
    data class Success(val news: FileResponse) : LocalFileUiState

    /*data class FileData(
        val selectedTopicId: String?,
        //val isReader: Boolean=true,
        //val topicId:Int,
        val topics: List<Book>,
    ) : LocalFileUiState*/
    data object Empty : LocalFileUiState
}

sealed interface TopicUiStatee {
    data class Success(val followableTopic: List<BookTest>) : TopicUiStatee
    data object Error : TopicUiStatee
    data object Loading : TopicUiStatee
}

sealed interface NewsFeedUiStatee {
    /**
     * The feed is still loading.
     */
    data object Loading : NewsFeedUiStatee

    /**
     * The feed is loaded with the given list of news resources.
     */
    data class Success(
        /**
         * The list of news resources contained in this feed.
         */
        val feed: List<BookTest>,
    ) : NewsFeedUiStatee
}