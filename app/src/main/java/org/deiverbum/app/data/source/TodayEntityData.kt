package org.deiverbum.app.data.source

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse

interface TodayEntityData {

    suspend fun getToday(todayRequest: TodayRequest): TodayResponse

    suspend fun addToday(today: TodayResponse)
}