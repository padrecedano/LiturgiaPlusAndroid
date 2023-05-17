package org.deiverbum.app.presentation.sync

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.deiverbum.app.model.SyncStatus
import org.deiverbum.app.repository.SyncRepository
import javax.inject.Inject

@HiltViewModel
class SyncViewModelOld @Inject constructor(private val mRepository: SyncRepository) : ViewModel() {
    val observable: LiveData<SyncStatus>
        get() = mRepository.fromDB
    val initialSyncStatus: LiveData<Int>
        get() = mRepository.initialSyncStatus
    val yearClean: LiveData<Int>
        get() = mRepository.yearClean

    fun launchSyncWorker() {
        mRepository.launchSyncWorker()
    }

    fun initialSync() {
        mRepository.initialSync()
    }

    fun cleanUp(lastYear: Int) {
        mRepository.launchCleanUp(lastYear)
    }
}