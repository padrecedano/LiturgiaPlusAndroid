package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.ERR_REPORT;

import androidx.lifecycle.MediatorLiveData;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.BreviarioHora;
import org.deiverbum.app.model.Homilias;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by a. Cedano on 2021/11/11
 */
public class SantosRepository {
    private FirebaseDataSource firebaseDataSource;
    private MediatorLiveData<DataWrapper<Homilias, CustomException>> mData = new MediatorLiveData<>();


    @Inject
    public SantosRepository(FirebaseDataSource firebaseDataSource) {
        this.firebaseDataSource = firebaseDataSource;
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Buscará únicamente en Firestore mediante {@link #callFirebase(String, String)}()}
     * y si no encuentra, devolverá un objeto {@link DataWrapper} con error.
     * @param month El parámetro a buscar, en principio la fecha, quizá también un Id
     * @return En MediatorLiveData con los datos obtenidos de cualquiera de las fuentes
     */
    public MediatorLiveData<DataWrapper<Homilias,CustomException>> getData(String month, String day) {
        callFirebase(month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<DataWrapper<Homilias,CustomException>>() {

                    @Override
                    public void onSuccess(DataWrapper<Homilias,CustomException> data) {
                        mData.postValue(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        DataWrapper error=new DataWrapper();
                        error.setException(String.format("%s%s",e.getMessage(),ERR_REPORT));
                        mData.postValue(error);
                        //loadFromApi(Utils.cleanDate(param));
                    }
                });
        return mData;
    }

    /**
     * Obtiene los datos desde Firebase
     * @param month La fecha del dato que se necesita
     * @return Un objeto {@link BreviarioHora} con los datos que haya encontrado
     */
    public Single callFirebase(String month, String day) {
        return firebaseDataSource.getSantos(month, day);
    }


}

