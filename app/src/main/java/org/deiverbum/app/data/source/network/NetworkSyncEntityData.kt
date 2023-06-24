package org.deiverbum.app.data.source.network

import org.deiverbum.app.data.api.TodayApi
import org.deiverbum.app.data.source.SyncEntityData
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.model.SyncStatus
import org.deiverbum.app.util.Source
import javax.inject.Inject

/**
 * Fuente de datos de red para la sincronización.
 *
 * Se llama esta fuente de datos en primer lugar.
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class NetworkSyncEntityData @Inject constructor(
    private val todayApi: TodayApi
) : SyncEntityData {

    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        val todayAll = todayApi.getTodayAll("all")
        val syncStatus = SyncStatus()
        syncStatus.source = Source.NETWORK
        return SyncResponse(syncStatus, todayAll)
    }


    override suspend fun addSync(syncResponse: SyncResponse) {
        TODO("Not yet implemented")
    }

}