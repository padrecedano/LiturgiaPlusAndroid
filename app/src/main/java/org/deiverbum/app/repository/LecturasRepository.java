package org.deiverbum.app.repository;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Lecturas;
import org.deiverbum.app.utils.Utils;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * <p>Repositorio de datos para el módulo Lecturas.</p>
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
public class LecturasRepository {
    ApiService apiService;
    private FirebaseDataSource firebaseDataSource;
    private MediatorLiveData<DataWrapper<Lecturas, CustomException>> mData = new MediatorLiveData<>();

    @Inject
    public LecturasRepository(@ApplicationContext Context context,ApiService apiService, FirebaseDataSource firebaseDataSource) {
        this.apiService = apiService;
        this.firebaseDataSource = firebaseDataSource;
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante {@link FirebaseDataSource#getLecturas(String)}
     * y si no encuentra, buscará en la Api mediante {@link ApiService#getLecturas(String)}
     * La llamada a la Api se hará desde el onError
     * @param dateString El parámetro a buscar, en principio la fecha, quizá también un Id
     * @return En MediatorLiveData con los datos obtenidos de cualquiera de las fuentes
     */
    public MediatorLiveData<DataWrapper<Lecturas,CustomException>> getData(String dateString) {
            firebaseDataSource.getLecturas(dateString)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<DataWrapper<Lecturas, CustomException>>() {

                        @Override
                        public void onSuccess(DataWrapper<Lecturas, CustomException> data) {
                            mData.postValue(data);
                        }

                        @Override
                        public void onError(Throwable e) {
                            loadFromApi(Utils.cleanDate(dateString));
                        }
                    });
        return mData;
    }

    /**
     * Obtiene los datos desde la Api
     * @param dateString La fecha del dato que se necesita
     * @return Un objeto {@link Lecturas} con los datos que haya encontrado
     */
    public MediatorLiveData<DataWrapper<Lecturas,CustomException>> loadFromApi(String dateString) {
        DataWrapper dataWrapper = new DataWrapper();
        apiService.getLecturas(dateString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Lecturas>() {

                    @Override public void onStart() {
                    }
                    @Override
                    public void onSuccess(Lecturas r) {
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

