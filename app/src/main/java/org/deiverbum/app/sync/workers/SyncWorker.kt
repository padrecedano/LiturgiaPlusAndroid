package org.deiverbum.app.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.tracing.traceAsync
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.deiverbum.app.core.analytics.AnalyticsHelper
import org.deiverbum.app.core.data.Synchronizer
import org.deiverbum.app.core.datastore.PreferencesDataSource
import org.deiverbum.app.core.network.Dispatcher
import org.deiverbum.app.core.network.NiaDispatchers
import org.deiverbum.app.sync.initializers.SyncConstraints
import org.deiverbum.app.sync.initializers.syncForegroundInfo
import org.deiverbum.app.sync.status.SyncSubscriber

/**
 * Syncs the data layer by delegating to the appropriate repository instances with
 * sync functionality.
 */
@HiltWorker
internal class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val niaPreferences: PreferencesDataSource,
    //private val topicRepository: TopicsRepository,
    //private val newsRepository: UniversalisRepository,
    //private val searchContentsRepository: SearchContentsRepository,
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val analyticsHelper: AnalyticsHelper,
    private val syncSubscriber: SyncSubscriber,
) : CoroutineWorker(appContext, workerParams), Synchronizer {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        traceAsync("Sync", 0) {
            analyticsHelper.logSyncStarted()

            syncSubscriber.subscribe()

            // First sync the repositories in parallel
            /*val syncedSuccessfully = awaitAll(
                //async { topicRepository.sync() },
                //async { newsRepository.sync() },
            ).all { it }
*/
            //analyticsHelper.logSyncFinished(syncedSuccessfully)

            if (/*syncedSuccessfully*/1 == 1) {
                //searchContentsRepository.populateFtsData()
                Result.success()
            } else {
                Result.retry()
            }
        }
    }



    companion object {
        /**
         * Expedited one time work to sync data on app startup
         */
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(SyncWorker::class.delegatedData())
            .build()
    }
}
