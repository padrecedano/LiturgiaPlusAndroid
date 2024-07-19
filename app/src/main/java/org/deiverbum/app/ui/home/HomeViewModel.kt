package org.deiverbum.app.ui.home

import androidx.lifecycle.ViewModel
import org.deiverbum.app.utils.ErrorMessage

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
     * ViewModel that handles the business logic of the Home screen
     */
    class HomeViewModel(
        preSelectedPostId: String?
    ) : ViewModel()
}









