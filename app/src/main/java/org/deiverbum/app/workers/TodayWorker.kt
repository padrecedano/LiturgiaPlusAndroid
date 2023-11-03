package org.deiverbum.app.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.deiverbum.app.core.database.dao.TodayDao
import org.deiverbum.app.core.network.api.TodayApi

/**
 * Esta clase se ocupa de manejar el Worker de sincronización.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@HiltWorker
class TodayWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val apiService: TodayApi,
    private val todayDao: TodayDao

) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            loadCrud()
            Result.success()
        } catch (error: Throwable) {
            Log.e(
                "WKerr",
                "applicationContext.resources.getString(R.string.error_applying_blur)",
                error
            )
            Result.failure()
        }
    }

    /**
     * Este método busca si hay sincronizaciones disponibles en el servidor
     * desde la última sincronización.
     * Si las hay, actualizará esos datos llamando al método [`Crud.doCrud()`](org.deiverbum.app.core.model.data.crud.Crud.doCrud)
     * y actualizará también la fecha de la última sincronización, que será la actual,
     * en la base de datos.
     *
     */
    private suspend fun loadCrud() {
        val crud = apiService.postCrud(todayDao.allSyncStatus)
        if(crud!!.haveData) {
            crud.doCrud(todayDao)
            todayDao.syncUpdate(crud.lastUpdate)
        }
    }
}