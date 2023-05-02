package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

class Oficio : BreviaryHour() {
    var invitatorio: LHInvitatory? = null
    var teDeum: TeDeum? = null
    @Ignore
    var hasTeDeum: Int = 0

    private val tituloHora: SpannableStringBuilder
        get() = Utils.toH1Red(Constants.TITLE_OFICIO)
    private val tituloHoraForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_OFICIO)

    fun getForView(
        liturgyTime: LiturgyTime?,
        hasSaint: Boolean
    ): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        this.hasSaint = hasSaint
        try {
            invitatorio!!.normalizeByTime(liturgyTime!!.timeID)
            salmodia!!.normalizeByTime(liturgyTime.timeID)
            lhOfficeOfReading!!.normalizeByTime(liturgyTime.timeID)
            sb.append(Utils.LS2)
            if (santo != null && this.hasSaint) {
                invitatorio!!.normalizeIsSaint(santo!!.theName)
                sb.append(santo?.vidaSmall)
                sb.append(Constants.LS)
            }
            sb.append(tituloHora)
            sb.append(Utils.fromHtmlToSmallRed(metaInfo))
            sb.append(Utils.LS2)
            sb.append(getSaludoOficio())
            sb.append(Utils.LS2)
            sb.append(invitatorio!!.all)
            sb.append(Utils.LS2)
            sb.append(himno!!.all)
            sb.append(Utils.LS2)
            sb.append(salmodia!!.getAll(1))
            sb.append(Utils.LS2)
            sb.append(lhOfficeOfReading!!.getAll(liturgyTime.timeID))
            if (hasTeDeum!=0) {
                sb.append(TeDeum().all)
            }
            sb.append(oracion?.all)
            sb.append(Utils.LS2)
            sb.append(getConclusionHorasMayores())
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                if (santo != null && hasSaint) {
                    sb.append(santo!!.vida)
                }
                sb.append(tituloHoraForRead)
                sb.append(getSaludoOficioForRead())
                sb.append(invitatorio!!.allForRead)
                sb.append(himno!!.allForRead)
                sb.append(salmodia!!.allForRead)
                sb.append(lhOfficeOfReading!!.allForRead)
                if (teDeum!=null) {
                    sb.append(teDeum?.allForRead)
                }
                sb.append(oracion?.allForRead)
                sb.append(getConclusionHorasMayoresForRead())
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
}