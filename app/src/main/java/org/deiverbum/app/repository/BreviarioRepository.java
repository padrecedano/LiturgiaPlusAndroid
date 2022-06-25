package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.NOTFOUND_OR_NOTCONNECTION;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.entity.TodayLaudes;
import org.deiverbum.app.data.entity.TodayMixto;
import org.deiverbum.app.data.entity.TodayNona;
import org.deiverbum.app.data.entity.TodayOficio;
import org.deiverbum.app.data.entity.TodaySexta;
import org.deiverbum.app.data.entity.TodayTercia;
import org.deiverbum.app.data.entity.TodayVisperas;
import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Intermedia;
import org.deiverbum.app.model.Laudes;
import org.deiverbum.app.model.Mixto;
import org.deiverbum.app.model.Oficio;
import org.deiverbum.app.model.Visperas;
import org.deiverbum.app.utils.Utils;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Repositorio del módulo Breviario.
 * Mientras se migra el contenido a una base de datos local
 * este repositorio busca los datos en Firebase, y si no los encuentra
 * los busca en el servidor remoto.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.01.01
 */

public class BreviarioRepository {
    private final ApiService apiService;
    private final FirebaseDataSource firebaseDataSource;
    private final MediatorLiveData<DataWrapper<Oficio, CustomException>> liveData = new MediatorLiveData<>();
    private final MediatorLiveData<DataWrapper<Mixto, CustomException>> liveDataMixto = new MediatorLiveData<>();
    private final MediatorLiveData<DataWrapper<Laudes, CustomException>> liveDataLaudes = new MediatorLiveData<>();
    private final MediatorLiveData<DataWrapper<Intermedia, CustomException>> liveDataIntermedia = new MediatorLiveData<>();
    private final MediatorLiveData<DataWrapper<Visperas, CustomException>> liveDataVisperas = new MediatorLiveData<>();
    private final TodayDao mTodayDao;


