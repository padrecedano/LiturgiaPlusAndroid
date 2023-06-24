package org.deiverbum.app.data.source

import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse
import org.deiverbum.app.model.Today

/**
 * Interfaz para manejar las fuentes de datos relativa a `Today`.
 **
 * @author A. Cedano
 * @since 2023.1.3
 */
interface TodayEntityData {

    suspend fun getToday(todayRequest: TodayRequest): TodayResponse

    suspend fun addToday(today: TodayResponse)
}