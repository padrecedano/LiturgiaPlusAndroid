package org.deiverbum.app.core.data.source.network

import kotlinx.coroutines.flow.MutableSharedFlow
import org.deiverbum.app.core.data.source.SyncEntityData
import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.model.SyncResponse
import org.deiverbum.app.core.model.SyncResponseNew
import org.deiverbum.app.core.model.data.SyncStatus
import org.deiverbum.app.core.network.api.TodayApi
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
        val sub = todayApi.getTodayAll("all")

        val syncStatus = SyncStatus()
        syncStatus.source = Source.NETWORK
        return SyncResponse(syncStatus, sub)
    }

    override suspend fun addSync(syncResponse: SyncResponse) {
        TODO("Not yet implemented")
    }

    override suspend fun addSyncc(syncResponse: SyncResponseNew) {
        TODO("Not yet implemented")
    }

    //TODO "Concluir esto"
    override suspend fun getSyncc(syncRequest: SyncRequest): SyncResponseNew {
        val todayAll = todayApi.getTodayAllNew("all")//.take(60)
        val syncStatus = SyncStatus("", 1)
        val userFlow = MutableSharedFlow<SyncStatus>(replay = 1)

        syncStatus.source = Source.NETWORK
        val r = SyncResponseNew(userFlow, todayAll)

        return r
    }

}