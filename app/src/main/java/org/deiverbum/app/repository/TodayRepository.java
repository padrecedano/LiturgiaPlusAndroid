package org.deiverbum.app.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Homily;
import org.deiverbum.app.model.LHPsalmody;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.workers.TodayWorker;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * <p>Repositorio de datos para el módulo Homilías.</p>
 * <p>Orden de búsqueda: </p>
 * <ul>
 *     <li>Firebase</li>
 *     <li>Api</li>
 * </ul>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

public class TodayRepository {
    ApiService apiService;
    private final FirebaseDataSource firebaseDataSource;
    private final MediatorLiveData<DataWrapper<Homily, CustomException>> mData = new MediatorLiveData<>();
    private LiveData<Today> mAllWords;
    private TodayDao mTodayDao;
    private Context mContext;
    private WorkManager mWorkManager;
    private LiveData<LHPsalmody> salmodiaLiveData;

    @Inject
    public TodayRepository(FirebaseDataSource firebaseDataSource,
                           ApiService apiService, TodayDao todayDao,
                           @ApplicationContext Context context) {
        this.firebaseDataSource = firebaseDataSource;
        this.apiService = apiService;
        this.mTodayDao = todayDao;
        this.mContext=context;
    }

    public void fetchData(String theDate) {
        Integer lastDate=mTodayDao.findLastDate();
//        Log.d("AXY-BD",ld.toString());
        this.mWorkManager=WorkManager.getInstance(mContext);

        // Create Network constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        Data inputData = new Data.Builder()
                .putInt("THE_DATE", 1)
                .build();

        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(TodayWorker.class, 15,
                        TimeUnit.MINUTES)
                        .addTag("TAG_SYNC_DATA")
                        .setConstraints(constraints)
                        .setInputData(inputData)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        mWorkManager.enqueueUniquePeriodicWork(
                "SYNC_DATA_WORK_NAME",
                ExistingPeriodicWorkPolicy.REPLACE, //Existing Periodic Work
                // policy
                periodicSyncDataWork //work request
        );




    }



}

