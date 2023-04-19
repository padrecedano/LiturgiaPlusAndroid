package org.deiverbum.app.data.source

import android.text.SpannableStringBuilder
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.model.TodayRequest

interface TodayEntityData {

    suspend fun getToday(todayRequest: TodayRequest): SpannableStringBuilder

    suspend fun addToday(today: SpannableStringBuilder)
}