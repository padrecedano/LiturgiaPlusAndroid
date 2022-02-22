package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.NOTFOUND_OR_NOTCONNECTION;

import androidx.lifecycle.MediatorLiveData;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Homilias;
import org.deiverbum.app.utils.Utils;

import javax.inject.Inject;

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

public class HomiliasRepository {
    ApiService apiService;
    private final FirebaseDataSource firebaseDataSource;
    private final MediatorLiveData<DataWrapper<Homilias, CustomException>> mData = new MediatorLiveData<>();

    @Inject
    public HomiliasRepository(FirebaseDataSource firebaseDataSource, ApiService apiService) {
        this.firebaseDataSource = firebaseDataSource;
        this.apiService = apiService;
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
}

