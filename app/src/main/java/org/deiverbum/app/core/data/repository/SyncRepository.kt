package org.deiverbum.app.core.data.repository

import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.model.SyncResponse

/**
 * Interfaz para el repositorio del módulo de Sincronización.
 *
 * @author A. Cedano
 * @since 2024.1
 */
interface SyncRepository {
    /**
     * Este método obtiene la sincronización.
     *
     * Es implementado en [SyncRepositoryImpl.getSync][org.deiverbum.app.core.data.repository.SyncRepositoryImpl.getSync]
     * @param syncRequest Un objeto [SyncRequest] con la sincronización requerida.
     * @return Un objeto [SyncResponse] con la sincronización que se haya realizado.
     */
    suspend fun getSync(syncRequest: SyncRequest): SyncResponse


}