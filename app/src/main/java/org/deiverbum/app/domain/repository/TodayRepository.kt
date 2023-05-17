package org.deiverbum.app.domain.repository

import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse

/**
 * <p>Interfaz para el repositorio del módulo Today.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
interface TodayRepository {

    suspend fun getToday(todayRequest: TodayRequest): TodayResponse

}