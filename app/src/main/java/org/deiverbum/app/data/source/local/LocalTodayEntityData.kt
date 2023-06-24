package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.source.TodayEntityData
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse
import org.deiverbum.app.model.Today
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
                    todayResponse.dataModel = dm//dm.getAllForView(todayRequest)
                    /*if (todayRequest.isVoiceOn) {
                        todayResponse.dataForRead =
                            dm.getAllForRead(todayRequest.isMultipleInvitatory)
                    }*/
                }

                1 -> {
                    todayResponse.dataModel = todayDao.getOficioByDate(todayRequest.theDate)?.domainModelToday!!
                    /*todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                    if (todayRequest.isVoiceOn) {
                        todayResponse.dataForRead =
                            dm.getAllForRead(todayRequest.isMultipleInvitatory)
                    }*/
                }
                2 -> {
                    todayResponse.dataModel = todayDao.getLaudesByDate(todayRequest.theDate)?.domainModelToday!!
                    /*todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                    if (todayRequest.isVoiceOn) {
                        todayResponse.dataForRead =
                            dm.getAllForRead(todayRequest.isMultipleInvitatory)
                    }*/
                }
                3 -> {
                    todayResponse.dataModel = todayDao.getTerciaByDate(todayRequest.theDate)?.domainModelToday!!
                    /*todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                    if (todayRequest.isVoiceOn) {
                        todayResponse.dataForRead = dm.getAllForRead(hasInvitatory = false)
                    }*/
                }
                4 -> {
                    todayResponse.dataModel = todayDao.getSextaByDate(todayRequest.theDate)?.domainModelToday!!
                    /*todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                    if (todayRequest.isVoiceOn) {
                        todayResponse.dataForRead = dm.getAllForRead(hasInvitatory = false)
                    }*/
                }
                5 -> {
                    todayResponse.dataModel = todayDao.getNonaByDate(todayRequest.theDate)?.domainModelToday!!
                    /*todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                    if (todayRequest.isVoiceOn) {
                        todayResponse.dataForRead = dm.getAllForRead(hasInvitatory = false)
                    }*/
                }
                6 -> {
                    todayResponse.dataModel = todayDao.getVisperasByDate(todayRequest.theDate)?.domainModelToday!!
                    /*todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                    if (todayRequest.isVoiceOn) {
                        todayResponse.dataForRead = dm.getAllForRead(hasInvitatory = false)
                    }*/
                }
                7 -> {
                    todayResponse.dataModel = todayDao.getCompletasByDate(todayRequest.theDate)?.domainModelToday!!
                    /*val c = dm?.today?.liturgyDay?.breviaryHour?.breviaryHour?.getCompletas()
                    val ssb: SpannableStringBuilder = c?.getAllForView()!!
                    var sb: StringBuilder? = null
                    if (todayRequest.isVoiceOn) {
                        sb = c.getForRead()
                    }
                    return TodayResponse(ssb, sb)*/
                }

                9 -> {
                    todayResponse.dataModel = todayDao.getHomilyByDate(todayRequest.theDate)?.domainModeToday!!
                    /*todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                    if (todayRequest.isVoiceOn) {
                        todayResponse.dataForRead = dm.getAllForRead(hasInvitatory = false)
                    }*/
                }
                10 -> {
                    todayResponse.dataModel =  todayDao.getMassReadingByDate(todayRequest.theDate)?.domainModel!!
                    /*todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                    if (todayRequest.isVoiceOn) {
                        todayResponse.dataForRead = dm.getAllForRead(hasInvitatory = false)
                    }*/
                }
                11 -> {
                    todayResponse.dataModel =  Today()//todayDao.getCommentsByDate(todayRequest.theDate)?.domainModel!!
                    /*todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                    if (todayRequest.isVoiceOn) {
                        todayResponse.dataForRead = dm.getAllForRead
                    }*/
                }
                else -> {
                    //todayResponse.dataForView = SpannableStringBuilder("no Data")
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