package org.deiverbum.app.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.deiverbum.app.data.wrappers.NetworkResult
import org.deiverbum.app.model.Today
import org.deiverbum.app.repository.Repository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (
    private val repository: Repository

    ) : ViewModel() {
    private val _response: MutableLiveData<NetworkResult<Today>> = MutableLiveData()
    val response: LiveData<NetworkResult<Today>> = _response
    fun fetchTestResponse(theDate:String) = viewModelScope.launch {
        repository.getTest(theDate).collect { values ->
            _response.value = values
        }
    }
}
