package org.deiverbum.app.domain

import org.deiverbum.app.core.data.repository.TodayRepository
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.model.UniversalisResponse
import javax.inject.Inject

/**
 * Caso de uso de **`Today`**.
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class GetTodayUseCase @Inject constructor(
    private val mRepository: TodayRepository
) {
    suspend fun execute(request: TodayRequest): UniversalisResponse {
        return mRepository.getToday(request)
    }
}