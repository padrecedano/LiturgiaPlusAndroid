package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.NOTFOUND_OR_NOTCONNECTION;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.entity.TodayComentarios;
import org.deiverbum.app.data.entity.TodayHomilias;
import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Comentarios;
import org.deiverbum.app.model.Homilias;
import org.deiverbum.app.utils.Utils;

import javax.inject.Inject;

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
 * @since 2022.1
 */
public class ComentariosRepository {
    ApiService apiService;
    private final FirebaseDataSource firebaseDataSource;
    private final TodayDao mTodayDao;
    private final MediatorLiveData<DataWrapper<Comentarios, CustomException>> mData = new MediatorLiveData<>();

    @Inject
    public ComentariosRepository(ApiService apiService, FirebaseDataSource firebaseDataSource, TodayDao todayDao) {
        this.apiService = apiService;
        this.firebaseDataSource = firebaseDataSource;
        this.mTodayDao = todayDao;
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante
     * {@link FirebaseDataSource#getLecturas(String)}
     * y si no encuentra, buscará en la Api mediante
     * {@link ApiService#getLecturas(String)}
     * La llamada a la Api se hará desde el onError
     * @param dateString La fecha
     * @return En MediatorLiveData con los datos obtenidos de cualquiera de las fuentes
     */
    public MutableLiveData<DataWrapper<Comentarios, CustomException>> getComentarios(String dateString) {
        firebaseDataSource.getComentarios(dateString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Comentarios,
                        CustomException>>() {

                    @Override
                    public void onSuccess(@NonNull DataWrapper<Comentarios,
                            CustomException> data) {
                        mData.postValue(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        comentariosFromApi(dateString);
                    }
                });
        return mData;
    }

    /**
     * Este método buscará los datos en el servidor remoto, si no los encuentra en Firebase.
     * @param dateString La fecha
     */

    public void comentariosFromApi(String dateString) {
        apiService.getComentarios(Utils.cleanDate(dateString))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Comentarios>() {
                    @Override public void onStart() {
                    }
                    @Override
                    public void onSuccess(@NonNull Comentarios r) {
                        mData.postValue(new DataWrapper<>(r));
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        mData.setValue(new DataWrapper<>(new CustomException(NOTFOUND_OR_NOTCONNECTION)));
                    }
                });
    }

    public MediatorLiveData<DataWrapper<Comentarios, CustomException>> getFromDB(String s) {
        TodayComentarios theEntity = mTodayDao.getComentarios(Integer.valueOf(s));
        if (theEntity != null) {
            Comentarios theModel = theEntity.getDomainModel();
            mData.postValue(new DataWrapper<>(theModel));
        } else {
            getComentarios(s);
        }
        return mData;
    }

}

