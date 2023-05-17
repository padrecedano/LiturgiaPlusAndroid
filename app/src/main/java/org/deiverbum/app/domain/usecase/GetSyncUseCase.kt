package org.deiverbum.app.domain.usecase

import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.domain.repository.SyncRepository
import javax.inject.Inject

/**
 * <p>Caso de uso para Sincronización.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class GetSyncUseCase @Inject constructor(
    private val mRepository: SyncRepository
) {
    suspend fun execute(request: SyncRequest): SyncResponse {
        return mRepository.getSync(request)
    }
}