package org.deiverbum.app.data.source.network

import org.deiverbum.app.data.api.TodayApi
import org.deiverbum.app.data.source.TodayEntityData
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse
import org.deiverbum.app.model.Today
import org.deiverbum.app.util.LiturgyHelper
import org.deiverbum.app.util.Source
import javax.inject.Inject

class NetworkTodayEntityData @Inject constructor(
    private val todayApi: TodayApi
) : TodayEntityData {

    override suspend fun getToday(todayRequest: TodayRequest): TodayResponse {
        val endPoint = LiturgyHelper.map[todayRequest.typeID]
        return try {
            val todayResponse=TodayResponse()
            val apiResponse = todayApi.getToday(endPoint, todayRequest.theDate.toString())
            todayResponse.dataModel = apiResponse!!
            todayResponse

        } catch (e: Exception) {
            TodayResponse(Today(),Source.NETWORK,false)
        }
    }

    override suspend fun addToday(today: TodayResponse) {
        //no op
    }
}