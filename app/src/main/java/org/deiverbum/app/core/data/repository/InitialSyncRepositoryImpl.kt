package org.deiverbum.app.core.data.repository

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import org.deiverbum.app.core.data.factory.SyncFactory
import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.model.SyncResponse
import org.deiverbum.app.core.model.data.SyncStatus
import org.deiverbum.app.util.Constants.SYNC_TAG
import org.deiverbum.app.util.Source
import org.deiverbum.app.workers.TodayWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 *
 * Implementación del Repositorio para el módulo de Sincronización.
 *
 * Busca en la base de datos la última fecha disponible en el calendario y la fecha de la última sincronización.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

class InitialSyncRepositoryImpl @Inject constructor(
    private val syncFactory: SyncFactory,
    @ApplicationContext val context: Context

) : InitialSyncRepository {

    /**
     * Este método obtiene el estado de la sincronización, según los datos
     * en el objeto [SyncRequest] que recibe en parámetro.
     *
     * El método funciona según dos contextos:
     *
     * 1. Si la propiedad [SyncRequest.hasInitialSync] es `false` abrirá una fuente remota ([Source.NETWORK])
     * para traer los datos remotos y guardarlos en la base de datos local. Pero si el valor de la propiedad
     * [SyncResponse.allToday] está vacío, abrirá una fuente remota, pero en Firebase ([Source.FIREBASE])
     * para traer los datos remotos de la presente semana.
     *
     *    En cualquiera de los casos, si el valor de la propiedad [SyncResponse.allToday] no está vacío
     *    abrirá una fuente de datos local para insertar los datos del objeto [SyncResponse]
     *    en la base de datos local. Finalmente, retornará el objeto [SyncResponse].
     *
     * 2. Si la propiedad [SyncRequest.hasInitialSync] es `true` verificará si el valor
     *  de la propiedad [SyncRequest.isWorkScheduled] es `false`, en cuyo caso
     *  llamará a [launchSyncWorker] para iniciar a [TodayWorker]. Luego, abrirá una fuente
     *  de datos local y obtendrá el estado actual de la sincronización en la base de datos.
     *  La llamada al método [LocalSyncEntityData.getSync][org.deiverbum.app.core.data.source.local.LocalSyncEntityData.getSync]
     *  verificará si hay que limpiar datos del año anterior.
     *
     * @param syncRequest Es un objeto [SyncRequest] que contiene la información necesaria para la sincronización que se debe realizar.
     * @return un objeto [SyncResponse]
     */
    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        var syncResponse: SyncResponse
        //launchSyncWorker()
        syncResponse = SyncResponse(SyncStatus(), emptyList())
        //return syncResponse
        //if (!syncRequest.hasInitialSync) {
        //if (!syncRequest.hasInitialSync||true) {
        syncResponse = syncFactory.create(Source.NETWORK).getSync(syncRequest)
        if (syncResponse.allToday.isEmpty()) {
            syncResponse = syncFactory.create(Source.FIREBASE).getSync(syncRequest)
            syncResponse.syncStatus.source = Source.FIREBASE
        }
        if (syncResponse.allToday.isNotEmpty()) {
            syncFactory.create(Source.LOCAL).addSync(syncResponse)
        }
        if (!syncRequest.isWorkScheduled) {
            launchSyncWorker()
        }

        return syncResponse
        //}


        //return syncFactory.create(Source.LOCAL).getSync(syncRequest)
    }


    /**
     *
     * Lanza el objeto  [TodayWorker], encargado de monitorear/sincronizar
     * eventuales cambios en los datos remotos.
     */
    private fun launchSyncWorker() {
        val mWorkManager = WorkManager.getInstance(context)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        //TODO: "Asignar SYNC_INTERVAL_DAYS como valor para repeatInteval y poner TimeUnit.DAYS"
        val periodicSyncDataWork =
            PeriodicWorkRequest.Builder(TodayWorker::class.java, 15, TimeUnit.MINUTES)
                .addTag(SYNC_TAG)
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
        mWorkManager.enqueueUniquePeriodicWork(
            "SYNC_TODAY", ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            periodicSyncDataWork
        )
    }
}