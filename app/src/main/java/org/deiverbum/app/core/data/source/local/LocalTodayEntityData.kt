package org.deiverbum.app.core.data.source.local

import org.deiverbum.app.core.data.source.TodayEntityData
import org.deiverbum.app.core.database.dao.TodayDao
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.model.UniversalisResponse
import org.deiverbum.app.util.Constants.EASTER_CODE
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

    override suspend fun getToday(todayRequest: TodayRequest): UniversalisResponse {
        val todayResponse = UniversalisResponse()
        try {
            when (todayRequest.typeID) {
                0 -> {
                    todayResponse.dataModel =
                        todayDao.getMixtumByDate(todayRequest.theDate).asExternalModel()
                }

                1 -> {
                    val dm = todayDao.getOfficiumByDate(todayRequest.theDate).asExternalModel()
                    if (dm.oBiblicalFK == EASTER_CODE) {
                        todayResponse.dataModel =
                            todayDao.getOfficiumPascuaByDate(EASTER_CODE).asExternalModel()
                    } else {
                        todayResponse.dataModel = dm
                    }
                }

                2 -> {
                    todayResponse.dataModel =
                        todayDao.getLaudesByDate(todayRequest.theDate).asExternalModel()
                }

                3 -> {
                    todayResponse.dataModel =
                        todayDao.getTertiamByDate(todayRequest.theDate).asExternalModel()
                }
                4 -> {
                    todayResponse.dataModel =
                        todayDao.getSextamByDate(todayRequest.theDate).asExternalModel()
                }
                5 -> {
                    todayResponse.dataModel =
                        todayDao.getNonamByDate(todayRequest.theDate).asExternalModel()
                }
                6 -> {
                    todayResponse.dataModel =
                        todayDao.getVesperasByDate(todayRequest.theDate).asExternalModel()
                }
                7 -> {
                    todayResponse.dataModel =
                        todayDao.getCompletoriumByDate(todayRequest.theDate).asExternalModel()
                }
                9 -> {
                    todayResponse.dataModel =
                        todayDao.getHomiliaeByDate(todayRequest.theDate).asExternalModel()
                }
                10 -> {
                    todayResponse.dataModel =
                        todayDao.getMissaeLectionumByDate(todayRequest.theDate).asExternalModel()
                    //todayResponse.dataModel =todayDao.getMassReadingByDate(todayRequest.theDate).domainModel
                }
                11 -> {
                    todayResponse.dataModel =
                        todayDao.getCommentariiByDate(todayRequest.theDate).asExternalModel()
                }
                12 -> {
                    val monthAndDay = Utils.getMonthAndDay(todayRequest.theDate.toString())
                    todayResponse.dataModel = todayDao.getSanctiByDate(
                        monthAndDay?.get(0),
                        monthAndDay?.get(1)
                    ).asExternalModel()
                }
            }
            return todayResponse
        } catch (e: Exception) {
            todayResponse.success = false
            return todayResponse
        }
    }
    override suspend fun addToday(today: UniversalisResponse) {
    }
}