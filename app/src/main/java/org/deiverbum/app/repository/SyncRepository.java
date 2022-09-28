package org.deiverbum.app.repository;

import androidx.lifecycle.LiveData;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.model.SyncStatus;

import javax.inject.Inject;

/**
 * <p>Repositorio para el módulo de Sincronización.</p>
 * <p>Busca en la base de datos la última fecha disponible en el calendario y la fecha de la última sincronización.</p>
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
        return mData;
    }
}

