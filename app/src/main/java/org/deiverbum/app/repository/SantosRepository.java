package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.ERR_REPORT;

import androidx.lifecycle.MediatorLiveData;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Santo;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by a. Cedano on 2021/11/11
 */
public class SantosRepository {
    private final FirebaseDataSource firebaseDataSource;
    private final MediatorLiveData<DataWrapper<Santo, CustomException>> mData =
            new MediatorLiveData<>();


    @Inject
    public SantosRepository(FirebaseDataSource firebaseDataSource) {
        this.firebaseDataSource = firebaseDataSource;
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Buscará únicamente en Firestore mediante
     * {@link FirebaseDataSource#getSantos(String, String)}
     * y si no encuentra, devolverá un objeto {@link DataWrapper} con error.
     * @param month El mes a buscar
     * @param day El día a buscar
     * @return En MediatorLiveData con los datos obtenidos
     */
    public MediatorLiveData<DataWrapper<Santo,CustomException>> getData(String month, String day) {
        firebaseDataSource.getSantos(month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Santo,
                        CustomException>>() {

                    @Override
                    public void onSuccess(DataWrapper<Santo,CustomException> data) {
                        mData.postValue(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String msg=String.format("%s%s",e.getMessage(),
                                ERR_REPORT);
                        mData.postValue(new DataWrapper<>(new CustomException(msg)));
                        //loadFromApi(Utils.cleanDate(param));
                    }
                });
        return mData;
    }



}

