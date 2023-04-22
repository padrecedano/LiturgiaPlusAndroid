package org.deiverbum.app.data.source.network

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.api.TodayApi
import org.deiverbum.app.data.source.TodayEntityData
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse
import org.deiverbum.app.util.LiturgyHelper
import timber.log.Timber
import javax.inject.Inject

class NetworkTodayEntityData @Inject constructor(
    private val todayApi: TodayApi
) : TodayEntityData {

    override suspend fun getToday(todayRequest: TodayRequest): TodayResponse {
        val endPoint = LiturgyHelper.map[todayRequest.typeID]
        Timber.d(endPoint)
        val apiResponse = todayApi.getToday(endPoint, todayRequest.theDate.toString())
        val ssb: SpannableStringBuilder
        return try {
            ssb = apiResponse?.getAllForView(
                todayRequest.isMultipleInvitatory,
                todayRequest.isNightMode
            )!!
            var sb: StringBuilder? = null
            if (todayRequest.isVoiceOn) {
                sb = apiResponse.getAllForRead(todayRequest.isMultipleInvitatory)
            }
            TodayResponse(ssb, sb)
        } catch (e: Exception) {
            TodayResponse(SpannableStringBuilder(e.stackTraceToString()), null)
        }
    }

    override suspend fun addToday(today: TodayResponse) {
        //no op
    }
}