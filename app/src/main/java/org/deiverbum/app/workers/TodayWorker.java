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
import org.deiverbum.app.data.wrappers.Crud;
import org.deiverbum.app.data.wrappers.SyncRequest;
import org.deiverbum.app.model.BibleHomilyJoin;
import org.deiverbum.app.model.Biblical;
import org.deiverbum.app.model.Homily;
import org.deiverbum.app.model.LHGospelCanticleJoin;
import org.deiverbum.app.model.LHHymnJoin;
import org.deiverbum.app.model.LHInvitatoryJoin;
import org.deiverbum.app.model.LiturgyHomilyJoin;
import org.deiverbum.app.model.MassReadingOLD;
import org.deiverbum.app.model.Saint;
import org.deiverbum.app.model.SaintLife;
import org.deiverbum.app.model.SyncStatus;
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
            Integer theDate = mTodayDao.findLastDate();
            loadToday(theDate);
            loadCrud();
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

    private void setSyncRequest() {
        List<SyncStatus> syncStatus = mTodayDao.getAllSyncStatus();
        syncStatus.add(new SyncStatus(LH_GOSPEL_CANTICLE));
        syncStatus.add(new SyncStatus(SYNC_LITURGY_HOMILY_JOIN));
        syncStatus.add(new SyncStatus(SYNC_MASS_READING));

        syncRequest = new SyncRequest();
        syncRequest.syncStatus = syncStatus;
        syncRequest.lastUpdate = mTodayDao.getLastUpdate();

        Integer lastLiturgia = mTodayDao.findLastLiturgia();
        syncRequest.liturgia = lastLiturgia;
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
                                //mTodayDao.syncUpdateAll(r.syncStatus);
                                mTodayDao.syncUpdate(r.lastUpdate);
                                if (r.crudSaint != null) {
                                    List<Saint> cs = r.crudSaint.cSaint;
                                    List<Saint> us = r.crudSaint.uSaint;
                                    List<Saint> ds = r.crudSaint.dSaint;
                                    List<SaintLife> cl = r.crudSaint.cLife;
                                    List<SaintLife> ul = r.crudSaint.uLife;
                                    List<SaintLife> dl = r.crudSaint.dLife;

                                    if (cs != null && !cs.isEmpty()) {
                                        mTodayDao.saintInsertAll(cs);
                                    }
                                    if (us != null && !us.isEmpty()) {
                                        mTodayDao.saintUpdateAll(us);
                                    }
                                    if (ds != null && !ds.isEmpty()) {
                                        mTodayDao.saintDeleteAll(ds);
                                    }

                                    if (cl != null && !cl.isEmpty()) {
                                        mTodayDao.saintLifeInsertAll(cl);
                                    }
                                    if (ul != null && !ul.isEmpty()) {
                                        mTodayDao.saintLifeUpdateAll(ul);
                                    }
                                    if (dl != null && !dl.isEmpty()) {
                                        mTodayDao.saintLifeDeleteAll(dl);
                                    }
                                }

                                if (r.crudLHInvitatoryJoin != null) {
                                    List<LHInvitatoryJoin> c = r.crudLHInvitatoryJoin.c;
                                    List<LHInvitatoryJoin> u = r.crudLHInvitatoryJoin.u;
                                    List<LHInvitatoryJoin> d = r.crudLHInvitatoryJoin.d;

                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.lhInvitatoryJoinInsertAll(c);
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        mTodayDao.lhInvitatoryJoinUpdateAll(u);
                                    }
                                    if (d != null && !d.isEmpty()) {
                                        mTodayDao.lhInvitatoryJoinDeleteAll(d);
                                    }
                                }

                                if (r.crudLHHymnJoin != null) {
                                    List<LHHymnJoin> c = r.crudLHHymnJoin.c;
                                    List<LHHymnJoin> u = r.crudLHHymnJoin.u;
                                    List<LHHymnJoin> d = r.crudLHHymnJoin.d;

                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.lhHymnJoinInsertAll(c);
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        mTodayDao.lhHymnJoinUpdateAll(u);
                                    }
                                    if (d != null && !d.isEmpty()) {
                                        mTodayDao.lhHymnJoinDeleteAll(d);
                                    }
                                }

                                if (r.crudBibleReading != null) {
                                    List<Biblical> c = r.crudBibleReading.c;
                                    List<Biblical> u = r.crudBibleReading.u;
                                    List<Biblical> d = r.crudBibleReading.d;

                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.bibleReadingInsertAll(c);
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        mTodayDao.bibleReadingUpdateAll(u);
                                    }
                                    if (d != null && !d.isEmpty()) {
                                        mTodayDao.bibleReadingDeleteAll(d);
                                    }
                                }

                                if (r.crudHomily != null) {
                                    List<Homily> c = r.crudHomily.c;
                                    List<Homily> u = r.crudHomily.u;
                                    List<Homily> d = r.crudHomily.d;
                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.homilyInsertAll(c);
                                    }
                                    if (d != null && !d.isEmpty()) {
                                        mTodayDao.homilyDeleteAll(d);
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        mTodayDao.homilyUpdateAll(u);
                                    }
                                }


                                if (r.homilyJoin != null) {
                                    List<LiturgyHomilyJoin> c = r.homilyJoin.c;
                                    List<LiturgyHomilyJoin> u = r.homilyJoin.u;
                                    List<LiturgyHomilyJoin> d = r.homilyJoin.d;
                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.homilyJoinInsertAll(c);
                                    }
                                    if (d != null && !d.isEmpty()) {
                                        mTodayDao.homilyJoinDeleteAll(d);
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        mTodayDao.homilyJoinUpdateAll(u);
                                    }
                                }

                                if (r.crudMassReading != null) {
                                    List<MassReadingOLD> c = r.crudMassReading.c;
                                    List<MassReadingOLD> u = r.crudMassReading.u;
                                    List<MassReadingOLD> d = r.crudMassReading.d;
                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.massReadingInsertAll(c);
                                    }
                                    if (d != null && !d.isEmpty()) {
                                        mTodayDao.massReadingDeleteAll(d);
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        mTodayDao.massReadingUpdateAll(u);
                                    }
                                }


                                if (r.crudBibleHomilyJoin != null) {
                                    List<BibleHomilyJoin> c = r.crudBibleHomilyJoin.c;
                                    List<BibleHomilyJoin> u = r.crudBibleHomilyJoin.u;
                                    List<BibleHomilyJoin> d = r.crudBibleHomilyJoin.d;
                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.bibleHomilyJoinInsertAll(c);
                                    }
                                    if (d != null && !d.isEmpty()) {
                                        mTodayDao.bibleHomilyJoinDeleteAll(d);
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        mTodayDao.bibleHomilyJoinUpdateAll(u);
                                    }
                                }
                                if (r.ce != null) {
                                    List<LHGospelCanticleJoin> c = r.ce.c;
                                    List<LHGospelCanticleJoin> u = r.ce.u;
                                    List<LHGospelCanticleJoin> d = r.ce.d;

                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.gospelCanticleInsertAll(c);
                                    }

                                    if (d != null && !d.isEmpty()) {
                                        mTodayDao.gospelCanticleDeleteAll(d);
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        mTodayDao.gospelCanticleUpdateAll(u);
                                    }
                                }



                                if (r.crudBibleHomilyJoin != null) {
                                    List<BibleHomilyJoin> c = r.crudBibleHomilyJoin.c;
                                    List<BibleHomilyJoin> u = r.crudBibleHomilyJoin.u;
                                    List<BibleHomilyJoin> d = r.crudBibleHomilyJoin.d;
                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.bibleHomilyJoinInsertAll(c);
                                    }
                                    if (d != null && !d.isEmpty()) {
                                        mTodayDao.bibleHomilyJoinDeleteAll(d);
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        mTodayDao.bibleHomilyJoinUpdateAll(u);
                                    }
                                }

                                if (r.crudToday != null) {
                                    List<Today> c = r.crudToday.c;
                                    List<Today> u = r.crudToday.u;
                                    List<Today> d = r.crudToday.d;

                                    if (c != null && !c.isEmpty()) {
                                        mTodayDao.todayInsertAll(c);
                                    }
                                    if (u != null && !u.isEmpty()) {
                                        mTodayDao.todayUpdateAll(u);
                                    }
                                    if (d != null && !d.isEmpty()) {
                                        mTodayDao.todayDeleteAll(d);
                                    }
                                }
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

