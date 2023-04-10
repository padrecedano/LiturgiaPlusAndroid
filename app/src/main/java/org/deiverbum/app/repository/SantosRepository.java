package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.ERR_REPORT;

import androidx.lifecycle.MediatorLiveData;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.SaintLife;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by a. Cedano on 2021/11/11
 */
public class SantosRepository {
    private final FirebaseDataSource firebaseDataSource;
    private final MediatorLiveData<DataWrapper<SaintLife, CustomException>> mData =
            new MediatorLiveData<>();
    private final TodayDao mTodayDao;


    @Inject
    public SantosRepository(FirebaseDataSource firebaseDataSource, TodayDao todayDao) {
        this.firebaseDataSource = firebaseDataSource;
        this.mTodayDao = todayDao;

    }

    /**
     * Este método inicia la llamada al DataSource.
     * Buscará únicamente en Firestore mediante
     * {@link FirebaseDataSource#getSantos(int[])}
     * y si no encuentra, devolverá un objeto {@link DataWrapper} con error.
     *
     * @param monthAndDay El mes y el día a buscar
     */
    public void getData(int[] monthAndDay) {
        firebaseDataSource.getSantos(monthAndDay)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<SaintLife,
                        CustomException>>() {

                    @Override
                    public void onSuccess(DataWrapper<SaintLife, CustomException> data) {
                        mData.postValue(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String msg = String.format("%s%s", e.getMessage(),
                                ERR_REPORT);
                        mData.postValue(new DataWrapper<>(new CustomException(msg)));
                        //loadFromApi(Utils.cleanDate(param));
                    }
                });
    }

    public MediatorLiveData<DataWrapper<SaintLife, CustomException>> getSaintDB(int[] monthAndDay) {
        SaintLife theModel = mTodayDao.getSantoOfToday(monthAndDay[0], monthAndDay[1]).getDomainModel();
        if (theModel != null) {
            mData.postValue(new DataWrapper<>(theModel));
        } else {
            getData(monthAndDay);
        }
        return mData;
    }
}