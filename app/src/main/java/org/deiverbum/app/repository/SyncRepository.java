package org.deiverbum.app.repository;

import androidx.lifecycle.LiveData;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.model.SyncStatus;

import javax.inject.Inject;

/**
 * <p>Repositorio de datos para el módulo Homilías.</p>
 * <p>Orden de búsqueda: </p>
 * <ul>
 *     <li>Firebase</li>
 *     <li>Api</li>
 * </ul>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

public class SyncRepository {
    ApiService apiService;
    private  LiveData<SyncStatus> mData;
    private final TodayDao mTodayDao;


    @Inject
    public SyncRepository(
                          ApiService apiService,
                          TodayDao todayDao
    ) {
        this.apiService = apiService;
        this.mTodayDao = todayDao;
        this.mData=mTodayDao.getSyncInfo();

    }


    public LiveData<SyncStatus> getFromDB() {
        //Integer n = mTodayDao.findLastToday();

        //mData.addSource(mTodayDao.getSyncInfo(), value -> mData.setValue(value));
        //mData.addSource(mTodayDao.liveLastSync(), value -> mData.setValue(value));

        //String s=String.format("Última fecha: %d",n);
        //LiveData<String> st=mTodayDao.syncLastVersionLD();
        //mData.postValue(mTodayDao.getSyncInfo());
        return mData;
    }
}

