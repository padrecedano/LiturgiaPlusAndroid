package org.deiverbum.app.core.data.source.network

import org.deiverbum.app.core.data.source.SyncEntityData
import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.model.SyncResponse
import org.deiverbum.app.core.model.data.SyncStatus
import javax.inject.Inject

/**
 * Fuente de datos de red para la sincronización.
 *
 * Se llama esta fuente de datos en primer lugar.
 *
 * @author A. Cedano
 * @since 2024.1
 */
class NetworkSyncEntityData @Inject constructor(
) : SyncEntityData {

    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        val syncStatus = SyncStatus()
        return SyncResponse(syncStatus)/*
        return try {
            val sub = todayApi.getUniversalisSync()
            syncStatus.source = Source.NETWORK
            syncStatus.lastUpdate = sub.lastUpdate
            SyncResponse(syncStatus, sub.data)
        } catch (e: Exception) {
            SyncResponse(syncStatus, emptyList())
        }*/
    }

    override suspend fun addSync(syncResponse: SyncResponse) {
    }

}