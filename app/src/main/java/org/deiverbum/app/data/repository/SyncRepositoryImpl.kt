package org.deiverbum.app.data.repository

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import org.deiverbum.app.data.factory.SyncFactory
import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.domain.repository.SyncRepository
import org.deiverbum.app.model.SyncStatus
import org.deiverbum.app.model.Today
import org.deiverbum.app.util.Source
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.workers.TodayWorker
import timber.log.Timber
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

        // Create Network constraint
        val constraints= Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicSyncDataWork = PeriodicWorkRequest.Builder(TodayWorker::class.java, Constants.SYNC_INTERVAL_DAYS.toLong(), TimeUnit.DAYS)
                .addTag("TAG_SYNC_DATA")
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

    fun initialSync() {
        /*apiService.getTodayAll("all")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<Today?>?>() {
                public override fun onStart() {}
                override fun onSuccess(r: List<Today?>) {
                    try {
                        if (r.size > 0) {
                            mTodayDao.insertAllTodays(r)
                            launchSyncWorker()
                            initialSync.setValue(1)
                        } else {
                            //fromFirebase
                        }
                    } catch (e: Exception) {
                        //fromFirebase
                    }
                }

                override fun onError(e: Throwable) {
                    //fromFirebase
                }
            })*/
    }

    /**
     *
     * Este método buscará las fechas de los próximos siete días
     * en Firestore mediante [FirebaseDataSource.loadInitialToday]
     *
     * Se usa como alternativa, en caso de que falle la llamada a [SyncRepository.initialSync]
     */
    /*val fromFirebase: Unit
        get() {
            mFirebase.loadInitialToday()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :
                    DisposableSingleObserver<List<DataWrapper<Today?, CustomException?>?>?>() {
                    override fun onSuccess(data: List<DataWrapper<Today?, CustomException?>>) {
                        val allToday: MutableList<Today?> = ArrayList()
                        for (item in data) {
                            allToday.add(item.data)
                        }
                        try {
                            mTodayDao.insertAllTodays(allToday)
                            initialSync.setValue(0)
                            launchSyncWorker()
                        } catch (e: Exception) {
                            initialSync.setValue(0)
                            launchSyncWorker()
                        }
                    }

                    override fun onError(e: Throwable) {
                        initialSync.setValue(0)
                        launchSyncWorker()
                    }
                })
        }*/
    val initialSyncStatus: LiveData<Int>
        get() = initialSync

    fun getYearClean(): LiveData<Int> {
        return yearClean
    }

    fun launchCleanUp(lastYear: Int) {
        //mTodayDao.deleteOldEntries(lastYear)
        yearClean.value = 1
    }

     suspend fun getSyncc(syncRequest: SyncRequest): SpannableStringBuilder {
        Timber.d("getSync desde SyncRepositoryImpl")
        return SpannableStringBuilder()
    }

    /**
     *
     * <p>
     *     Este método buscará las fechas disponibles en el servidor remoto.
     *     Si hubiera algún fallo, buscará provisionalmente las fechas
     *     de la presente semana en Firebase. En este caso, el módulo de sincronización
     *     e intentará en cuanto sea posible una sincronización completa
     *     con los datos del servidor remoto.
     *     En cualquier caso, se insertarán en la tabla Today de la base de datos
     *     las fechas que se hayan encontrado, sin importar la fuente.
     * </p>
     *
     */
    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        var syncResponse = SyncResponse(SpannableStringBuilder(), emptyList())
        if(syncRequest.type==1) {
            val syncNetwork = syncFactory.create(Source.NETWORK).getSync(syncRequest)
            if(syncNetwork.allToday.isNotEmpty()) {
                syncResponse.allToday=syncNetwork.allToday
                syncResponse.dataForView=syncNetwork.dataForView
                syncResponse.status=1
            }else{
                val syncFirebase = syncFactory.create(Source.FIREBASE).getSync(syncRequest)
                syncResponse.allToday=syncFirebase.allToday
                syncResponse.dataForView=syncFirebase.dataForView
                syncResponse.status=2
            }
        }
        syncFactory.create(Source.LOCAL).addSync(syncResponse)

        return syncResponse
        //Timber.d(local.dataForView.toString())
        //val r = syncFactory.create(Source.FIREBASE).getSync(syncRequest)
        //return if(r.dataForView.isNullOrEmpty()) syncSync(syncRequest)
        //else r
        // . dataForView { syncSync(syncRequest) }
    }
/*
    private suspend fun syncPraySchedule(prayScheduleRequest: SyncRequest): List<Today> {
        return syncFactory.create(Source.NETWORK).getSync(prayScheduleRequest)
            .also { prayScheduleFromNetwork ->
                syncFactory.create(Source.LOCAL).addSync(prayScheduleFromNetwork)
            }
    }*/


    private suspend fun syncSync(syncRequest: SyncRequest): SyncResponse {
        return syncFactory.create(Source.NETWORK).getSync(syncRequest)
           // .also { syncFromNetwork ->
           //     syncFactory.create(Source.LOCAL).addSync(syncFromNetwork)
            }
    }

