package org.deiverbum.app.core.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.domain.GetTodayUseCase
import org.deiverbum.app.model.Post
import org.deiverbum.app.model.PostsFeed
import org.deiverbum.app.ui.home.HomeNewUiState
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

    /**
     * There are posts to render, as contained in [postsFeed].
     *
     * There is guaranteed to be a [selectedPost], which is one of the posts from [postsFeed].
     */
    data class HasPosts(
        val postsFeed: PostsFeed,
        val selectedPost: Post,
        val isArticleOpen: Boolean,
        val favorites: Set<String>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : HomeUiState
}

/**
 * An internal representation of the Home route state, in a raw form
 */
private data class HomeViewModelState(
    val postsFeed: PostsFeed? = null,
    val selectedPostId: String? = null, // TODO back selectedPostId in a SavedStateHandle
    val isArticleOpen: Boolean = false,
    val favorites: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val searchInput: String = "",
) {

    /**
     * Converts this [HomeViewModelState] into a more strongly typed [HomeUiState] for driving
     * the ui.
     */
    fun toUiState(): HomeUiState =
        if (postsFeed == null) {
            HomeUiState.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        } else {
            HomeUiState.HasPosts(
                postsFeed = postsFeed,
                // Determine the selected post. This will be the post the user last selected.
                // If there is none (or that post isn't in the current feed), default to the
                // highlighted post
                selectedPost = postsFeed.allPosts.find {
                    it.id == selectedPostId
                } ?: postsFeed.highlightedPost,
                isArticleOpen = isArticleOpen,
                favorites = favorites,
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        }
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
        interactedWithArticleDetails(postId)
    }

    /**
     * Notify that the user interacted with the article details
     */
    fun interactedWithArticleDetails(postId: String) {
        viewModelState.update {
            it.copy(
                selectedPostId = postId,
                isArticleOpen = true
            )
        }
    }


    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true,
            //selectedPostId = preSelectedPostId,
            //isArticleOpen = preSelectedPostId != null
        )
    )

    // UI state exposed to the UI
    val uiStateHome = viewModelState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )


    private val _uiState = MutableStateFlow<TodayUiState>(TodayUiState.Empty)
    val uiState: StateFlow<TodayUiState> = _uiState

    fun loadData(todayRequest: TodayRequest) {
        _uiState.value = TodayUiState.Loading
        /*
        viewModelScope.launch(dispatcherIO) {
            try {
                val result = getTodayUseCase.execute(todayRequest)
                //_uiState.value = TodayUiState.Loaded(TodayItemUiState(result,result.dataModel))
                changeCounterValue(result.dataModel.getAllForView(todayRequest).toString())
            } catch (error: Exception) {
                _uiState.value = TodayUiState.Error(error.message.toString())
                changeCounterValue(error.message.toString())

            }
        }
        */
    }

    fun getDetailText(): String {
        return counter.value
        /*when (val state = uiState.value) {
            is TodayUiState.Loading ->  return "Loading..."

            is TodayUiState.Empty ->  return "Empty"
            is TodayUiState.Error -> return state.message
            is TodayUiState.Loaded -> {
                val tr= TodayRequest(0,1,false,false)
                /*val annotatedString = buildAnnotatedString {
                    append(state.itemState.todayResponse.dataModel.getAllForView())

                }*/
                return state.itemState.todayResponse.dataModel.getAllForView(tr).toString()


                //Text(text = s.toString())
            }
            //TodayViewModel.TodayUiState.Loading -> CircularProgressIndicator()
        }*/
    }

    sealed class TodayUiState {
        object Empty : TodayUiState()
        object Loading : TodayUiState()

        //object Universalis
        class Loaded(val itemState: TodayItemUiState) : TodayUiState()
        class Error(val message: String) : TodayUiState()
    }
}

/**
 * An internal representation of the Home route state, in a raw form
 */
private data class HomeViewModelNewState(
    val postsFeed: PostsFeed? = null,
    val selectedPostId: String? = null, // TODO back selectedPostId in a SavedStateHandle
    val isArticleOpen: Boolean = false,
    val favorites: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val searchInput: String = "",
) {

    /**
     * Converts this [HomeViewModelNewState] into a more strongly typed [HomeNewUiState] for driving
     * the ui.
     */
    fun toUiState(): HomeNewUiState =
        if (postsFeed == null) {
            HomeNewUiState.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        } else {
            HomeNewUiState.HasPosts(
                postsFeed = postsFeed,
                // Determine the selected post. This will be the post the user last selected.
                // If there is none (or that post isn't in the current feed), default to the
                // highlighted post
                selectedPost = postsFeed.allPosts.find {
                    it.id == selectedPostId
                } ?: postsFeed.highlightedPost,
                isArticleOpen = isArticleOpen,
                favorites = favorites,
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        }
}
