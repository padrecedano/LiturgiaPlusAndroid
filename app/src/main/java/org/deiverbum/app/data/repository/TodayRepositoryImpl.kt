package org.deiverbum.app.data.repository

import org.deiverbum.app.data.factory.TodayFactory
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse
import org.deiverbum.app.domain.repository.TodayRepository
import org.deiverbum.app.util.Source
import javax.inject.Inject

/**
 *
 * Implementación del Repositorio para el módulo Today.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
class TodayRepositoryImpl @Inject constructor(
    private val todayFactory: TodayFactory
) : TodayRepository {

    override suspend fun getToday(todayRequest: TodayRequest): TodayResponse {
        val todayResponse = todayFactory.create(Source.LOCAL).getToday(todayRequest)
        return if (todayResponse.success) {
            todayResponse
        } else
            todayFactory.create(Source.NETWORK)
                .getToday(todayRequest)//syncToday(todayRequest)

    }
    //return todayResponse



private suspend fun syncTodayy(todayRequest: TodayRequest): TodayResponse {
    return todayFactory.create(Source.NETWORK).getToday(todayRequest)
        .also { todayFromNetwork ->
            todayFactory.create(Source.LOCAL).addToday(todayFromNetwork)
        }
}

private suspend fun syncToday(todayRequest: TodayRequest): TodayResponse {
    val todayResponse = todayFactory.create(Source.NETWORK).getToday(todayRequest)
    return todayResponse
}
}