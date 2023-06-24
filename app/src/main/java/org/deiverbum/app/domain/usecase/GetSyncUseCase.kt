package org.deiverbum.app.domain.usecase

import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.domain.repository.SyncRepository
import javax.inject.Inject

/**
 * Caso de uso para Sincronización.
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class GetSyncUseCase @Inject constructor(
    private val mRepository: SyncRepository
) {
    /**
     * Obtiene la respuesta de sincronización desde el repositorio, llamando a [SyncRepository.getSync].
     * @param syncRequest Un objeto [SyncRequest] con la información de la sincronización que se solicita.
     * @return Un objeto  [SyncResponse] con el resultado de la sincronización.
     */
    suspend fun execute(syncRequest: SyncRequest): SyncResponse {
        return mRepository.getSync(syncRequest)
    }
}