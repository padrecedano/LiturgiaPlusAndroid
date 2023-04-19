package org.deiverbum.app.data.source.network

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.api.TodayApi
import org.deiverbum.app.data.mapper.HomilyResponseMapper.toHomily
import org.deiverbum.app.data.source.HomilyEntityData
import org.deiverbum.app.domain.model.Homily
import org.deiverbum.app.domain.model.HomilyRequest
import javax.inject.Inject

class NetworkHomilyEntityData @Inject constructor(
    private val todayApi: TodayApi
) : HomilyEntityData {

    override suspend fun getHomily(homilyRequest: HomilyRequest): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        return sb;
    }

    override suspend fun addHomily(homily: SpannableStringBuilder) {
        //no op
    }
}