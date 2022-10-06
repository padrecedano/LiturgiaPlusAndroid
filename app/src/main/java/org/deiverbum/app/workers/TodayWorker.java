package org.deiverbum.app.workers;

import static org.deiverbum.app.utils.Constants.LH_GOSPEL_CANTICLE;
import static org.deiverbum.app.utils.Constants.SYNC_LITURGY_HOMILY_JOIN;
import static org.deiverbum.app.utils.Constants.SYNC_MASS_READING;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.SyncRequest;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.model.crud.Crud;

import java.util.List;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@HiltWorker
public class TodayWorker extends Worker {

    private final ApiService workerDependency;
    private final TodayDao mTodayDao;
    private SyncRequest syncRequest;

    @AssistedInject
    public TodayWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params,
            ApiService workerDependency,
            TodayDao mTodayDao

            ) {
        super(context, params);
        this.workerDependency = workerDependency;
        this.mTodayDao = mTodayDao;

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
        List<SyncStatus> syncStatus = mTodayDao.getAllSyncStatus();
        syncStatus.add(new SyncStatus(LH_GOSPEL_CANTICLE));
        syncStatus.add(new SyncStatus(SYNC_LITURGY_HOMILY_JOIN));
        syncStatus.add(new SyncStatus(SYNC_MASS_READING));

        syncRequest = new SyncRequest();
        syncRequest.syncStatus = syncStatus;
        syncRequest.lastUpdate = mTodayDao.getLastUpdate();

        syncRequest.liturgia = mTodayDao.findLastLiturgia();
        syncRequest.massReading = 1;
    }

    public void loadCrud() {
        workerDependency.callInsertB(syncRequest)
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

    public void loadToday(Integer param) {
        String theDate = String.valueOf(param);
        workerDependency.getToday(theDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Today>>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(List<Today> r) {
                        try {
                            if (r != null && !r.isEmpty()) {
                                mTodayDao.todayInsertAll(r);
                            }
                        } catch (Exception e) {
                            Log.e("ERR", e.getMessage());
                            loadCrud();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("ERR", e.getMessage());
                    }
                });

    }

}

