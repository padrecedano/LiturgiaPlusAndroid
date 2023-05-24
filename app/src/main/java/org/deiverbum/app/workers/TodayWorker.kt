package org.deiverbum.app.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.model.crud.Crud;

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
    ApiService apiService;

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
            loadCrud();
            return Result.success();
        } catch (Throwable e) {
            return Result.failure();
        }
    }

    public void loadCrud() {
        apiService.postCrud(mTodayDao.getAllSyncStatus())
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
                                mTodayDao.syncUpdate(r.lastUpdate);
                            }
                        } catch (Exception e) {
                            Log.d("ERR", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ERR", e.getMessage() == null ? "" : e.getMessage());
                    }
                });
    }
}

