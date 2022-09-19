package org.deiverbum.app.workers;

import static org.deiverbum.app.utils.Constants.DB_VERSION;
import static org.deiverbum.app.utils.Constants.HOMILY;
import static org.deiverbum.app.utils.Constants.LH_GOSPEL_CANTICLE;
import static org.deiverbum.app.utils.Constants.LITURGY_HOMILY_JOIN;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.Crud;
import org.deiverbum.app.data.wrappers.SyncRequest;
import org.deiverbum.app.model.LHCanticoEvangelico;
import org.deiverbum.app.model.Liturgia;
import org.deiverbum.app.model.LiturgiaHomiliaJoin;
import org.deiverbum.app.model.SyncStatus;
import org.deiverbum.app.model.Today;

import java.util.ArrayList;
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
 * @since 2022.2
 */

@HiltWorker
public class TodayWorker<lhGospelCanticle> extends Worker {

    private final ApiService workerDependency;
    private final TodayDao mTodayDao;
    private final Context context;
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
        this.context = context;
    }


    @NonNull
    @Override
    public Result doWork() {

        try {
            setSyncRequest();
            Integer theDate = mTodayDao.findLastDate();//getInputData().getInt("THE_DATE",0);
            HashMap<String, Integer> map = new HashMap<>();
            //map.put("liturgia", mTodayDao.findLastLiturgia());
            //map.put("homilia", mTodayDao.findLastHomilia());
            //loadCrud(map);

            //loadToday(theDate);
            loadInsert();
            //loadUpdate();
            //loadDelete();

            return Result.success();
        } catch (Throwable e) {
            //e.printStackTrace();
            return Result.failure();
        }
    }


    @Override
    public void onStopped() {
        super.onStopped();
    }

    private void setSyncRequest(){
        List<SyncStatus> syncStatus = mTodayDao.getAllSyncStatus();
        syncStatus.add(new SyncStatus(LH_GOSPEL_CANTICLE));

        //syncStatus.add(new SyncStatus(HOMILY,mTodayDao.getLastVersion(HOMILY)));

        /*
        syncStatus.add(new SyncStatus("bible_reading",1));
        syncStatus.add(new SyncStatus("mass_reading",1));
        syncStatus.add(new SyncStatus(LH_GOSPEL_CANTICLE,mTodayDao.getLastVersion(LH_GOSPEL_CANTICLE)));
        syncStatus.add(new SyncStatus(LITURGY_HOMILY_JOIN,mTodayDao.getLastVersion(LITURGY_HOMILY_JOIN)));
*/
        syncRequest=new SyncRequest();
        syncRequest.syncStatus=syncStatus;

        Integer lastLiturgia = mTodayDao.findLastLiturgia();
        syncRequest.liturgia=lastLiturgia;
        syncRequest.massReading=1;
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
                        try {
                            if (r.getAction().equals("c")) {
                                if (r.liturgia != null) {
                                    mTodayDao.liturgiaInsertAll(r.liturgia);
                                }
                                if (r.homily != null) {
                                    mTodayDao.homiliaInsertAll(r.homily);
                                }
                                if (r.liturgiaHomiliaJoin != null) {
                                    mTodayDao.homiliaJoinInsertAll(r.liturgiaHomiliaJoin);
                                }
                                if (r.ce != null) {
                                    //mTodayDao.canticoEvangelicoInsertAll(r.ce);
                                }
                                if (r.bvJoin != null) {
                                    mTodayDao.biblicaBreveJoinInsertAll(r.bvJoin);
                                }
                                if (r.bibleReading != null) {
                                    //mTodayDao.bibleReadingInsertAll(r.bibleReading);
                                }

                            }
                        }catch (Exception e){
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
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
                        try {
                            if (r.getAction().equals("c")) {
                                if (r.liturgia != null) {
                                    mTodayDao.liturgiaInsertAll(r.liturgia);
                                }
                                if (r.homily != null) {
                                    mTodayDao.homiliaInsertAll(r.homily);
                                }
                                if (r.liturgiaHomiliaJoin != null) {
                                    mTodayDao.homiliaJoinInsertAll(r.liturgiaHomiliaJoin);
                                }
                                if (r.ce != null) {
                                    //mTodayDao.canticoEvangelicoInsertAll(r.ce);
                                }
                                if (r.bvJoin != null) {
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
                                if (r.mass_reading != null) {
                                    mTodayDao.misaLecturaUpdateAll(r.mass_reading);
                                }
                                if (r.saintLife != null) {
                                    mTodayDao.saintLifeInsertAll(r.saintLife);
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


        //Integer lastLiturgia = mTodayDao.findLastLiturgia();
        //HashMap<String, Integer> map = new HashMap<>();
        //map.put("liturgia", lastLiturgia);
        //map.put("homilia", mTodayDao.findLastHomilia());
        //map.put("dbVersion",DB_VERSION);
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
                            if(r.haveData) {
                                //mTodayDao.syncUpdateAll(r.syncStatus);
                                if (r.liturgia != null) {
                                    mTodayDao.liturgiaInsertAll(r.liturgia);
                                }
                                if (r.homily != null && !r.homily.isEmpty()) {
                                    mTodayDao.homiliaInsertAll(r.homily);
                                    //mTodayDao.syncUpdate(HOMILY);
                                }
                                if (r.homilyJoin != null) {
                                    List<LiturgiaHomiliaJoin> c=r.homilyJoin.c;
                                    List<LiturgiaHomiliaJoin> u=r.homilyJoin.u;
                                    List<LiturgiaHomiliaJoin> d=r.homilyJoin.d;

                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.homiliaJoinInsertAll(c);
                                    }

                                    if (d != null && !d.isEmpty()) {
                                        Integer i=mTodayDao.homiliaJoinDeleteAll(d);
                                        Log.d("ACGDELETE", String.valueOf(i));
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        mTodayDao.homiliaJoinUpdateAll(u);
                                        //Log.d("ACGDELETE", String.valueOf(i));
                                    }
                                    //mTodayDao.syncUpdate(LITURGY_HOMILY_JOIN);
                                }
                                if (r.ce != null) {
                                    List<LHCanticoEvangelico> c=r.ce.c;
                                    List<LHCanticoEvangelico> u=r.ce.u;
                                    List<LHCanticoEvangelico> d=r.ce.d;

                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.canticoEvangelicoInsertAll(c);
                                    }

                                    if (d != null && !d.isEmpty()) {
                                        //Integer i=mTodayDao.homiliaJoinDeleteAll(d);
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        //mTodayDao.homiliaJoinUpdateAll(u);
                                        //Log.d("ACGDELETE", String.valueOf(i));
                                    }
                                    //mTodayDao.canticoEvangelicoInsertAll(r.ce);
                                    //mTodayDao.syncUpdate(LH_GOSPEL_CANTICLE);
                                }
                                if (r.bvJoin != null) {
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
                                if ((r.mass_reading != null) && !r.mass_reading.isEmpty()) {
                                    mTodayDao.misaLecturaInsertAll(r.mass_reading);
                                }

                                if (r.saintLife != null) {
                                    mTodayDao.saintLifeInsertAll(r.saintLife);
                                }

                                if (r.updateList != null && !r.updateList.isEmpty()) {
                                    mTodayDao.syncUpdateAll(r.updateList);
                                    //mTodayDao.syncUpdate(LH_GOSPEL_CANTICLE);
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

    public void loadDelete() {
        workerDependency.callDelete(syncRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Crud>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(Crud r) {
                        try {
                            if(r.haveData) {
                                mTodayDao.syncUpdateAll(r.syncStatus);
                                if (r.liturgia != null) {
                                    mTodayDao.liturgiaInsertAll(r.liturgia);
                                }
                                if (r.homily != null && !r.homily.isEmpty()) {
                                    mTodayDao.homiliaInsertAll(r.homily);
                                    //mTodayDao.syncUpdate(HOMILY);
                                }
                                /*if (r.homilyJoin != null && !r.homilyJoin.isEmpty()) {
                                    mTodayDao.homiliaJoinDeleteAll(r.homilyJoin);
                                    //mTodayDao.syncUpdate(LITURGY_HOMILY_JOIN);
                                }*/
                                if (r.ce != null ) {
                                    //mTodayDao.canticoEvangelicoInsertAll(r.ce);
                                    //mTodayDao.syncUpdate(LH_GOSPEL_CANTICLE);
                                }
                                if (r.bvJoin != null) {
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
                                if ((r.mass_reading != null) && !r.mass_reading.isEmpty()) {
                                    mTodayDao.misaLecturaInsertAll(r.mass_reading);
                                }

                                if (r.saintLife != null) {
                                    mTodayDao.saintLifeInsertAll(r.saintLife);
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
                            if(r!=null && !r.isEmpty()) {
                                mTodayDao.insertAllTodays(r);
                            }
                        }catch (Exception e){
                            //loadCrud();
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

