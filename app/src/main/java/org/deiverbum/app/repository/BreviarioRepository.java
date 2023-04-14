package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.CONTENT_TO_SYNC;
import static org.deiverbum.app.utils.Constants.ERR_REPORT;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.entity.relation.TodayCompletas;
import org.deiverbum.app.data.entity.relation.TodayLaudes;
import org.deiverbum.app.data.entity.relation.TodayMixto;
import org.deiverbum.app.data.entity.relation.TodayNona;
import org.deiverbum.app.data.entity.relation.TodayOficio;
import org.deiverbum.app.data.entity.relation.TodaySexta;
import org.deiverbum.app.data.entity.relation.TodayTercia;
import org.deiverbum.app.data.entity.relation.TodayVisperas;
import org.deiverbum.app.data.source.local.FileDataSource;
import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.LiturgyHelper;
import org.deiverbum.app.model.Today;
import org.deiverbum.app.utils.Utils;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Repositorio del módulo Breviary.
 * Este repositorio busca los datos en el siguiente orden:
 * <ul>
 *     <li>En la base de datos local</li>
 *     <li>En Firebase</li>
 *     <li>En el servidor remoto</li>
 * </ul>
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.01.01
 */

public class BreviarioRepository {
    private final ApiService apiService;
    private final FirebaseDataSource firebaseDataSource;
    private final MediatorLiveData<DataWrapper<Liturgy, CustomException>> liveData = new MediatorLiveData<>();
    private final MediatorLiveData<DataWrapper<Today, CustomException>> liveDataToday = new MediatorLiveData<>();
    private final TodayDao mTodayDao;
    private final FileDataSource fileDataSource;

    @Inject
    public BreviarioRepository(
            FirebaseDataSource firebaseDataSource,
            ApiService apiService,
            TodayDao todayDao,
            FileDataSource fileDataSource
    ) {
        this.firebaseDataSource = firebaseDataSource;
        this.mTodayDao = todayDao;
        this.apiService = apiService;
        this.fileDataSource = fileDataSource;
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en la base de datos
     * si no encuentra, buscará en Firestore mediante {@link #getFromFirebase(String, int)}
     * y si no encuentra, buscará en la Api mediante {@link #getFromApi(String, int)}
     * La llamada a la Api se hará desde el onError
     *
     * @param theDate La fecha
     * @param hourId  Un valor numérico que identifica la hora
     * @return En MediatorLiveData con los datos obtenidos de cualquiera de las fuentes
     */

    public MediatorLiveData<DataWrapper<Today, CustomException>> getFromLocal(String theDate, int hourId) {
        //getFromFirebase(1);
        switch (hourId) {
            case 0:
                TodayMixto mixtoEntity = mTodayDao.getMixtoOfToday(Integer.valueOf(theDate));
                if (mixtoEntity != null) {
                    Today theModel = mixtoEntity.getDomainModelToday();
                    liveDataToday.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 1:
                TodayOficio oficioEntity = mTodayDao.getOficioOfToday(Integer.valueOf(theDate));
                if (oficioEntity != null) {
                    Today theModel = oficioEntity.getDomainModelToday();
                    liveDataToday.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 2:
                TodayLaudes laudesEntity = mTodayDao.getLaudesOfToday(Integer.valueOf(theDate));
                if (laudesEntity != null) {
                    Today theModel = laudesEntity.getDomainModelToday();
                    liveDataToday.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 3:
                TodayTercia todayTercia = mTodayDao.getTerciaOfToday(Integer.valueOf(theDate));
                if (todayTercia != null) {
                    Today theModel = todayTercia.getDomainModelToday();
                    liveDataToday.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 4:
                TodaySexta todaySexta = mTodayDao.getSextaOfToday(Integer.valueOf(theDate));
                if (todaySexta != null) {
                    Today theModel = todaySexta.getDomainModelToday();
                    liveDataToday.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 5:
                TodayNona todayNona = mTodayDao.getNonaOfToday(Integer.valueOf(theDate));
                if (todayNona != null) {
                    Today theModel = todayNona.getDomainModelToday();
                    liveDataToday.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 6:
                TodayVisperas todayVisperas = mTodayDao.getVisperasOfToday(Integer.valueOf(theDate));
                if (todayVisperas != null) {
                    Today theModel = todayVisperas.getDomainModelToday();
                    liveDataToday.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 7:
                TodayCompletas completasEntity = mTodayDao.getCompletasOfToday(Integer.valueOf(theDate));
                if (completasEntity != null) {
                    getFromFile(completasEntity.getToday());

                } else {
                    getFromApi(theDate, hourId);
                }
                break;

            default:
                break;
        }
        return liveDataToday;
    }

    /**
     * Este método buscará en Firestore mediante {@link FirebaseDataSource#getBreviary(String, int)}
     * y si no encuentra, buscará en la Api llamando a {@link #getFromApi(String, int)}
     * La llamada a la Api se hará desde el onError
     *
     * @param theDate La fecha
     * @param hourId  El Id de la hora
     */

    public void getFromFirebase(String theDate, int hourId) {
        firebaseDataSource.getBreviary(theDate, hourId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Today, CustomException>>() {

                    @Override
                    public void onSuccess(@NonNull DataWrapper<Today, CustomException> data) {
                        liveDataToday.postValue(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getFromApi(theDate, hourId);
                    }
                });
    }

    /**
     * Este método buscará los datos en el servidor remoto, si no los encuentra en Firebase,
     * mediante una llamada a {@link ApiService#getBreviario(String, String)}
     *
     * @param theDate La fecha
     * @param hourId  Un identificador numérico para la hora
     */

    public void getFromApi(String theDate, int hourId) {
        String endPoint = LiturgyHelper.Companion.getMap(hourId);
        apiService.getToday(endPoint, Utils.cleanDate(theDate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Today>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(@NonNull Today r) {
                        liveDataToday.postValue(new DataWrapper<>(r));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        liveDataToday.setValue(new DataWrapper<>(new CustomException(CONTENT_TO_SYNC)));
                    }
                });
    }

    /**
     * Este método es exclusivo de la hora de Completas.
     * Obtendrá los textos desde un archivo local.
     * En un futuro se migrará el modelo de Completas a la Base de Datos.
     *
     * @param today Un objeto {@link Today obtenido de la Base de Datos}
     */

    public void getFromFile(Today today) {
        fileDataSource.getCompletas(today)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Today,
                        CustomException>>() {
                    @Override
                    public void onSuccess(@NonNull DataWrapper<Today,
                            CustomException> data) {
                        data.getData().liturgyDay.typeID = 7;
                        liveDataToday.postValue(data);
                        dispose();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        liveData.postValue(new DataWrapper<>(new CustomException(String.format("Error:\n%s%s", e.getMessage(), ERR_REPORT))));
                        dispose();
                    }
                });
    }


}