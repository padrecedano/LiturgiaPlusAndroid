package org.deiverbum.app.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.model.crud.Crud;
import org.deiverbum.app.utils.Utils;

import javax.inject.Inject;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@HiltWorker
public class TodayWorker extends Worker {

    @Inject
    ApiService workerDependency;

    @Inject
    TodayDao mTodayDao;

    @AssistedInject
    public TodayWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params

    ) {
        super(context, params);


    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            setSyncRequest();
            loadCrud();
            //getFromFirebase(theDate);
            return Result.success();
        } catch (Throwable e) {
            //e.printStackTrace();
            return Result.failure();
        }
    }



    private void setSyncRequest() {
        //List<SyncStatus> syncStatus = mTodayDao.getAllSyncStatus();
    }

    public void loadCrud() {
        String lastUpdatee= mTodayDao.getAllSyncStatus().lastUpdate;

        String lastUpdate= Utils.formatDate(mTodayDao.getAllSyncStatus().lastUpdate);
        SyncStatus ss = mTodayDao.getAllSyncStatus();
        workerDependency.callSyncStatuss(mTodayDao.getAllSyncStatus())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Crud>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(Crud r) {
                        try {
                            if (r.haveData) {
                                r.doCrud(mTodayDao);
                                //mTodayDao.syncUpdateAll(r.syncStatus);
                                mTodayDao.syncUpdate(r.lastUpdate);
                            }
                        } catch (Exception e) {
                            Log.e("ERR", e.getMessage());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERR", e.getMessage());

                    }
                });
    }


}

