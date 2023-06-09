package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.source.SyncEntityData
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.model.SyncStatus
import org.deiverbum.app.util.Source
import org.deiverbum.app.utils.Utils
import timber.log.Timber
import javax.inject.Inject

/**
 * <p>Fuente de datos local para la sincronización.</p>
 * <p>Se llama a esta fuente de datos si falla la llamada a [org.deiverbum.app.data.source.network.NetworkSyncEntityData].</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */

class LocalSyncEntityData @Inject constructor(
    private val todayDao: TodayDao
) : SyncEntityData {

    /**
     * <p>Este método recibe una petición de sincronización expresada
     * en un objeto del tipo [SyncRequest].</p>
     * <p>Verifica si el valor de la propiedad [SyncRequest.yearToClean]
     * no es igual a `0`. Esto sirve para borrar de la base de datos
     * los datos del calendario del año anterior, optimizando el espacio.</p>
     * <p>Luego llama a [addSync] para insertar los datos
     */
    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        var isYearCleaned = false

        if(syncRequest.yearToClean!=0){
        var syncStatus=SyncStatus()

            //if(isYearCleaned){
            val rowsDeleted=todayDao.deleteLastYear(syncRequest.yearToClean)
            //val yearClean=todayDao.deleteLastYear(syncRequest.yearToClean)
            //val syncInfo=String.format("Total de filas borradas: %d",rowsDeleted)
                if(rowsDeleted>0){
                    isYearCleaned=true
                    syncStatus.lastYearCleaned=syncRequest.yearToClean
                }
            //return SyncResponse(SyncStatus(syncInfo,syncRequest.yearToClean))
        }
        val se = todayDao.syncInfo()
        //syncStatus.
        if(isYearCleaned){
            se?.lastYearCleaned==syncRequest.yearToClean
        }
        //syncResponse.dataForView=SpannableStringBuilder( se!!.getAll(false))
        //if(syncResponse.allToday.isNullOrEmpty())
        se?.source=Source.LOCAL
        //var syncResponse=SyncResponse(syncStatus,se)

        //syncResponse..
        return SyncResponse(se!!)
    }

    override suspend fun addSync(sync: SyncResponse) {
        val indx=
             todayDao.insertAllTodays(sync.allToday)
        //return sync
        todayDao.syncUpdate(Utils.getCurrentTimeStamp())
        Timber.d(indx.toString())
    }
}