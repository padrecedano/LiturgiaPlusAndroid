package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.source.SyncEntityData
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import javax.inject.Inject

/**
 * <p>Fuente de datos local para la sincronización.</p>
 * <p>Se llama a esta fuente de datos si falla la llamada a [org.deiverbum.app.data.source.network.NetworkSyncEntityData].</p>
 *
 * @author A. Cedano
 * @since 2023.3
 */

class LocalSyncEntityData @Inject constructor(
    private val todayDao: TodayDao
) : SyncEntityData {

    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        val se = todayDao.syncInfo()
        //syncResponse.dataForView=SpannableStringBuilder( se!!.getAll(false))
        //if(syncResponse.allToday.isNullOrEmpty())
        return SyncResponse(SpannableStringBuilder(se!!.getAll(false)), null)
    }

    override suspend fun addSync(sync: SyncResponse) {
        //
    }
}