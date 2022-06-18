package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.NOTFOUND_OR_NOTCONNECTION;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.entity.SalmoEntity;
import org.deiverbum.app.data.entity.SalmodiaWithSalmos;
import org.deiverbum.app.data.entity.mapper.OficioDataMapper;
import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Homilias;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Salmodia;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.utils.Utils;
import org.deiverbum.app.workers.ExampleWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
    private final MediatorLiveData<DataWrapper<Homilias, CustomException>> mData = new MediatorLiveData<>();
    private LiveData<Today> mAllWords;
    private TodayDao mTodayDao;
    private Context mContext;
    private WorkManager mWorkManager;
    private OficioDataMapper mMapper;
    private LiveData<Salmodia> salmodiaLiveData;

    @Inject
    public TodayRepository(FirebaseDataSource firebaseDataSource,
                           ApiService apiService, TodayDao todayDao,
                           OficioDataMapper mapper,
                           @ApplicationContext Context context) {
        this.firebaseDataSource = firebaseDataSource;
        this.apiService = apiService;
        this.mTodayDao = todayDao;
        this.mContext=context;
        this.mMapper=mapper;
    }

    public void fetchData(String theDate) {
//        Integer ld=mTodayDao.findLastDate();
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
                new PeriodicWorkRequest.Builder(ExampleWorker.class, 15,
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

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante {@link FirebaseDataSource#getHomilias(String)}
     * y si no encuentra, buscará en la Api mediante {@link ApiService#getHomilias(String)}
     * La llamada a la Api se hará desde el onError
     * @param param El parámetro a buscar, en principio la fecha, quizá también un Id
     * @return En MediatorLiveData con los datos obtenidos de cualquiera de las fuentes
     */
    public MediatorLiveData<DataWrapper<Homilias,CustomException>> getData(String param) {
        firebaseDataSource.getHomilias(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Homilias,CustomException>>() {

                    @Override
                    public void onSuccess(DataWrapper<Homilias,CustomException> data) {
                        mData.postValue(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadFromApi(Utils.cleanDate(param));
                    }
                });
        return mData;
    }

    public LiveData<Today> getFavoritePokemon(String theDate){
        return mTodayDao.findByDate(Integer.parseInt(theDate));
    }
/*
    public LiveData<Map<Integer, List<Integer>>> getSalmodiaB(String theDate){
        return mTodayDao.loadUserAndBookNames();
    }
*/
    public LiveData<List<SalmodiaWithSalmos>> getSalmos(String theDate){
        return null;//mTodayDao.getSalmos();
    }
/*
    public LiveData<List<SalmosWithSalmodia>> getSalmodia(String theDate){
        return mTodayDao.getJoin();
    }
*/

    public LiveData<Integer> getLastLive(){
        return null;//mTodayDao.findLastLive();
    }

    LiveData<Today> getAllWords() {
        return (LiveData<Today>) mTodayDao;
    }
    public void loadFromApi(String param) {
        apiService.getHomilias(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Homilias>() {

                    @Override public void onStart() {
                    }
                    @Override
                    public void onSuccess(Homilias r) {
                        mData.postValue(new DataWrapper<>(r));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mData.setValue(new DataWrapper<>(new CustomException(NOTFOUND_OR_NOTCONNECTION)));
                    }
                });

        //return mData;
    }


/*
    public LiveData<TodayWithOficio> getTodayWithOficio(String s) {
        return mTodayDao.getTodayWithOficio(Integer.valueOf(s));
    }
*/


    public LiveData<Oficio> getOficio(String s) {
        return mMapper.transformOficio(mTodayDao.getOficioOfToday(Integer.valueOf(s)));
    }

    public LiveData<Laudes> mappedLaudes(String s) {
        return mMapper.transformLaudes(mTodayDao.getLaudesOfToday(Integer.valueOf(s)));
    }

    public LiveData<Intermedia> getTercia(String s) {
        MutableLiveData<Intermedia> ss=
                mMapper.transformTercia(mTodayDao.geTerciaOfToday(Integer.valueOf(s)));
        return ss;//mMapper.transform(mTodayDao.getUsersWithPlaylistsAndSongs
        // ());
    }



    public LiveData<Mixto> getMixto(String s) {
        MutableLiveData<Mixto> ss=
                mMapper.transformMixto(mTodayDao.getMixtoOfToday(Integer.valueOf(s)));
        return ss;//mMapper.transform(mTodayDao.getUsersWithPlaylistsAndSongs
        // ());
    }

/*
    public LiveData<Salmodia> getS(String s) {
        MutableLiveData<Salmodia> ss=
                mMapper.transform(mTodayDao.getSalmod());
        return ss;//mMapper.transform(mTodayDao.getUsersWithPlaylistsAndSongs
        // ());
    }
*/
/*
    public LiveData<Map<Salmodia, List<Salmo>>> loadUserAndBookNames() {
        return mTodayDao.loadUserAndBookNames();
    }

 */
    public LiveData<SalmoEntity> getSalmo() {
        return null;//mTodayDao.getSalmo();
    }
}

