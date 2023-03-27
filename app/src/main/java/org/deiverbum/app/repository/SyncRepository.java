package org.deiverbum.app.repository;

import android.content.Context;
import android.util.Log;

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
import org.deiverbum.app.model.Today;
import org.deiverbum.app.workers.TodayWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * <p>Repositorio para el módulo de Sincronización.</p>
 * <p>Busca en la base de datos la última fecha disponible en el calendario y la fecha de la última sincronización.</p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

public class SyncRepository {
    private final ApiService apiService;
    private final LiveData<SyncStatus> mData;
    private final Context context;
    private final TodayDao mTodayDao;

    @Inject
    public SyncRepository(
            ApiService apiService,
            TodayDao todayDao, @ApplicationContext Context context) {
        this.apiService = apiService;
        this.mTodayDao = todayDao;

        this.mData= todayDao.getSyncInfo();
        this.context = context;
    }

    public LiveData<SyncStatus> getFromDB() {
        return mData;
    }

    /**
     * <p>Lanza el objeto  {@link TodayWorker}, encargado de monitorear/sincronizar
     * eventuales cambios en los datos remotos.</p>
     */
    public void launchSyncWorker() {
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

    public void initialSync() {
        apiService.getTodayAll("all")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Today>>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(List<Today> r) {
                        try {
                            if (r.size()>0) {
                                mTodayDao.insertAllTodays(r);
                            }
                            launchSyncWorker();
                        } catch (Exception e) {
                            Log.d("ERR", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d("ERR", e.getMessage());
                    }
                });
    }
}