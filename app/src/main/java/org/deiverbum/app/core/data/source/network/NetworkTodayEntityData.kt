package org.deiverbum.app.core.data.source.network

import org.deiverbum.app.core.data.source.TodayEntityData
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.model.UniversalisResponse
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.network.api.TodayApi
import org.deiverbum.app.util.LiturgyHelper.Companion.liturgyByType
import org.deiverbum.app.util.Source
import javax.inject.Inject

class NetworkTodayEntityData @Inject constructor(
    private val todayApi: TodayApi
) : TodayEntityData {

    override suspend fun getToday(todayRequest: TodayRequest): UniversalisResponse {
        val endPoint = liturgyByType(todayRequest.typeID)
        return try {
            val todayResponse = UniversalisResponse()
            /*
            //TODO:
                        val u=todayApi.getUniversalis(endPoint,todayRequest.theDate.toString())
                        Timber.tag("bbb").d(u!!.liturgyDay.name)
                        //Timber.tag("bbb").d(u!!.liturgyDay.breviaryHour!!.oficio!!.himno!!.hymn)
                        Timber.tag("bbb").d(u.fake)
            */
            val apiResponse = todayApi.getToday(endPoint, todayRequest.theDate.toString())
            todayResponse.dataModel = apiResponse!!
            todayResponse

        } catch (e: Exception) {
            UniversalisResponse(Universalis(), Source.NETWORK, false)
        }
    }

    override suspend fun addToday(today: UniversalisResponse) {
        //no op
    }
}