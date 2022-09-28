package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.CONTENT_NOTFOUND;
import static org.deiverbum.app.utils.Constants.ERR_REPORT;
import static org.deiverbum.app.utils.Constants.FILE_NIGHT_PRAYER;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.deiverbum.app.data.source.local.FileDataSource;
import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.source.remote.network.ConnectionChecker;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Book;
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
    private final FileDataSource fileDataSource;

    @Inject
    public CompletasRepository(@ApplicationContext Context context, ApiService apiService, FirebaseDataSource firebaseDataSource,FileDataSource fileDataSource) {
        this.apiService = apiService;
        this.firebaseDataSource = firebaseDataSource;
        this.fileDataSource = fileDataSource;
        mChecker = new ConnectionChecker(context);
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante {@link FirebaseDataSource#getMetaLiturgia(String)}
     * y si no encuentra, buscará en la Api mediante {@link ApiService#getMetaLiturgia(String)}
     * La llamada a la Api se hará desde el onError
     *
     * @param param El parámetro a buscar, en principio la fecha, quizá también un Id
     */
    public void getData(String param) {
        if (mChecker.isConnected()) {
            firebaseDataSource.getMetaLiturgia(param)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<DataWrapper<MetaLiturgia,
                            CustomException>>() {
                        @Override
                        public void onSuccess(DataWrapper<MetaLiturgia, CustomException> data) {
                            readJson(data);
                        }

                        @Override
                        public void onError(Throwable e) {

                            loadFromApi(Utils.cleanDate(param));
                        }
                    });
        } else {

            loadFromApi(param);
        }
    }


    /**
     * Obtiene los datos desde la Api
     *
     * @param param La fecha del dato que se necesita
     */

    public void loadFromApi(String param) {
        DataWrapper<MetaLiturgia, CustomException> dataWrapper =
                new DataWrapper<>();

        apiService.getMetaLiturgia(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<MetaLiturgia>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(MetaLiturgia r) {
                        dataWrapper.postValue(r);
                        readJson(dataWrapper);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mData.setValue(new DataWrapper<>(new CustomException(CONTENT_NOTFOUND)));
                    }
                });
    }

    public void readJson(DataWrapper<MetaLiturgia, CustomException> metaWrapper) {
        if (metaWrapper.status == DataWrapper.Status.SUCCESS) {
            MetaLiturgia meta = metaWrapper.getData();
            if (meta.getWeekDay() == 7) {
                meta.setWeekDay(0);
            }
            String filePath = FILE_NIGHT_PRAYER;
            InputStream raw = Objects.requireNonNull(getClass().getClassLoader()).getResourceAsStream(filePath);
            Gson gson = new Gson();
            Completas hora = gson.fromJson(new InputStreamReader(raw), Completas.class);
            hora.setMetaLiturgia(meta);
            mData.setValue(new DataWrapper<>(hora));
        }
    }

    public MediatorLiveData<DataWrapper<Completas, CustomException>> getLiveData() {
        return mData;
    }

    public LiveData<DataWrapper<Completas, CustomException>> getBookk(String rawPath) {
        MutableLiveData<DataWrapper<Completas, CustomException>> finalData =
                new MediatorLiveData<>();
        fileDataSource.getCompletas(rawPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Completas,
                        CustomException>>() {
                    @Override
                    public void onSuccess(@NonNull DataWrapper<Completas,
                            CustomException> data) {
                        mData.postValue(data);
                        dispose();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mData.postValue(new DataWrapper<>(new CustomException(String.format("Error:\n%s%s", e.getMessage(), ERR_REPORT))));
                        dispose();
                    }});

        return finalData;
    }
}

