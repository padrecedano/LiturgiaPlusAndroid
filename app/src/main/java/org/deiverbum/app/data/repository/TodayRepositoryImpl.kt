package org.deiverbum.app.data.repository

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.factory.HomilyFactory
import org.deiverbum.app.data.factory.TodayFactory
import org.deiverbum.app.domain.model.HomilyRequest
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.repository.HomilyRepository
import org.deiverbum.app.domain.repository.TodayRepository
import org.deiverbum.app.util.Source
import javax.inject.Inject

class TodayRepositoryImpl @Inject constructor(
    private val todayFactory: TodayFactory
) : TodayRepository {

    override suspend fun getToday(todayRequest: TodayRequest): SpannableStringBuilder {
        return todayFactory.create(Source.LOCAL).getToday(todayRequest)
            .ifEmpty { syncToday(todayRequest) }
    }

    private suspend fun syncToday(todayRequest: TodayRequest): SpannableStringBuilder {
        return todayFactory.create(Source.NETWORK).getToday(todayRequest)
            .also { todayFromNetwork ->
                todayFactory.create(Source.LOCAL).addToday(todayFromNetwork)
            }
    }
}