package org.deiverbum.app.workers;

import static org.deiverbum.app.utils.Constants.DB_VERSION;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.Crud;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.Today;

import java.util.HashMap;
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
public class TodayWorker extends Worker {

    private final ApiService workerDependency;
    private final TodayDao mTodayDao;
    private final Context context;


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
        this.context = context;
    }


    @NonNull
    @Override
    public Result doWork() {

        try {
            Integer theDate = mTodayDao.findLastDate();//getInputData().getInt("THE_DATE",0);
            HashMap<String, Integer> map = new HashMap<>();
            map.put("liturgia", mTodayDao.findLastLiturgia());
            map.put("homilia", mTodayDao.findLastHomilia());

            //loadCrud(map);
            loadFromApi(theDate);
            loadInsert();

            loadUpdate();
            return Result.success();
        } catch (Throwable e) {
            e.printStackTrace();
            return Result.failure();
        }
    }


    @Override
    public void onStopped() {
        super.onStopped();
    }


    public void loadCrud() {
        Integer lastLiturgia = mTodayDao.findLastLiturgia();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("liturgia", lastLiturgia);
        map.put("homilia", mTodayDao.findLastHomilia());
        workerDependency.getCrud(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Crud>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(Crud r) {
                        //Log.d("CRUDX", String.valueOf(r.ce.get(0).grupoId));
                        try {
                            if (r.getAction().equals("c")) {
                                if (r.liturgia != null) {
                                    mTodayDao.liturgiaInsertAll(r.liturgia);
                                }
                                if (r.homilia != null) {
                                    mTodayDao.homiliaInsertAll(r.homilia);
                                }
                                if (r.liturgiaHomiliaJoin != null) {
                                    mTodayDao.homiliaJoinInsertAll(r.liturgiaHomiliaJoin);
                                }
                                if (r.ce != null) {
                                    mTodayDao.canticoEvangelicoInsertAll(r.ce);
                                }
                                if (r.bvJoin != null) {
                                    //Log.d("a", String.valueOf(r.bvJoin.get(0).responsorioFK));
                                    mTodayDao.biblicaBreveJoinInsertAll(r.bvJoin);
                                }
                                if (r.bibleReading != null) {
                                    Log.d("a", String.valueOf(r.bibleReading.get(0).getAllForRead()));
                                    //mTodayDao.bibleReadingInsertAll(r.bibleReading);
                                }

                            }
                        }catch (Exception e){
                            Log.e("ERR",e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERR",e.getMessage());

                    }
                });

        //return mData;
    }


    public void loadUpdate() {
        Integer lastLiturgia = mTodayDao.findLastLiturgia();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("liturgia", lastLiturgia);
        map.put("homilia", mTodayDao.findLastHomilia());
        map.put("dbVersion",DB_VERSION);
        workerDependency.callUpdate(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Crud>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(Crud r) {
                        //Log.d("CRUDX", String.valueOf(r.ce.get(0).grupoId));
                        try {
                            if (r.getAction().equals("c")) {
                                if (r.liturgia != null) {
                                    mTodayDao.liturgiaInsertAll(r.liturgia);
                                }
                                if (r.homilia != null) {
                                    mTodayDao.homiliaInsertAll(r.homilia);
                                }
                                if (r.liturgiaHomiliaJoin != null) {
                                    mTodayDao.homiliaJoinInsertAll(r.liturgiaHomiliaJoin);
                                }
                                if (r.ce != null) {
                                    mTodayDao.canticoEvangelicoInsertAll(r.ce);
                                }
                                if (r.bvJoin != null) {
                                    //Log.d("a", String.valueOf(r.bvJoin.get(0).responsorioFK));
                                    mTodayDao.biblicaBreveJoinInsertAll(r.bvJoin);
                                }
                                if (r.bibleReading != null) {
                                    mTodayDao.bibleReadingUpdateAll(r.bibleReading);
                                }
                                if (r.lhAntifona != null) {
                                    mTodayDao.lhAntifonaUpdateAll(r.lhAntifona);
                                }
                                if (r.today != null) {
                                    mTodayDao.todayUpdateAll(r.today);
                                }
                                if (r.misaLectura != null) {
                                    mTodayDao.misaLecturaUpdateAll(r.misaLectura);
                                }

                            }
                        }catch (Exception e){
                            Log.e("ERR",e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERR",e.getMessage());

                    }
                });
    }

    public void loadInsert() {
        Integer lastLiturgia = mTodayDao.findLastLiturgia();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("liturgia", lastLiturgia);
        map.put("homilia", mTodayDao.findLastHomilia());
        map.put("dbVersion",DB_VERSION);
        workerDependency.callUpdate(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Crud>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(Crud r) {
                        //Log.d("CRUDX", String.valueOf(r.ce.get(0).grupoId));
                        try {
                            if (r.getAction().equals("c")) {
                                if (r.liturgia != null) {
                                    mTodayDao.liturgiaInsertAll(r.liturgia);
                                }
                                if (r.homilia != null) {
                                    mTodayDao.homiliaInsertAll(r.homilia);
                                }
                                if (r.liturgiaHomiliaJoin != null) {
                                    mTodayDao.homiliaJoinInsertAll(r.liturgiaHomiliaJoin);
                                }
                                if (r.ce != null) {
                                    mTodayDao.canticoEvangelicoInsertAll(r.ce);
                                }
                                if (r.bvJoin != null) {
                                    //Log.d("a", String.valueOf(r.bvJoin.get(0).responsorioFK));
                                    mTodayDao.biblicaBreveJoinInsertAll(r.bvJoin);
                                }
                                if (r.bibleReading != null) {
                                    mTodayDao.bibleReadingUpdateAll(r.bibleReading);
                                }
                                if (r.lhAntifona != null) {
                                    mTodayDao.lhAntifonaUpdateAll(r.lhAntifona);
                                }
                                if (r.today != null) {
                                    mTodayDao.todayUpdateAll(r.today);
                                }
                                if (r.bibleReading != null) {
                                    mTodayDao.bibleReadingInsertAll(r.bibleReading);
                                }
                                if (r.misaLectura != null) {
                                    mTodayDao.misaLecturaInsertAll(r.misaLectura);
                                }
                            }
                        }catch (Exception e){
                            Log.e("ERR",e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERR",e.getMessage());

                    }
                });
    }

    public void loadFromApi(Integer param) {
        String theDate = String.valueOf(param);
        Log.d("AXY-API", theDate);
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
                            if(r!=null && r.size()>0) {
                                mTodayDao.insertAllTodays(r);
                            }
                        }catch (Exception e){
                            Log.e("ERR",e.getMessage());
                            loadCrud();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ERR",e.getMessage());
                    }
                });

    }

    public void loadLast(Integer lastId) {
        workerDependency.getLiturgia(lastId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Liturgia>>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(List<Liturgia> r) {
                        mTodayDao.liturgiaInsertAll(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

    }

}

