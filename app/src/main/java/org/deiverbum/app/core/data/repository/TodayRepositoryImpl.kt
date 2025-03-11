package org.deiverbum.app.core.data.repository

import org.deiverbum.app.core.data.factory.TodayFactory
import org.deiverbum.app.core.model.data.TodayRequest
import org.deiverbum.app.core.model.universalis.UniversalisResponse
import org.deiverbum.app.util.Source
import javax.inject.Inject

/**
 *
 * Implementación del Repositorio para el módulo Today.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */
class TodayRepositoryImpl @Inject constructor(
    private val todayFactory: TodayFactory
) : TodayRepository {

    /**
     * En primer lugar, solicita el [TodayRequest] a [Source.LOCAL] para buscar los datos en la base de datos.
     * Si se obtiene un [UniversalisResponse.success] retorna la respuesta,
     * de lo contrario enviará el [TodayRequest] a [Source.NETWORK]
     * para buscar los datos en el servidor remoto.

     */
    override suspend fun getToday(todayRequest: TodayRequest): UniversalisResponse {
        //val todayResponse = todayFactory.create(Source.NETWORK).getToday(todayRequest)
        val todayResponse = todayFactory.create(Source.LOCAL).getToday(todayRequest)
        //val todayResponse=UniversalisResponse(Universalis(),Source.NETWORK,true)
        return if (todayResponse.success) {
            todayResponse
        } else
            todayFactory.create(Source.NETWORK).getToday(todayRequest)
    }
}