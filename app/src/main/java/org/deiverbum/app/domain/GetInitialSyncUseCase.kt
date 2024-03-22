package org.deiverbum.app.domain

import org.deiverbum.app.core.data.repository.InitialSyncRepository
import org.deiverbum.app.core.model.SyncRequest
import org.deiverbum.app.core.model.SyncResponse
import javax.inject.Inject

/**
 * Caso de uso para Sincronización.
 *
 * @author A. Cedano
 * @since 2024.1
 */
class GetInitialSyncUseCase @Inject constructor(
    private val mRepository: InitialSyncRepository
) {
    /**
     * Obtiene la respuesta de sincronización desde el repositorio, llamando a [InitialSyncRepository.getSync].
     * @param syncRequest Un objeto [SyncRequest] con la información de la sincronización que se solicita.
     * @return Un objeto  [SyncResponse] con el resultado de la sincronización.
     */
    suspend fun execute(syncRequest: SyncRequest): SyncResponse {
        return mRepository.getSync(syncRequest)
    }
}