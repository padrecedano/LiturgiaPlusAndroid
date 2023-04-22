package org.deiverbum.app.domain.repository

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse

interface TodayRepository {

    suspend fun getToday(todayRequest: TodayRequest): TodayResponse

}