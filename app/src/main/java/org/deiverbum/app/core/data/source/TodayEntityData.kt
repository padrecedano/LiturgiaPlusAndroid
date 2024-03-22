package org.deiverbum.app.core.data.source

import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.model.UniversalisResponse

/**
 * Interfaz para manejar las fuentes de datos relativa a `Today`.
 **
 * @author A. Cedano
 * @since 2024.1
 */
interface TodayEntityData {

    suspend fun getToday(todayRequest: TodayRequest): UniversalisResponse

    suspend fun addToday(today: UniversalisResponse)
}