package org.deiverbum.app.repository;

import android.content.Context;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.workers.TodayWorker;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * <p>Repositorio de datos para el módulo Home.</p>
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

public class HomeRepository {
    private final FirebaseDataSource firebaseDataSource;
    final Context context;

    @Inject
    public HomeRepository(FirebaseDataSource firebaseDataSource,@ApplicationContext Context context
    ) {
        this.firebaseDataSource = firebaseDataSource;
        this.context=context;
    }

    /**
     * Agrega un evento de escucha para sincronizar las fechas del calendario.
     * Los datos serán recibidos desde Firebase mediante {@link FirebaseDataSource#getSync()}
     */

    public void getFromFirebase() {
        firebaseDataSource.getSync();
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
                ExistingPeriodicWorkPolicy.UPDATE, //Existing Periodic Work
                // policy
                periodicSyncDataWork //work request
        );

    }

}