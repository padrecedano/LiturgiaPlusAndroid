package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class Laudes : BreviaryHour() {
    var  invitatorio: LHInvitatory? = null

    var lecturaBreve: BiblicalShort? = null
    var gospelCanticle: LHGospelCanticle? = null
    var preces: LHIntercession? = null
    private val tituloHora: SpannableStringBuilder
        get() = Utils.toH1Red(Constants.TITLE_LAUDES)
    private val tituloHoraForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_LAUDES)


    fun getForView(liturgyTime: LiturgyTime, hasSaint: Boolean): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        try {
            //TODO hacer esto en la clase LHResponsoryShort, revisar Completas y
            // demás horas
            //biblicaBreve.normalizeByTime(metaLiturgia.calendarTime);
            this.hasSaint = hasSaint
            //invitatorio!!.normalizeByTime(liturgyTime.timeID)
            salmodia!!.normalizeByTime(liturgyTime.timeID)

            //sb.append(today.getAllForView());
            sb.append(Utils.LS2)
            if (santo != null && this.hasSaint) {
                invitatorio!!.normalizeIsSaint(santo!!.theName)
                sb.append(santo!!.vidaSmall)
                sb.append(Constants.LS)
            }
            sb.append(tituloHora)
            sb.append(Utils.fromHtmlToSmallRed(metaInfo))
            sb.append(Utils.LS2)
            sb.append(getSaludoOficio())
            sb.append(Utils.LS2)
            sb.append(invitatorio?.getForView(liturgyTime.timeID))
            sb.append(Utils.LS2)
            sb.append(himno?.all)
            sb.append(Utils.LS2)
            sb.append(salmodia!!.all)
            sb.append(Utils.LS2)
            sb.append(lecturaBreve!!.getAllWithHourCheck(2))
            sb.append(Utils.LS2)
            sb.append(gospelCanticle?.all)
            sb.append(Utils.LS2)
            sb.append(preces?.all)
            sb.append(Utils.LS2)
            sb.append(PadreNuestro.all)
            sb.append(Utils.LS2)
            sb.append(oracion!!.all)
            sb.append(Utils.LS2)
            sb.append(getConclusionHorasMayores())
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    //sb.append(today.getAllForRead());
    val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                //sb.append(today.getAllForRead());
                if (santo != null && hasSaint) {
                    sb.append(santo!!.vida)
                }
                sb.append(tituloHoraForRead)
                sb.append(getSaludoOficioForRead())
                sb.append(invitatorio?.allForRead)
                sb.append(himno?.allForRead)
                sb.append(salmodia!!.allForRead)
                sb.append(lecturaBreve!!.getAllForRead())
                sb.append(gospelCanticle?.allForRead)
                sb.append(preces?.allForRead)
                sb.append(PadreNuestro.allForRead)
                sb.append(oracion!!.allForRead)
                sb.append(getConclusionHorasMayoresForRead())
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
}