    @Inject
    public BreviarioRepository(
            FirebaseDataSource firebaseDataSource,
            ApiService apiService,
            TodayDao todayDao

    ) {
        this.firebaseDataSource = firebaseDataSource;
        this.mTodayDao = todayDao;
        this.apiService = apiService;
    }


    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante {@link FirebaseDataSource#getOficio(String)}
     * y si no encuentra, buscará en la Api mediante {@link ApiService#getBreviario(String, String)}
     * La llamada a la Api se hará desde el onError
     *
     * @param dateString La fecha
     */
    public void getOficio(String dateString) {
        firebaseDataSource.getOficio(dateString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Oficio, CustomException>>() {

                    @Override
                    public void onSuccess(@NonNull DataWrapper<Oficio, CustomException> data) {
                        liveData.postValue(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        oficioFromApi(dateString);
                    }
                });
    }

    public MediatorLiveData<DataWrapper<Oficio, CustomException>> getOficioDB(String s) {
        TodayOficio theEntity = mTodayDao.getOficioOfToday(Integer.valueOf(s));
        if (theEntity != null) {
            liveData.postValue(new DataWrapper<>(theEntity.getDomainModel()));
        } else {
            getOficio(s);
        }
        return liveData;
    }

    public MediatorLiveData<DataWrapper<Laudes, CustomException>> getLaudesDB(String s) {
        TodayLaudes theEntity = mTodayDao.getLaudesOfToday(Integer.valueOf(s));
        if (theEntity != null) {
            Laudes theModel = theEntity.getDomainModel();
            liveDataLaudes.postValue(new DataWrapper<>(theModel));
        } else {
            getLaudes(s);
        }
        return liveDataLaudes;
    }

    public MediatorLiveData<DataWrapper<Intermedia, CustomException>> getIntermediaDB(String s, int hourId, String endPoint) {
        Intermedia theModel;
        if (hourId == 3) {
            TodayTercia theEntity = mTodayDao.geTerciaOfToday(Integer.valueOf(s));
            if (theEntity != null) {
                theModel = theEntity.getDomainModel();
                liveDataIntermedia.postValue(new DataWrapper<>(theModel));
            } else {
                getIntermedia(s, hourId, endPoint);
            }
            //return liveDataIntermedia;
        }else if(hourId==4){
            TodaySexta theEntity = mTodayDao.geSextaOfToday(Integer.valueOf(s));
            if (theEntity != null) {
                theModel = theEntity.getDomainModel();
                liveDataIntermedia.postValue(new DataWrapper<>(theModel));
            } else {
                getIntermedia(s, hourId, endPoint);
            }

        }else{
            TodayNona theEntity = mTodayDao.geNonaOfToday(Integer.valueOf(s));
            if (theEntity != null) {
                theModel = theEntity.getDomainModel();
                liveDataIntermedia.postValue(new DataWrapper<>(theModel));
            } else {
                getIntermedia(s, hourId, endPoint);
            }
        }
        return liveDataIntermedia;
    }





        /**
         * Este método inicia la llamada al DataSource.
         * Primero buscará en Firestore mediante {@link FirebaseDataSource#getOficio(String)}
         * y si no encuentra, buscará en la Api mediante {@link ApiService#getBreviario(String, String)}
         * La llamada a la Api se hará desde el onError
         *
         * @param dateString La fecha
         * @return En MediatorLiveData con los datos obtenidos de cualquiera de las fuentes
         */
    public MediatorLiveData<DataWrapper<Mixto, CustomException>> getMixto(String dateString) {
        firebaseDataSource.getMixto(dateString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Mixto,
                        CustomException>>() {

                    @Override
                    public void onSuccess(@NonNull DataWrapper<Mixto,
                            CustomException> data) {
                        liveDataMixto.postValue(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mixtoFromApi(dateString);
                    }
                });
        return liveDataMixto;
    }

    /**
     * Este método buscará los datos en el servidor remoto, si no los encuentra en Firebase.
     *
     * @param dateString La fecha
     */

    public void mixtoFromApi(String dateString) {
        apiService.getMixto(Utils.cleanDate(dateString))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Mixto>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(@NonNull Mixto r) {
                        liveDataMixto.postValue(new DataWrapper<>(r));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        liveDataMixto.setValue(new DataWrapper<>(new CustomException(NOTFOUND_OR_NOTCONNECTION)));
                    }
                });
    }

    /**
     * Este método buscará los datos en el servidor remoto, si no los encuentra en Firebase.
     *
     * @param dateString La fecha
     */

    public void oficioFromApi(String dateString) {
        apiService.getOficio(Utils.cleanDate(dateString))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Oficio>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(@NonNull Oficio r) {
                        liveData.postValue(new DataWrapper<>(r));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        liveData.setValue(new DataWrapper<>(new CustomException(NOTFOUND_OR_NOTCONNECTION)));
                    }
                });
        //return liveData;
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante {@link FirebaseDataSource#getLaudes(String)}
     * y si no encuentra, buscará en la Api mediante {@link ApiService#getBreviario(String, String)}
     * La llamada a la Api se hará desde el onError
     *
     * @param dateString La fecha
     * @return En MediatorLiveData con los datos obtenidos de cualquiera de las fuentes
     */
    public MediatorLiveData<DataWrapper<Laudes, CustomException>> getLaudes(String dateString) {
        firebaseDataSource.getLaudes(dateString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Laudes, CustomException>>() {

                    @Override
                    public void onSuccess(@NonNull DataWrapper<Laudes, CustomException> data) {
                        liveDataLaudes.postValue(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        laudesFromApi(dateString);
                    }
                });
        return liveDataLaudes;
    }

    /**
     * Este método buscará los datos en el servidor remoto, si no los encuentra en Firebase.
     *
     * @param dateString La fecha
     */

    public void laudesFromApi(String dateString) {
        apiService.getLaudes(Utils.cleanDate(dateString))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Laudes>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(@NonNull Laudes r) {
                        liveDataLaudes.postValue(new DataWrapper<>(r));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        liveDataLaudes.setValue(new DataWrapper<>(new CustomException(NOTFOUND_OR_NOTCONNECTION)));
                    }
                });
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante {@link FirebaseDataSource#getIntermedia(String, int)}
     * y si no encuentra, buscará en la Api mediante {@link ApiService#getBreviario(String, String)}
     * La llamada a la Api se hará desde el onError
     *
     * @param dateString La fecha
     */
    public void getIntermedia(String dateString, int hourId, String endPoint) {
        firebaseDataSource.getIntermedia(dateString, hourId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Intermedia, CustomException>>() {

                    @Override
                    public void onSuccess(@NonNull DataWrapper<Intermedia, CustomException> data) {
                        liveDataIntermedia.postValue(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        intermediaFromApi(endPoint, dateString);
                    }
                });
    }

    /**
     * Este método buscará los datos en el servidor remoto, si no los encuentra en Firebase.
     *
     * @param dateString La fecha
     */

    public void intermediaFromApi(String endPoint, String dateString) {
        apiService.getIntermedia(endPoint, Utils.cleanDate(dateString))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Intermedia>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(@NonNull Intermedia r) {
                        liveDataIntermedia.postValue(new DataWrapper<>(r));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        liveDataIntermedia.postValue(new DataWrapper<>(new CustomException(NOTFOUND_OR_NOTCONNECTION)));
                    }
                });
    }


    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante {@link FirebaseDataSource#getVisperas(String)}
     * y si no encuentra, buscará en la Api mediante {@link ApiService#getBreviario(String, String)}
     * La llamada a la Api se hará desde el onError
     *
     * @param dateString La fecha
     */
    public void getVisperas(String dateString) {
        firebaseDataSource.getVisperas(dateString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Visperas, CustomException>>() {

                    @Override
                    public void onSuccess(@NonNull DataWrapper<Visperas, CustomException> data) {
                        liveDataVisperas.postValue(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        visperasFromApi(dateString);
                    }
                });
    }

    /**
     * Este método buscará los datos en el servidor remoto, si no los encuentra en Firebase.
     *
     * @param dateString La fecha
     */

    public void visperasFromApi(String dateString) {

        apiService.getVisperas(Utils.cleanDate(dateString))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Visperas>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(@NonNull Visperas r) {
                        liveDataVisperas.postValue(new DataWrapper<>(r));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        liveDataVisperas.setValue(new DataWrapper<>(new CustomException(NOTFOUND_OR_NOTCONNECTION)));
                    }
                });
    }

    public MediatorLiveData<DataWrapper<Visperas, CustomException>> getVisperasDB(String s) {
        TodayVisperas theEntity = mTodayDao.getVisperasOfToday(Integer.valueOf(s));
        if (theEntity != null) {
            Visperas theModel = theEntity.getDomainModel();
            liveDataVisperas.postValue(new DataWrapper<>(theModel));
        } else {
            getVisperas(s);
        }
        return liveDataVisperas;
    }

    public MediatorLiveData<DataWrapper<Mixto, CustomException>> getMixtoDB(String s) {
        TodayMixto theEntity = mTodayDao.getMixtoOfToday(Integer.valueOf(s));
        if (theEntity != null) {
            Mixto theModel = theEntity.getDomainModel();
            liveDataMixto.postValue(new DataWrapper<>(theModel));
        } else {
            getMixto(s);
        }
        return liveDataMixto;
    }
}

