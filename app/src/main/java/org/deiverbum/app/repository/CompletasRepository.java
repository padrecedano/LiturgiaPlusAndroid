package org.deiverbum.app.repository;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;

import com.google.gson.Gson;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.source.remote.network.ConnectionChecker;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Completas;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.utils.Utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by a. Cedano on 2021/11/11
 */
public class CompletasRepository {
    ApiService apiService;
    private final FirebaseDataSource firebaseDataSource;
    private final MediatorLiveData<DataWrapper<Completas, CustomException>> mData = new MediatorLiveData<>();
    private final ConnectionChecker mChecker;

    @Inject
    public CompletasRepository(@ApplicationContext Context context, ApiService apiService, FirebaseDataSource firebaseDataSource) {
        this.apiService = apiService;
        this.firebaseDataSource = firebaseDataSource;
        mChecker=new ConnectionChecker(context);
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante {@link FirebaseDataSource#getMetaLiturgia(String)}
     * y si no encuentra, buscará en la Api mediante {@link ApiService#getMetaLiturgia(String)}
     * La llamada a la Api se hará desde el onError
     * @param param El parámetro a buscar, en principio la fecha, quizá también un Id
     */
    public void getData(String param) {
        if(mChecker.isConnected()) {
            firebaseDataSource.getMetaLiturgia(param)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<DataWrapper<MetaLiturgia,
                            CustomException>>() {
                        @Override
                        public void onSuccess(DataWrapper<MetaLiturgia, CustomException> data) {
                            readJson(data);
                            //mData.postValue(data);
                        }

                        @Override
                        public void onError(Throwable e) {
                            loadFromApi(Utils.cleanDate(param));
                        }
                    });
        } else {

loadFromApi(param);
            //mData.postValue(data);
        }
        //return mData;
    }



    /**
     * Obtiene los datos desde la Api
     * @param param La fecha del dato que se necesita
     */

    public void loadFromApi(String param) {
        DataWrapper<MetaLiturgia,CustomException> dataWrapper =
                new DataWrapper<>();

        apiService.getMetaLiturgia(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<MetaLiturgia>() {

                    @Override public void onStart() {
                    }
                    @Override
                    public void onSuccess(MetaLiturgia r) {
                        dataWrapper.postValue(r);
                        //mData.setValue(dataWrapper);
                        readJson(dataWrapper);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dataWrapper.setValue(new CustomException(e));
                        //mData.setValue(dataWrapper);
                        readJson(dataWrapper);

                    }

                });
        //return mData;
    }

    public void readJson(DataWrapper<MetaLiturgia,CustomException> metaWrapper){
        //DataWrapper dataWrapper = new DataWrapper();

        MetaLiturgia meta=metaWrapper.getData();
        if(meta.getWeekDay() == 7) {
            meta.setWeekDay(0);
        }
        String filePath = "res/raw/completas.json";
        InputStream raw = Objects.requireNonNull(getClass().getClassLoader()).getResourceAsStream(filePath);
        Gson gson = new Gson();
        Completas hora = gson.fromJson(new InputStreamReader(raw), Completas.class);
        hora.setMetaLiturgia(meta);
        //dataWrapper.postValue(hora);
        mData.setValue(new DataWrapper<>(hora));
        //return mData;


    }

    public MediatorLiveData<DataWrapper<Completas,CustomException>> getLiveData(){
        return mData;
    }



}

