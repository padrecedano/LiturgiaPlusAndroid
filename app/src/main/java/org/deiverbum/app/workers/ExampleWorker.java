package org.deiverbum.app.workers;

import static org.deiverbum.app.utils.Constants.NOTFOUND_OR_NOTCONNECTION;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Homilias;
import org.deiverbum.app.model.Today;

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
public class ExampleWorker extends Worker {

    private final ApiService workerDependency;
    private final TodayDao mTodayDao;


    @AssistedInject
    public ExampleWorker(
            @Assisted @NonNull Context context,
            @Assisted @NonNull WorkerParameters params,
            ApiService workerDependency,
            TodayDao mTodayDao
    ) {
        super(context, params);
        this.workerDependency = workerDependency;
        this.mTodayDao=mTodayDao;
    }

    //@NonNull
    //@Override
    public Result doWorks() {
        //return null;
        return Result.success();
    }

    @NonNull
    @Override
    public  Result doWork() {

        Context applicationContext = getApplicationContext();
        //simulate slow work
        // WorkerUtils.makeStatusNotification("Fetching Data", applicationContext);
        Log.d("TAGs", "Fetching Data from Remote host");
        //WorkerUtils.sleep();

        try {
            //create a call to network
            //Call<List<Book>> call = bookService.fetchBooks();
            //Response<List<Book>> response = call.execute();
            Integer theDate = getInputData().getInt("THE_DATE",0);
                loadFromApi(theDate);

                return Result.success();



        } catch (Throwable e) {
            e.printStackTrace();
            // Technically WorkManager will return Result.failure()
            // but it's best to be explicit about it.
            // Thus if there were errors, we're return FAILURE
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
        workerDependency.getToday("20220302")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Today>>() {

                    @Override public void onStart() {
                    }
                    @Override
                    public void onSuccess(List<Today> r) {
                        //mData.postValue(new DataWrapper<>(r));
                        Log.d("AXY-r",r.get(0).getHoy().toString());
                        //mTodayDao.insertToday(r.get(0));
                        mTodayDao.insertTodays(r);


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AXY-error",e.toString());

                        //mData.setValue(new DataWrapper<>(new
                        // CustomException(NOTFOUND_OR_NOTCONNECTION)));
                    }
                });

        //return mData;
    }

}

