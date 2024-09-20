package org.deiverbum.app.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetFollowableTopicsUseCase
import org.deiverbum.app.navigation.UniversalisRouteFromHome
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TypeSafetyViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    getFollowableTopics: GetFollowableTopicsUseCase,
) : ViewModel() {

    // Key used to save and retrieve the currently selected topic id from saved state.
    private val selectedTopicIdKey = "selectedTopicIdKey"

    private val _book = MutableStateFlow(savedStateHandle.toRoute<UniversalisRouteFromHome>())
    val book = _book.asStateFlow()
    private val interestsRoute: UniversalisRouteFromHome = savedStateHandle.toRoute()

    private val selectedTopicId = savedStateHandle.getStateFlow(
        key = selectedTopicIdKey,
        initialValue = interestsRoute.initialTopicId,
    )


    fun show(): String? {
        Timber.d("axy: ${interestsRoute.initialTopicId}")
        return interestsRoute.initialTopicId
    }
}