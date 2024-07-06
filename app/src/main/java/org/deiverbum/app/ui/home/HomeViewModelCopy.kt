package org.deiverbum.app.ui.home

/*
import com.example.jetnews.R
import com.example.jetnews.data.Result
import com.example.jetnews.data.posts.PostsRepository
import com.example.jetnews.model.Post
import com.example.jetnews.model.PostsFeed
import com.example.jetnews.utils.ErrorMessage*/

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.deiverbum.app.domain.GetTodayUseCase
import org.deiverbum.app.model.Post
import org.deiverbum.app.model.PostsFeed
import org.deiverbum.app.utils.ErrorMessage

/**
 * UI state for the Home route.
 *
 * This is derived from [HomeViewModelCopyState], but split into two possible subclasses to more
 * precisely represent the state available to render the UI.
 */
sealed interface HomeCopyUiState {

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
    ) : HomeCopyUiState

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
    ) : HomeCopyUiState
}

/**
 * An internal representation of the Home route state, in a raw form
 */
private data class HomeViewModelCopyState(
    val postsFeed: PostsFeed? = null,
    val selectedPostId: String? = null, // TODO back selectedPostId in a SavedStateHandle
    val isArticleOpen: Boolean = false,
    val favorites: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val searchInput: String = "",
) {

    /**
     * Converts this [HomeViewModelCopyState] into a more strongly typed [HomeCopyUiState] for driving
     * the ui.
     */
    fun toUiState(): HomeCopyUiState =
        if (postsFeed == null) {
            HomeCopyUiState.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        } else {
            HomeCopyUiState.HasPosts(
                postsFeed = postsFeed,
                // Determine the selected post. This will be the post the user last selected.
                // If there is none (or that post isn't in the current feed), default to the
                // highlighted post
                selectedPost = postsFeed.allPosts.find {
                    it.id == "post8"//selectedPostId
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
 * ViewModel that handles the business logic of the Home screen
 */
@HiltViewModel(assistedFactory = HomeViewModelCopy.HomeViewModelCopyFactory::class)
class HomeViewModelCopy @AssistedInject constructor(
    //@Assisted val todayRequest: TodayRequest,
    @Assisted val todayDate: Int,

    private val getTodayUseCase: GetTodayUseCase,
    //private val coroutineDispatcherProvider: CoroutineDispatcher
    //@IODispatcher private val dispatcherIO: CoroutineDispatcher

) : ViewModel() {

    @AssistedFactory
    interface HomeViewModelCopyFactory {
        fun create(todayDate: Int): HomeViewModelCopy
    }

    private var preSelectedPostId: String? = "post8"
    private val preselectedPostId: String = "post8"

    private val viewModelState = MutableStateFlow(
        HomeViewModelCopyState(
            isLoading = true,
            selectedPostId = preSelectedPostId,
            isArticleOpen = preSelectedPostId != null
        )
    )

    // UI state exposed to the UI
    val uiState = viewModelState
        .map(HomeViewModelCopyState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        refreshPosts()

        // Observe for favorite changes in the repo layer
        /*viewModelScope.launch {
            postsRepository.observeFavorites().collect { favorites ->
                viewModelState.update { it.copy(favorites = favorites) }
            }
        }*/
    }

    /**
     * Refresh posts and update the UI state accordingly
     */
    fun refreshPosts() {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        /*viewModelScope.launch {
            val result = postsRepository.getPostsFeed()
            viewModelState.update {
                when (result) {
                    is Result.Success -> it.copy(postsFeed = result.data, isLoading = false)
                    is Result.Error -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.err_no_data
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }
            }
        }*/
    }

    /**
     * Toggle favorite of a post
     */
    fun toggleFavourite(postId: String) {
        /*viewModelScope.launch {
            postsRepository.toggleFavorite(postId)
        }*/
    }

    /**
     * Selects the given article to view more information about it.
     */
    fun selectArticle(postId: String) {
        // Treat selecting a detail as simply interacting with it
        interactedWithArticleDetails(postId)
    }

    /**
     * Notify that an error was displayed on the screen
     */
    fun errorShown(errorId: Long) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }

    /**
     * Notify that the user interacted with the feed
     */
    fun interactedWithFeed() {
        viewModelState.update {
            it.copy(isArticleOpen = false)
        }
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

    /**
     * Notify that the user updated the search query
     */
    fun onSearchInputChanged(searchInput: String) {
        viewModelState.update {
            it.copy(searchInput = searchInput)
        }
    }

    /**
     * Factory for HomeViewModel that takes PostsRepository as a dependency
     */
    /*companion object {
        fun provideFactory(
            postsRepository: PostsRepository,
            preSelectedPostId: String? = null
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModelCopy(postsRepository, preSelectedPostId) as T
            }
        }
    }*/
}