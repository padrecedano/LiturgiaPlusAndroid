package org.deiverbum.app.sync.status

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import org.deiverbum.app.core.data.util.SyncManager
import org.deiverbum.app.sync.initializers.SYNC_WORK_NAME
import org.deiverbum.app.sync.workers.SyncWorker
import javax.inject.Inject

/**
 * [SyncManager] backed by [WorkInfo] from [WorkManager]
 */
internal class WorkManagerSyncManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : SyncManager {
    override val isSyncing: Flow<Boolean> =
        WorkManager.getInstance(context).getWorkInfosForUniqueWorkFlow(SYNC_WORK_NAME)
            .map(List<WorkInfo>::anyRunning)
            .conflate()

    override fun requestSync() {
        val workManager = WorkManager.getInstance(context)
        // Run sync on app startup and ensure only one sync worker runs at any time
        workManager.enqueueUniqueWork(
            "SYNC_WORK_NAME",
            ExistingWorkPolicy.KEEP,
            SyncWorker.startUpSyncWork(),
        )
    }
}

private fun List<WorkInfo>.anyRunning() = any { it.state == WorkInfo.State.RUNNING }
