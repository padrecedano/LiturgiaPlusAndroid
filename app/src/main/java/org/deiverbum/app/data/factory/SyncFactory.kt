package org.deiverbum.app.data.factory

import org.deiverbum.app.data.source.SyncEntityData
import org.deiverbum.app.data.source.firebase.FirebaseSyncEntityData
import org.deiverbum.app.data.source.local.LocalSyncEntityData
import org.deiverbum.app.data.source.network.NetworkSyncEntityData
import org.deiverbum.app.util.Source
import javax.inject.Inject

/**
 * <p>Factory para la sincronización.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */

class SyncFactory @Inject constructor(
    private val networkSyncEntityData: NetworkSyncEntityData,
    private val localSyncEntityData: LocalSyncEntityData,
    private val firebaseSyncEntityData: FirebaseSyncEntityData

) {

    fun create(source: Source): SyncEntityData {
        return when (source) {
            Source.NETWORK -> networkSyncEntityData
            Source.LOCAL  -> localSyncEntityData
            else -> firebaseSyncEntityData
        }
    }
}