package org.deiverbum.app.repository;
import android.content.Context;

import androidx.lifecycle.MediatorLiveData;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Homilias;
import org.deiverbum.app.utils.Utils;

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
 * @CreateDate: 2021/11/11
 * @since 2022.01.01
 */

public class HomiliasRepository {
    ApiService apiService;
    private FirebaseDataSource firebaseDataSource;
    private MediatorLiveData<DataWrapper<Homilias, CustomException>> mData = new MediatorLiveData<>();

    @Inject
    public HomiliasRepository(FirebaseDataSource firebaseDataSource, ApiService apiService) {
        this.apiService = apiService;
        this.firebaseDataSource = new FirebaseDataSource();
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
                .subscribeWith(new DisposableSingleObserver<DataWrapper<Homilias,CustomException>>() {

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


    public MediatorLiveData<DataWrapper<Homilias,CustomException>> loadFromApi(String param) {
        DataWrapper dataWrapper = new DataWrapper();

        apiService.getHomilias(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Homilias>() {

                    @Override public void onStart() {
                    }
                    @Override
                    public void onSuccess(Homilias r) {
                        dataWrapper.postValue(r);
                        mData.setValue(dataWrapper);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dataWrapper.setValue(new CustomException(e));
                        mData.setValue(dataWrapper);
                    }
                });

        return mData;
    }
}
