package org.deiverbum.app.data.source.local

import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.source.TodayEntityData
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse
import org.deiverbum.app.util.Utils
import javax.inject.Inject

/**
 * <p>Fuente de datos local para el módulo Today.</p>
 *
 * @author A. Cedano
 * @since 2023.1.3
 */
class LocalTodayEntityData @Inject constructor(
    private val todayDao: TodayDao
) : TodayEntityData {

    override suspend fun getToday(todayRequest: TodayRequest): TodayResponse {
        val todayResponse = TodayResponse()
        try {
            when (todayRequest.typeID) {
                0 -> {
                    val dm = todayDao.getMixtoByDate(todayRequest.theDate)?.domainModelToday!!
                    todayResponse.dataModel = dm
                }

                1 -> {
                    todayResponse.dataModel = todayDao.getOficioByDate(todayRequest.theDate)?.domainModelToday!!
                }
                2 -> {
                    todayResponse.dataModel = todayDao.getLaudesByDate(todayRequest.theDate)?.domainModelToday!!
                }
                3 -> {
                    todayResponse.dataModel = todayDao.getTerciaByDate(todayRequest.theDate)?.domainModelToday!!
                }
                4 -> {
                    todayResponse.dataModel = todayDao.getSextaByDate(todayRequest.theDate)?.domainModelToday!!
                }
                5 -> {
                    todayResponse.dataModel = todayDao.getNonaByDate(todayRequest.theDate)?.domainModelToday!!
                }
                6 -> {
                    todayResponse.dataModel = todayDao.getVisperasByDate(todayRequest.theDate)?.domainModelToday!!
                }
                7 -> {
                    todayResponse.dataModel = todayDao.getCompletasByDate(todayRequest.theDate)?.domainModelToday!!
                }

                9 -> {
                    todayResponse.dataModel = todayDao.getHomilyByDate(todayRequest.theDate)?.domainModeToday!!
                }
                10 -> {
                    todayResponse.dataModel =  todayDao.getMassReadingByDate(todayRequest.theDate)?.domainModel!!
                }
                11 -> {
                    todayResponse.dataModel =  todayDao.getCommentsByDate(todayRequest.theDate)?.domainModelToday!!
                }

                12 -> {
                    val monthAndDay= Utils.getMonthAndDay(todayRequest.theDate.toString())
                    todayResponse.dataModel =  todayDao.getSaintByDate(monthAndDay[0],monthAndDay[1])?.domainModelToday!!
                }
            }
            return todayResponse
        } catch (e: Exception) {
            todayResponse.success=false
            return todayResponse
        }
    }

    override suspend fun addToday(today: TodayResponse) {
    }
}