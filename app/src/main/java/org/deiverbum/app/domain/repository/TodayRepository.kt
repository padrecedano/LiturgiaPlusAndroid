package org.deiverbum.app.domain.repository

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.model.TodayRequest

interface TodayRepository {

    suspend fun getToday(todayRequest: TodayRequest): SpannableStringBuilder

}