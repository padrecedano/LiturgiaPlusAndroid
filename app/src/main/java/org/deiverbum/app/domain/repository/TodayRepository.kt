package org.deiverbum.app.domain.repository

import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse

interface TodayRepository {

    suspend fun getToday(todayRequest: TodayRequest): TodayResponse

}