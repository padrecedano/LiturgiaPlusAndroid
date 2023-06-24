package org.deiverbum.app.repository;

import static org.deiverbum.app.util.Constants.SYNC_INTERVAL_DAYS;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.workers.TodayWorker;

import java.util.ArrayList;
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
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

public class SyncRepository {
    private final ApiService apiService;
    private final FirebaseDataSource mFirebase;
    private final Context context;
    private final TodayDao mTodayDao;
    private final MutableLiveData<Integer> initialSync = new MutableLiveData<>();
    private final MutableLiveData<Integer> yearClean = new MutableLiveData<>();

    @Inject
    public SyncRepository(
            FirebaseDataSource mFirebase,
            ApiService apiService,
            TodayDao todayDao, @ApplicationContext Context context) {
        this.apiService = apiService;
        this.mTodayDao = todayDao;
        this.mFirebase = mFirebase;
        this.context = context;
    }

    public LiveData<SyncStatus> getFromDB() {

        if (mTodayDao.syncStatusCount() == 0) {
            mTodayDao.insertSyncStatus("initial");
        }
        return mTodayDao.syncInfo();
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
                new PeriodicWorkRequest.Builder(TodayWorker.class, SYNC_INTERVAL_DAYS, TimeUnit.DAYS)
                        .addTag("TAG_SYNC_DATA")
                        .setConstraints(constraints)
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        mWorkManager.enqueueUniquePeriodicWork(
                "SYNC_TODAY",
                ExistingPeriodicWorkPolicy.UPDATE,
                periodicSyncDataWork
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
                            if (r.size() > 0) {
                                mTodayDao.insertAllTodays(r);
                                launchSyncWorker();
                                initialSync.setValue(1);
                            } else {
                                getFromFirebase();
                            }
                        } catch (Exception e) {
                            getFromFirebase();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getFromFirebase();
                    }
                });
    }


    /**
     * <p>Este método buscará las fechas de los próximos siete días
     * en Firestore mediante {@link FirebaseDataSource#loadInitialToday()}</p>
     * <p>Se usa como alternativa, en caso de que falle la llamada a {@link SyncRepository#initialSync()}</p>
     */

    public void getFromFirebase() {
        mFirebase.loadInitialToday()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<DataWrapper<Today, CustomException>>>() {

                    @Override
                    public void onSuccess(@NonNull List<DataWrapper<Today, CustomException>> data) {
                        List<Today> allToday = new ArrayList<>();
                        for (DataWrapper<Today, CustomException> item : data) {
                            allToday.add(item.getData());
                        }
                        try {
                            mTodayDao.insertAllTodays(allToday);
                            initialSync.setValue(0);
                            launchSyncWorker();
                        } catch (Exception e) {
                            initialSync.setValue(0);
                            launchSyncWorker();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        initialSync.setValue(0);
                        launchSyncWorker();
                    }
                });
    }

    public LiveData<Integer> getInitialSyncStatus() {
        return initialSync;
    }

    public LiveData<Integer> getYearClean() {
        return yearClean;
    }

    public void launchCleanUp(int lastYear) {
        mTodayDao.deleteOldEntries(lastYear);
        yearClean.setValue(1);
    }
}