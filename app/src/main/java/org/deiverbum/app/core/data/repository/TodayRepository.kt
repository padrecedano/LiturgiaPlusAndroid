package org.deiverbum.app.core.data.repository

import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.model.UniversalisResponse

/**
 * Interfaz para el repositorio del módulo Today.
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
interface TodayRepository {

    suspend fun getToday(todayRequest: TodayRequest): UniversalisResponse

}