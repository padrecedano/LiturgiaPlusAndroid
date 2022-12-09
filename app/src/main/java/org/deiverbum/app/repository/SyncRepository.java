package org.deiverbum.app.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.workers.TodayWorker;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * <p>Repositorio para el módulo de Sincronización.</p>
 * <p>Busca en la base de datos la última fecha disponible en el calendario y la fecha de la última sincronización.</p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

public class SyncRepository {
    final ApiService apiService;
    private final LiveData<SyncStatus> mData;
    final Context context;

    @Inject
    public SyncRepository(
            ApiService apiService,
            TodayDao todayDao, @ApplicationContext Context context) {
        this.apiService = apiService;
        this.mData= todayDao.getSyncInfo();
        this.context = context;
    }

    public LiveData<SyncStatus> getFromDB() {
        return mData;
    }

    public void launchWorker() {
        WorkManager mWorkManager = WorkManager.getInstance(this.context);

        // Create Network constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(TodayWorker.class, 15, TimeUnit.MINUTES)
                        .addTag("TAG_SYNC_DATA")
                        .setConstraints(constraints)
                        //.setInputData(inputData)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        mWorkManager.enqueueUniquePeriodicWork(
                "SYNC_TODAY",
                ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work
                // policy
                periodicSyncDataWork //work request
        );

    }

}