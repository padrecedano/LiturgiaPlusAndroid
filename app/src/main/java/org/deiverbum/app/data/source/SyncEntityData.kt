package org.deiverbum.app.data.source

import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse

interface SyncEntityData {

    suspend fun getSync(syncRequest: SyncRequest): SyncResponse

    suspend fun addSync(sync: SyncResponse)

    //suspend fun getFromFirebase(): SyncResponse

}