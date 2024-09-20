package org.deiverbum.app.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.domain.GetUniversalisUseCase
import org.deiverbum.app.feature.home.navigation.BookDetail
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val userDataRepository: UserDataRepository,
    getTopicWithDate: GetUniversalisUseCase,
) : ViewModel() {

    private val _book = MutableStateFlow(savedStateHandle.toRoute<BookDetail>().book)
    val book = _book.asStateFlow()

    fun aa() {
        Timber.d(book.value.title)

    }
}