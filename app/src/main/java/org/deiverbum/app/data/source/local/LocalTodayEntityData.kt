package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.source.TodayEntityData
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse
import javax.inject.Inject
/**
 * <p>Fuente de datos local para el módulo Today.</p>
 *
 * @author A. Cedano
 * @since 2023.3
 */
class LocalTodayEntityData @Inject constructor(
    private val todayDao: TodayDao
) : TodayEntityData {

    override suspend fun getToday(todayRequest: TodayRequest): TodayResponse {
        val todayResponse = TodayResponse()

        when (todayRequest.typeID) {
            0 -> {
                val dm = todayDao.getMixtoByDate(todayRequest.theDate)?.domainModelToday
                todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                if (todayRequest.isVoiceOn) {
                    todayResponse.dataForRead = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
            }

            1 -> {
                val dm = todayDao.getOficioByDate(todayRequest.theDate)?.domainModelToday
                todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                if (todayRequest.isVoiceOn) {
                    todayResponse.dataForRead = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
            }
            2 -> {
                val dm = todayDao.getLaudesByDate(todayRequest.theDate)?.domainModelToday
                todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                if (todayRequest.isVoiceOn) {
                    todayResponse.dataForRead = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
            }
            3 -> {
                val dm = todayDao.getTerciaByDate(todayRequest.theDate)?.domainModelToday
                todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                if (todayRequest.isVoiceOn) {
                    todayResponse.dataForRead = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
            }
            4 -> {
                val dm = todayDao.getSextaByDate(todayRequest.theDate)?.domainModelToday
                todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                if (todayRequest.isVoiceOn) {
                    todayResponse.dataForRead = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
            }
            5 -> {
                val dm = todayDao.getNonaByDate(todayRequest.theDate)?.domainModelToday
                todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                if (todayRequest.isVoiceOn) {
                    todayResponse.dataForRead = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
            }
            6 -> {
                val dm = todayDao.getVisperasByDate(todayRequest.theDate)?.domainModelToday
                todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                if (todayRequest.isVoiceOn) {
                    todayResponse.dataForRead = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
            }
            7 -> {
                TODO("Pasar Completas de archivo a BD")
                val dm = todayDao.getCompletasByDate(todayRequest.theDate)?.domainModel
                val c = dm?.today?.liturgyDay?.breviaryHour?.breviaryHour?.getCompletas()
                //dm.breviaryHour.breviaryHour.breviaryHour.
                //c..getAllForView()
                val ssb: SpannableStringBuilder = c?.getAllForView()!!
                var sb: StringBuilder? = null
                if (todayRequest.isVoiceOn) {
                    sb = c.getForRead()
                }
                return TodayResponse(ssb, sb)
            }

            9 -> {
                val dm = todayDao.getHomilyByDate(todayRequest.theDate)?.domainModeToday
                todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                if (todayRequest.isVoiceOn) {
                    todayResponse.dataForRead = dm.getAllForRead(false)
                }
            }
            10 -> {
                val dm = todayDao.getMassReadingByDate(todayRequest.theDate)?.domainModel
                todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                if (todayRequest.isVoiceOn) {
                    todayResponse.dataForRead = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
            }
            11 -> {
                val dm = todayDao.getCommentsByDate(todayRequest.theDate)?.domainModel
                todayResponse.dataForView = dm?.getAllForView(todayRequest)!!
                if (todayRequest.isVoiceOn) {
                    todayResponse.dataForRead = dm.getAllForRead
                }
            }
            else -> {
                todayResponse.dataForView = SpannableStringBuilder("no Data")
            }
        }
        return todayResponse
    }

    override suspend fun addToday(today: TodayResponse) {
    }
}