package org.deiverbum.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.deiverbum.app.R
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.presentation.today.TodayItemUiState
import org.deiverbum.app.domain.GetTodayUseCase
import org.deiverbum.app.utils.ErrorMessage
import java.util.UUID
import javax.inject.Inject

/**
 * UI state for the Home route.
 *
 * This is derived from [HomeViewModelNewState], but split into two possible subclasses to more
 * precisely represent the state available to render the UI.
 */
sealed interface TodayUiState {

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
    ) : TodayUiState

    /**
     * There are posts to render, as contained in [postsFeed].
     *
     * There is guaranteed to be a [selectedPost], which is one of the posts from [postsFeed].
     */
    data class HasPosts(
        //val postsFeed: PostsFeed,
        val selectedPost: Universalis,
        val isArticleOpen: Boolean,
        val favorites: Set<String>,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ) : TodayUiState
}


/**
 * An internal representation of the Home route state, in a raw form
 */
private data class TodayViewModelState(
    val postsFeed: Universalis? = null,
    val selectedPostId: TodayRequest? = null, // TODO back selectedPostId in a SavedStateHandle
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
    fun toUiState(): TodayUiState =
        if (postsFeed == null) {
            TodayUiState.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        } else {
            TodayUiState.HasPosts(
                //postsFeed = postsFeed,
                // Determine the selected post. This will be the post the user last selected.
                // If there is none (or that post isn't in the current feed), default to the
                // highlighted post
                selectedPost = postsFeed,
                isArticleOpen = isArticleOpen,
                favorites = favorites,
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        }
}

//@HiltViewModel
@HiltViewModel

class TodayViewModel @Inject constructor(
    //@Assisted val todayRequest: TodayRequest,
    private val getTodayUseCase: GetTodayUseCase,
    //private val coroutineDispatcherProvider: CoroutineDispatcher
    //@IODispatcher private val dispatcherIO: CoroutineDispatcher

) : ViewModel() {
    /*
    @AssistedFactory
    interface TodayViewModelFactory {
        fun create(todayRequest: TodayRequest): TodayViewModel
    }
*/
    private val viewModelState = MutableStateFlow(
        TodayViewModelState(
            isLoading = true,
            //selectedPostId = todayRequest
            //isArticleOpen = preSelectedPostId != null
        )
    )

    // UI state exposed to the UI
    val uiStateJ = viewModelState
        .map(TodayViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    /**
     * Refresh posts and update the UI state accordingly
     */
    fun refreshPosts(todayRequest: TodayRequest) {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            //val result = postsRepository.getPostsFeed()
            val result = getTodayUseCase.execute(todayRequest)
            viewModelState.update {
                when (result.dataModel) {
                    is Universalis -> it.copy(postsFeed = result.dataModel, isLoading = false)
                    /*is Result.Error -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.err_no_data
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }*/
                    else -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.err_no_data
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }
            }
        }
    }

    /**
     * Selects the given article to view more information about it.
     */
    fun selectArticle(todayRequest: TodayRequest) {
        // Treat selecting a detail as simply interacting with it
        interactedWithArticleDetails(todayRequest)
    }

    /**
     * Notify that the user interacted with the article details
     */
    fun interactedWithArticleDetails(postId: TodayRequest) {


        /*viewModelState.update {
            it.copy(
                selectedPostId = postId,
                isArticleOpen = true
            )
        }*/
    }

    /**
     * Notify that the user interacted with the feed
     */
    fun interactedWithFeed() {
        viewModelState.update {
            it.copy(isArticleOpen = false)
        }
    }


    private val _uiState = MutableStateFlow<TodayUiState>(TodayUiState.Empty)
    val uiState: StateFlow<TodayUiState> = _uiState

    fun refreshPostss(todayRequest: TodayRequest) {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = getTodayUseCase.execute(todayRequest)
            viewModelState.update {
                when (result.dataModel) {
                    is Universalis -> it.copy(
                        postsFeed = result.dataModel,
                        isLoading = false,
                        isArticleOpen = true
                    )

                    else -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.err_no_data
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }
            }
        }
    }


    fun loadData(todayRequest: TodayRequest) {
        _uiState.value = TodayUiState.Loading
        /*
        viewModelScope.launch(dispatcherIO) {
            try {
                val result = getTodayUseCase.execute(todayRequest)
                viewModelState.update {
                    when (result.dataModel) {

                                is Universalis -> {
                                    _uiState.value = TodayUiState.Loaded(TodayItemUiState(result,result.dataModel))

                                    it.copy(postsFeed = result.dataModel, isLoading = false)
                                }
                        /*is Result.Error -> {
                            val errorMessages = it.errorMessages + ErrorMessage(
                                id = UUID.randomUUID().mostSignificantBits,
                                messageId = R.string.err_no_data
                            )
                            it.copy(errorMessages = errorMessages, isLoading = false)
                        }*/
                        else -> {
                            val errorMessages = it.errorMessages + ErrorMessage(
                                id = UUID.randomUUID().mostSignificantBits,
                                messageId = R.string.err_no_data
                            )
                            it.copy(errorMessages = errorMessages, isLoading = false)
                        }
                    }
                }


            } catch (error: Exception) {
                _uiState.value = TodayUiState.Error(error.message.toString())

            }
        }
        */
    }

    sealed class TodayUiState {
        object Empty : TodayUiState()
        object Loading : TodayUiState()

        //object Universalis
        class Loaded(val itemState: TodayItemUiState) : TodayUiState()
        class Error(val message: String) : TodayUiState()
    }
}

