package org.deiverbum.app.domain.usecase

import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse
import org.deiverbum.app.domain.repository.TodayRepository
import javax.inject.Inject

/**
 * <p>Caso de uso Today.</p>
 *
 * @author A. Cedano
 * @since 2023.3
 */
class GetTodayUseCase @Inject constructor(
    private val mRepository: TodayRepository
) {
    suspend fun execute(request: TodayRequest): TodayResponse {
        return mRepository.getToday(request)
    }
}