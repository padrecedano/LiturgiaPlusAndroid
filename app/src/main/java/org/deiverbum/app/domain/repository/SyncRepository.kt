package org.deiverbum.app.domain.repository

import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse

/**
 * <p>Interfaz para el repositorio del módulo de Sincronización.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
interface SyncRepository {
    suspend fun getSync(syncRequest: SyncRequest): SyncResponse
}