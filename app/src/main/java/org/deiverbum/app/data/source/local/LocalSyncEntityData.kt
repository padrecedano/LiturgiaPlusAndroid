package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.source.SyncEntityData
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.model.SyncStatus
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

    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        if(syncRequest.yearToClean!=0){
            val rowsDeleted=todayDao.deleteLastYear(syncRequest.yearToClean)
            //val yearClean=todayDao.deleteLastYear(syncRequest.yearToClean)
            val syncInfo=String.format("Total de filas borradas: %d",rowsDeleted)
            return SyncResponse(SyncStatus(syncInfo,syncRequest.yearToClean))
        }
        val se = todayDao.syncInfo()
        //syncResponse.dataForView=SpannableStringBuilder( se!!.getAll(false))
        //if(syncResponse.allToday.isNullOrEmpty())
        return SyncResponse(se!!)
    }

    override suspend fun addSync(sync: SyncResponse) {
        val indx=//
             todayDao.insertAllTodays(sync.allToday)
        Timber.d("indx.toString()")
    }
}