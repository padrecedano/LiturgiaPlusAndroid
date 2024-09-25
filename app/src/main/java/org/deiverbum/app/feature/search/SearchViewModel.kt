package org.deiverbum.app.feature.search

//import kotlinx.coroutines.launch
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.deiverbum.app.core.analytics.AnalyticsHelper
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    /*    getSearchContentsUseCase: GetSearchContentsUseCase,
        recentSearchQueriesUseCase: GetRecentSearchQueriesUseCase,
        private val searchContentsRepository: SearchContentsRepository,
        private val recentSearchRepository: RecentSearchRepository,
        private val userDataRepository: UserDataRepository,
        private val universalisRepository: UserDataRepository,
    */
    private val savedStateHandle: SavedStateHandle,
    private val analyticsHelper: AnalyticsHelper,
) : ViewModel()

//val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")


/*
val searchResultUiState: StateFlow<SearchResultUiState> =
    searchContentsRepository.getSearchContentsCount()
        .flatMapLatest { totalCount ->
            if (totalCount < SEARCH_MIN_FTS_ENTITY_COUNT) {
                flowOf(SearchResultUiState.SearchNotReady)
            } else {
                searchQuery.flatMapLatest { query ->
                    if (query.length < SEARCH_QUERY_MIN_LENGTH) {
                        flowOf(SearchResultUiState.EmptyQuery)
                    } else {
                        getSearchContentsUseCase(query)
                            // Not using .asResult() here, because it emits Loading state every
                            // time the user types a letter in the search box, which flickers the screen.
                            .map<UserSearchResult, SearchResultUiState> { data ->
                                SearchResultUiState.Success(
                                    topics = data.topics,
                                    newsResources = data.newsResources,
                                )
                            }
                            .catch { emit(SearchResultUiState.LoadFailed) }
                    }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchResultUiState.Loading,
        )

*/
/*
    val recentSearchQueriesUiState: StateFlow<RecentSearchQueriesUiState> =
        recentSearchQueriesUseCase()
            .map(RecentSearchQueriesUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RecentSearchQueriesUiState.Loading,
            )
*//*
    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    /**
     * Called when the search action is explicitly triggered by the user. For example, when the
     * search icon is tapped in the IME or when the enter key is pressed in the search text field.
     *
     * The search results are displayed on the fly as the user types, but to explicitly save the
     * search query in the search text field, defining this method.
     */
    fun onSearchTriggered(query: String) {
        viewModelScope.launch {
            recentSearchRepository.insertOrReplaceRecentSearch(searchQuery = query)
        }
        analyticsHelper.logEventSearchTriggered(query = query)
    }

    fun clearRecentSearches() {
        viewModelScope.launch {
            recentSearchRepository.clearRecentSearches()
        }
    }

    fun setNewsResourceBookmarked(newsResourceId: String, isChecked: Boolean) {
        viewModelScope.launch {
            userDataRepository.setNewsResourceBookmarked(newsResourceId, isChecked)
        }
    }

    fun followTopic(followedTopicId: String, followed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setTopicIdFollowed(followedTopicId, followed)
        }
    }

    fun setNewsResourceViewed(newsResourceId: String, viewed: Boolean) {
        viewModelScope.launch {
            userDataRepository.setNewsResourceViewed(newsResourceId, viewed)
        }
    }
}

private fun AnalyticsHelper.logEventSearchTriggered(query: String) =
    logEvent(
        event = AnalyticsEvent(
            type = SEARCH_QUERY,
            extras = listOf(element = AnalyticsEvent.Param(key = SEARCH_QUERY, value = query)),
        ),
    )

/** Minimum length where search query is considered as [SearchResultUiState.EmptyQuery] */
private const val SEARCH_QUERY_MIN_LENGTH = 2

/** Minimum number of the fts table's entity count where it's considered as search is not ready */
private const val SEARCH_MIN_FTS_ENTITY_COUNT = 1
private const val SEARCH_QUERY = "searchQuery"*/