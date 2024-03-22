package org.deiverbum.app.core.data.factory

import org.deiverbum.app.core.data.source.SyncEntityData
import org.deiverbum.app.core.data.source.firebase.FirebaseSyncEntityData
import org.deiverbum.app.core.data.source.local.LocalSyncEntityData
import org.deiverbum.app.core.data.source.network.NetworkSyncEntityData
import org.deiverbum.app.util.Source
import javax.inject.Inject

/**
 * Factory para la sincronización.
 *
 * @author A. Cedano
 * @since 2024.1
 */

class SyncFactory @Inject constructor(
    private val networkSyncEntityData: NetworkSyncEntityData,
    private val localSyncEntityData: LocalSyncEntityData,
    private val firebaseSyncEntityData: FirebaseSyncEntityData

) {

    /**
     * Crea una fuente de datos del tipo pasado en [source].
     *
     * @param source El tipo de fuente de datos que se requiere.
     * @return Una interfaz [SyncEntityData] que se ocupa de manejar las diferentes fuentes de datos.
     */
    fun create(source: Source): SyncEntityData {
        return when (source) {
            Source.NETWORK -> networkSyncEntityData
            Source.LOCAL -> localSyncEntityData
            else -> firebaseSyncEntityData
        }
    }
}