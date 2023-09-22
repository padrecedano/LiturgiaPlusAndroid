package org.deiverbum.app.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    //private val repository: SyncRepository
    //private val apiService: TodayApi,
    //private val todayDao: TodayDao
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // TODO: check if the license if still valid and if so return Result.success() else Result.failure()
        return try {

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}