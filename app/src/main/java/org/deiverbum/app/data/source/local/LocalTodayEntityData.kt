package org.deiverbum.app.data.source.local

import android.text.SpannableStringBuilder
import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.source.TodayEntityData
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.model.TodayResponse
import org.deiverbum.app.model.Homily
import org.deiverbum.app.model.Today
import javax.inject.Inject

class LocalTodayEntityData @Inject constructor(
    private val todayDao: TodayDao
) : TodayEntityData {

    override suspend fun getToday(todayRequest: TodayRequest): TodayResponse {
        when (todayRequest.typeID) {
            0 -> {
                val dm=todayDao.getMixtoByDate(todayRequest.theDate)?.domainModelToday
                val ssb:SpannableStringBuilder=dm?.getAllForView(todayRequest.isMultipleInvitatory,todayRequest.isNightMode)!!
                var sb:StringBuilder?=null
                if (todayRequest.isVoiceOn) {
                    sb = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
                return TodayResponse(ssb,sb)

            }

            1 ->  {
                val dm=todayDao.getOficioByDate(todayRequest.theDate)?.domainModelToday
                val ssb:SpannableStringBuilder=dm?.getAllForView(todayRequest.isMultipleInvitatory,todayRequest.isNightMode)!!
                var sb:StringBuilder?=null
                if (todayRequest.isVoiceOn) {
                    sb = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
                return TodayResponse(ssb,sb)
            }
            2 ->  {
                val dm=todayDao.getLaudesByDate(todayRequest.theDate)?.domainModelToday
                val ssb:SpannableStringBuilder=dm?.getAllForView(todayRequest.isMultipleInvitatory,todayRequest.isNightMode)!!
                var sb:StringBuilder?=null
                if (todayRequest.isVoiceOn) {
                    sb = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
                return TodayResponse(ssb,sb)
            }
            3 ->  {
                val dm=todayDao.getTerciaByDate(todayRequest.theDate)?.domainModelToday
                val ssb:SpannableStringBuilder=dm?.getAllForView(todayRequest.isMultipleInvitatory,todayRequest.isNightMode)!!
                var sb:StringBuilder?=null
                if (todayRequest.isVoiceOn) {
                    sb = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
                return TodayResponse(ssb,sb)
            }
            4 ->  {
                val dm=todayDao.getSextaByDate(todayRequest.theDate)?.domainModelToday
                val ssb:SpannableStringBuilder=dm?.getAllForView(todayRequest.isMultipleInvitatory,todayRequest.isNightMode)!!
                var sb:StringBuilder?=null
                if (todayRequest.isVoiceOn) {
                    sb = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
                return TodayResponse(ssb,sb)
            }
            5 ->  {
                val dm=todayDao.getNonaByDate(todayRequest.theDate)?.domainModelToday
                val ssb:SpannableStringBuilder=dm?.getAllForView(todayRequest.isMultipleInvitatory,todayRequest.isNightMode)!!
                var sb:StringBuilder?=null
                if (todayRequest.isVoiceOn) {
                    sb = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
                return TodayResponse(ssb,sb)
            }
            6 ->  {
                val dm=todayDao.getVisperasByDate(todayRequest.theDate)?.domainModelToday
                val ssb:SpannableStringBuilder=dm?.getAllForView(todayRequest.isMultipleInvitatory,todayRequest.isNightMode)!!
                var sb:StringBuilder?=null
                if (todayRequest.isVoiceOn) {
                    sb = dm.getAllForRead(todayRequest.isMultipleInvitatory)
                }
                return TodayResponse(ssb,sb)
            }
            7 ->  {
                TODO("Pasar Completas de archivo a BD")
                val dm=todayDao.getCompletasByDate(todayRequest.theDate)?.domainModel
                val c=dm?.today?.liturgyDay?.breviaryHour?.breviaryHour?.getCompletas()
                //dm.breviaryHour.breviaryHour.breviaryHour.
                //c..getAllForView()
                val ssb:SpannableStringBuilder=c?.getAllForView()!!
                var sb:StringBuilder?=null
                if (todayRequest.isVoiceOn) {
                    sb = c.getForRead()
                }
                return TodayResponse(ssb,sb)
            }

            //2 -> //return todayDao.getLaudesOfToday(todayRequest.theDate)?.domainModelToday?.getAllForView(false,false)!!
            8 -> {
                val dm =todayDao.getHomilyByDate(todayRequest.theDate)?.domainModel
            val ssb: SpannableStringBuilder = dm?.getForView(todayRequest.isNightMode)!!
            var sb:StringBuilder?=null
            if (todayRequest.isVoiceOn) {
                sb = dm.allForRead
            }
            return TodayResponse(ssb,sb)
            //return dm!!.getForView(false)
            }
            else -> {
                return TodayResponse(SpannableStringBuilder("no Data"),null)
            }
        }
    }

    override suspend fun addToday(today: TodayResponse) {
    }
}