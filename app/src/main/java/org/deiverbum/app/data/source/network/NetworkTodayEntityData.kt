package org.deiverbum.app.data.source.network

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.api.TodayApi
import org.deiverbum.app.data.source.TodayEntityData
import org.deiverbum.app.domain.model.TodayRequest
import javax.inject.Inject

class NetworkTodayEntityData @Inject constructor(
    private val todayApi: TodayApi
) : TodayEntityData {


    override suspend fun getToday(todayRequest: TodayRequest): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        return sb;
    }

    override suspend fun addToday(today: SpannableStringBuilder) {
        //no op
    }
}