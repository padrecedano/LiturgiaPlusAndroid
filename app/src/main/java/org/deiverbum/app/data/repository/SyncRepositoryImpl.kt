package org.deiverbum.app.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import org.deiverbum.app.data.factory.SyncFactory
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.domain.repository.SyncRepository
import org.deiverbum.app.model.SyncStatus
import org.deiverbum.app.util.Source
import org.deiverbum.app.utils.Constants.SYNC_TAG
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

class SyncRepositoryImpl @Inject constructor(
    private val syncFactory: SyncFactory,
    @ApplicationContext val context: Context

) : SyncRepository {

    private val initialSync = MutableLiveData<Int>()
    private val yearClean = MutableLiveData<Int>()


    /**
     *
     * Lanza el objeto  [TodayWorker], encargado de monitorear/sincronizar
     * eventuales cambios en los datos remotos.
     */
    fun launchSyncWorker() {
        val mWorkManager = WorkManager.getInstance(context)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
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
            "SYNC_TODAY", ExistingPeriodicWorkPolicy.UPDATE,
            periodicSyncDataWork
        )
    }


    val initialSyncStatus: LiveData<Int>
        get() = initialSync

    fun getYearClean(): LiveData<Int> {
        return yearClean
    }

    fun launchCleanUp(lastYear: Int) {
        //mTodayDao.deleteOldEntries(lastYear)
        yearClean.value = 1
    }


    /**
     *
     * <p>
     *     Este método buscará las fechas disponibles en el servidor remoto.
     *     Si hubiera algún fallo, buscará provisionalmente las fechas
     *     de la presente semana en Firebase. En este caso, el módulo de sincronización
     *     intentará en cuanto sea posible una sincronización completa
     *     con los datos del servidor remoto.
     *     En cualquier caso, se insertarán en la tabla Today de la base de datos
     *     las fechas que se hayan encontrado, sin importar la fuente.
     *     Finalmente, se activará el Worker llamando a [launchSyncWorker].
     * </p>
     *
     */
    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        var syncResponse : SyncResponse
        if(!syncRequest.hasInitial){
            syncResponse=syncFactory.create(Source.NETWORK).getSync(syncRequest)
            if(syncResponse.allToday.isNotEmpty()){
                syncFactory.create(Source.LOCAL).addSync(syncResponse)
            } else {
                syncResponse=syncFactory.create(Source.FIREBASE).getSync(syncRequest)
                syncFactory.create(Source.LOCAL).addSync(syncResponse)

            }
            return syncResponse
        }
        if(!syncRequest.isWorkScheduled && syncRequest.hasInitial){
            launchSyncWorker()
        }
        //syncResponse=syncFactory.create(Source.LOCAL).getSync(syncRequest)

        return syncFactory.create(Source.LOCAL).getSync(syncRequest)
/*
        return if (syncRequest.yearToClean!=0) {
            syncFactory.create(Source.LOCAL).getSync(syncRequest)
        }else {
            syncFactory.create(Source.LOCAL).getSync(syncRequest)
        }*/
    }


    /**
     *
     * <p>
     *     Este método buscará las fechas disponibles en el servidor remoto.
     *     Si hubiera algún fallo, buscará provisionalmente las fechas
     *     de la presente semana en Firebase. En este caso, el módulo de sincronización
     *     intentará en cuanto sea posible una sincronización completa
     *     con los datos del servidor remoto.
     *     En cualquier caso, se insertarán en la tabla Today de la base de datos
     *     las fechas que se hayan encontrado, sin importar la fuente.
     *     Finalmente, se activará el Worker llamando a [launchSyncWorker].
     * </p>
     *
     */
    suspend fun getInitialSync(syncRequest: SyncRequest): SyncResponse {
        val syncResponse :SyncResponse
        val syncNetwork = syncFactory.create(Source.NETWORK).getSync(syncRequest)
        syncResponse = if (syncNetwork.allToday.isNotEmpty()) {
            syncFactory.create(Source.LOCAL).addSync(syncNetwork)
            syncNetwork
        } else {
            val syncFirebase = syncFactory.create(Source.FIREBASE).getSync(syncRequest)
            syncFirebase
        }
        launchSyncWorker()
        return syncResponse
    }
}

