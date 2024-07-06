package org.deiverbum.app.ui.interests


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import org.deiverbum.app.data.interests.InterestSection
import org.deiverbum.app.data.interests.InterestsRepository
import org.deiverbum.app.data.interests.TopicSelection
import org.deiverbum.app.data.successOr
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * UI state for the Interests screen
 */
data class InterestsUiState(
    val topics: List<InterestSection> = emptyList(),
    val people: List<String> = emptyList(),
    val publications: List<String> = emptyList(),
    val loading: Boolean = false,
)

class InterestsViewModel(
    private val interestsRepository: InterestsRepository
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(InterestsUiState(loading = true))
    val uiState: StateFlow<InterestsUiState> = _uiState.asStateFlow()

    val selectedTopics =
        interestsRepository.observeTopicsSelected().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptySet()
        )

    val selectedPeople =
        interestsRepository.observePeopleSelected().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptySet()
        )

    val selectedPublications =
        interestsRepository.observePublicationSelected().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptySet()
        )

    init {
        refreshAll()
    }

    fun toggleTopicSelection(topic: TopicSelection) {
        viewModelScope.launch {
            interestsRepository.toggleTopicSelection(topic)
        }
    }

    fun togglePersonSelected(person: String) {
        viewModelScope.launch {
            interestsRepository.togglePersonSelected(person)
        }
    }

    fun togglePublicationSelected(publication: String) {
        viewModelScope.launch {
            interestsRepository.togglePublicationSelected(publication)
        }
    }

    /**
     * Refresh topics, people, and publications
     */
    private fun refreshAll() {
        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            // Trigger repository requests in parallel
            val topicsDeferred = async { interestsRepository.getTopics() }
            val peopleDeferred = async { interestsRepository.getPeople() }
            val publicationsDeferred = async { interestsRepository.getPublications() }

            // Wait for all requests to finish
            val topics = topicsDeferred.await().successOr(emptyList())
            val people = peopleDeferred.await().successOr(emptyList())
            val publications = publicationsDeferred.await().successOr(emptyList())

            _uiState.update {
                it.copy(
                    loading = false,
                    topics = topics,
                    people = people,
                    publications = publications
                )
            }
        }
    }

    /**
     * Factory for InterestsViewModel that takes PostsRepository as a dependency
     */
    companion object {
        fun provideFactory(
            interestsRepository: InterestsRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return InterestsViewModel(interestsRepository) as T
            }
        }
    }
}