package org.deiverbum.app.domain.repository

import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse

/**
 * Interfaz para el repositorio del módulo de Sincronización.
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
interface SyncRepository {
    /**
     * Este método obtiene la sincronización.
     *
     * Es implementado en [org.deiverbum.app.data.repository.SyncRepositoryImpl.getSync]
     * @param syncRequest Un objeto [SyncRequest] con la sincronización requerida.
     * @return Un objeto [SyncResponse] con la sincronización que se haya realizado.
     */
    suspend fun getSync(syncRequest: SyncRequest): SyncResponse
}