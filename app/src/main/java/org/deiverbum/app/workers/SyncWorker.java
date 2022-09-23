package org.deiverbum.app.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.model.Liturgy;

import java.util.List;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author A. Cedano
 * @version 1.0
 * @date 15/3/22
 * @since 2022.2
 */

@HiltWorker
public class SyncWorker extends Worker {

    private final ApiService workerDependency;
    private final TodayDao mTodayDao;
    private final Context context;


    @AssistedInject
    public SyncWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params,
            ApiService workerDependency,
            TodayDao mTodayDao
    ) {
        super(context, params);
        this.workerDependency = workerDependency;
        this.mTodayDao=mTodayDao;
        this.context = context;
    }


    @NonNull
    @Override
    public  Result doWork() {

        try {
            Integer lastId = mTodayDao.findLastLiturgia();//getInputData().getInt("THE_DATE",0);
                loadFromApi(lastId);
                return Result.success();
        } catch (Throwable e) {
            e.printStackTrace();
            Log.e("TAGs", "Error fetching data", e);
            return Result.failure();
        }
    }


    @Override
    public void onStopped() {
        super.onStopped();
        Log.d("TAGs", "OnStopped called for this worker");
    }

    public void loadFromApi(Integer param) {
        String theDate=String.valueOf(param);
        Log.d("AXY-API",theDate);
        workerDependency.getLiturgia(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Liturgy>>() {

                    @Override public void onStart() {
                    }
                    @Override
                    public void onSuccess(List<Liturgy> r) {
                            for (Liturgy item : r) {
                            Log.d("AXY-r", String.valueOf(item.getHoy()));
                            //}
                        }

                            //Log.d("AXY-r",r.get(0).getHoy().toString());
                        //Log.d("AXY-r",r.get(0).getTexto());
                        //DataWrapper<Homily, CustomException>

                        //mTodayDao.insertToday(r.get(0));
                        //mTodayDao.insertAllTodays(r);
                        //mTodayDao.insertAllHimnos(r);
                        //mTodayDao.insertAllTodaysTest(r);
                        //SharedPreferences sp =context.getSharedPreferences("preference_key", Context.MODE_PRIVATE);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AXY-error"+theDate,e.toString());
                        //mData.setValue(new DataWrapper<>(new
                        // CustomException(NOTFOUND_OR_NOTCONNECTION)));
                    }
                });

        //return mData;
    }

}

