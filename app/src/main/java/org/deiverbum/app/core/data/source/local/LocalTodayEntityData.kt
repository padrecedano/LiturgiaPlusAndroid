package org.deiverbum.app.core.data.source.local

import org.deiverbum.app.core.data.source.TodayEntityData
import org.deiverbum.app.core.database.dao.TodayDao
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.model.TodayRequest
import org.deiverbum.app.core.model.UniversalisResponse
import org.deiverbum.app.core.model.data.Universalis
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
                    val c = todayDao.getMixtoByDate(todayRequest.theDate)
                    val u = c.asExternalModel()
                    todayResponse.dataModel = u
                    //todayResponse.dataModel =todayDao.getMixtoByDate(todayRequest.theDate).domainModel
                }

                1 -> {
                    todayResponse.dataModel =
                        todayDao.getOficioByDate(todayRequest.theDate).asExternalModel()
                    //todayResponse.dataModel = todayDao.getOficioByDate(todayRequest.theDate).domainModel
                }

                2 -> {
                    todayResponse.dataModel =
                        todayDao.getLaudesByDate(todayRequest.theDate).asExternalModel()

                    //todayResponse.dataModel =todayDao.getLaudesByDate(todayRequest.theDate).domainModel
                }

                3 -> {
                    todayResponse.dataModel =
                        todayDao.getTerciaByDate(todayRequest.theDate).domainModel
                }
                4 -> {
                    todayResponse.dataModel =
                        todayDao.getSextaByDate(todayRequest.theDate).domainModel
                }
                5 -> {
                    val c = todayDao.getNonaByDate(todayRequest.theDate)
                    val u = Universalis()
                    //u.liturgy=c.asExternalModel()
                    todayResponse.dataModel = u
                    //todayResponse.dataModel =todayDao.getNonaByDate(todayRequest.theDate).domainModel
                }
                6 -> {
                    todayResponse.dataModel =
                        todayDao.getVisperasByDate(todayRequest.theDate).domainModel
                }
                7 -> {
                    //val u=todayDao.getUniversalisByDate(todayRequest.theDate)
                    //val h=todayDao.getHymById().map { it.asExternalModel() }
                    //h.collect(it.)
                    //h.collect { v -> println(v.toString()+"bbbb") }
                    val c = todayDao.getCompletasByDate(todayRequest.theDate)
                    val u = Universalis()
                    //u.liturgy=c.asExternalModel()
                    todayResponse.dataModel = u
                    //todayResponse.dataModel =
                    //    todayDao.getCompletasByDate(todayRequest.theDate).domainModel
                }
                9 -> {
                    todayResponse.dataModel =
                        todayDao.getHomilyByDate(todayRequest.theDate).domainModel
                }
                10 -> {
                    todayResponse.dataModel =
                        todayDao.getMassReadingByDate(todayRequest.theDate).asExternalModel()
                    //todayResponse.dataModel =todayDao.getMassReadingByDate(todayRequest.theDate).domainModel
                }
                11 -> {

                    val c = todayDao.getCommentsByDate(todayRequest.theDate)
                    //c.a
                    //val b=c.asExternalModel()
                    //val l=org.deiverbum.app.core.model.data.Liturgy()
                    //l.lhBreviarium=b
                    val u = Universalis()
                    //u.liturgy=c.asExternalModel()
                    todayResponse.dataModel = u
                    //todayResponse.dataModel =todayDao.getCommentsByDate(todayRequest.theDate).domainModel
                }
                12 -> {
                    val monthAndDay = Utils.getMonthAndDay(todayRequest.theDate.toString())
                    todayResponse.dataModel = todayDao.getSaintByDate(
                        monthAndDay?.get(0),
                        monthAndDay?.get(1)
                    )?.domainModel!!
                }
            }
            return todayResponse
        } catch (e: Exception) {
            todayResponse.success = false
            return todayResponse
        }
    }

    //fun getTopics(): Flow<LHHymn> = todayDao.getHymById().map{ it.map(LHHymnEntity::asExternalModel) }


    override suspend fun addToday(today: UniversalisResponse) {
    }
}