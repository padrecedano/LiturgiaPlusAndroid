package org.deiverbum.app.core.data.source

import org.deiverbum.app.core.model.sync.SyncRequest
import org.deiverbum.app.core.model.sync.SyncResponse

/**
 * Interfaz para manejar las fuentes de datos en la sincronización.
 **
 * @author A. Cedano
 * @since 2024.1
 */
interface SyncEntityData {

    /**
     * Este método recibe una petición de sincronización expresada
     * en un objeto del tipo [SyncRequest].
     *
     * @param syncRequest es un objeto [SyncRequest] con la petición de sincronización.
     * @return Un objeto [SyncResponse].
     */
    suspend fun getSync(syncRequest: SyncRequest): SyncResponse

    /**
     * Este método inserta en la base de datos el contenido de un objeto [SyncResponse].
     *
     * @param syncResponse Es un objeto [SyncResponse] con los datos a sincronizar.
     */
    suspend fun addSync(syncResponse: SyncResponse)

}