package org.deiverbum.app.data.source.network

import org.deiverbum.app.data.api.TodayApi
import org.deiverbum.app.data.mapper.BibleResponseMapper.toBible
import org.deiverbum.app.data.source.BibleEntityData
import org.deiverbum.app.domain.model.Bible
import org.deiverbum.app.domain.model.BibleRequest
import javax.inject.Inject

class NetworkBibleEntityData @Inject constructor(
    private val todayApi: TodayApi
) : BibleEntityData {

    override suspend fun getBible(bibleRequest: BibleRequest): List<Bible> {
        return todayApi.getBible(bibleRequest.date)
            .toBible()
    }

    override suspend fun addBible(bibles: List<Bible>) {
        //no op
    }
}