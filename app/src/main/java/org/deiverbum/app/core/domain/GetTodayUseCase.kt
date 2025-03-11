package org.deiverbum.app.core.domain

import org.deiverbum.app.core.model.data.TodayRequest
import org.deiverbum.app.core.model.universalis.UniversalisResponse
import javax.inject.Inject

/**
 * Caso de uso de **`Today`**.
 *
 * @author A. Cedano
 * @since 2024.1
 */
class GetTodayUseCase @Inject constructor(
    //private val mRepository: TodayRepository
) {
    suspend fun execute(request: TodayRequest): UniversalisResponse {
        return UniversalisResponse()
        //return mRepository.getToday(request)
    }
}