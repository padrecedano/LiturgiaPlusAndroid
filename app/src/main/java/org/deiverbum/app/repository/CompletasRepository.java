package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.ERR_CONEXION;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.source.remote.network.ConnectionChecker;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Completas;
import org.deiverbum.app.model.Lecturas;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.utils.Utils;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by a. Cedano on 2021/11/11
 */
public class CompletasRepository {
    ApiService apiService;
    private FirebaseDataSource firebaseDataSource;
    private MediatorLiveData<DataWrapper<Completas, CustomException>> mData = new MediatorLiveData<>();
    private Context mContext;
    private ConnectionChecker mChecker;

    @Inject
    public CompletasRepository(@ApplicationContext Context context, ApiService apiService, FirebaseDataSource firebaseDataSource) {
        this.apiService = apiService;
        this.firebaseDataSource = firebaseDataSource;
        this.mContext=context;
        mChecker=new ConnectionChecker(mContext);
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante {@link FirebaseDataSource#getMetaLiturgia(String)}
     * y si no encuentra, buscará en la Api mediante {@link ApiService#getMetaLiturgia(String)}
     * La llamada a la Api se hará desde el onError
     * @param param El parámetro a buscar, en principio la fecha, quizá también un Id
     * @return En MediatorLiveData con los datos obtenidos de cualquiera de las fuentes
     */
    public void getData(String param) {
        if(mChecker.isConnected()) {
            firebaseDataSource.getMetaLiturgia(param)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<DataWrapper<MetaLiturgia, CustomException>>() {

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
            DataWrapper data = new DataWrapper();
            data.setValue(new CustomException(ERR_CONEXION));
            readJson(data);

            //mData.postValue(data);
        }
        //return mData;
    }



    /**
     * Obtiene los datos desde la Api
     * @param param La fecha del dato que se necesita
     * @return Un objeto {@link MetaLiturgia} con los datos que haya encontrado
     */

    public void loadFromApi(String param) {
        DataWrapper dataWrapper = new DataWrapper();

        apiService.getMetaLiturgia(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MetaLiturgia>() {

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

    public MediatorLiveData<DataWrapper<Completas,CustomException>> readJson(DataWrapper<MetaLiturgia,CustomException> metaWrapper){
        DataWrapper dataWrapper = new DataWrapper();

        MetaLiturgia meta=metaWrapper.getData();
        if(meta.getWeekDay() == 7) {
            meta.setWeekDay(0);
        }
        String filePath = "res/raw/completas.json";
        InputStream raw = getClass().getClassLoader().getResourceAsStream(filePath);
        Gson gson = new Gson();
        Completas hora = gson.fromJson(new InputStreamReader(raw), Completas.class);
        hora.setMetaLiturgia(meta);
        dataWrapper.postValue(hora);
        mData.setValue(dataWrapper);
        return mData;


    }

    public MediatorLiveData<DataWrapper<Completas,CustomException>> getLiveData(){
        return mData;
    }

    }

