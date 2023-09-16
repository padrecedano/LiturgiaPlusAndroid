package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class Mixto : BreviaryHour() {
    var misaLecturas: List<MassReading?>? = null
    override fun getLaudes(): Laudes? {
        return laudes
    }

    override fun setLaudes(laudes: Laudes?) {
        this.laudes = laudes
    }

    override fun getOficio(): Oficio? {
        return oficio
    }

    override fun setOficio(oficio: Oficio?) {
        this.oficio = oficio
    }

    val tituloHora: SpannableStringBuilder
        get() = Utils.toH1Red(Constants.TITLE_MIXTO)
    val tituloHoraForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_MIXTO)
    val evangeliosForView: SpannableStringBuilder
        get() {
            val ssb = SpannableStringBuilder()
            for (item in misaLecturas!!) {
                ssb.append(item?.getAll(0))
            }
            return ssb
        }
    val evangeliosForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            for (item in misaLecturas!!) {
                sb.append(item?.getAllForRead())
            }
            return sb
        }

    fun getForView(liturgyTime: LiturgyTime?, hasSaint: Boolean): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        try {
            this.hasSaint = hasSaint
            val invitatory = oficio?.invitatorio
            //invitatory?.normalizeByTime(liturgyTime?.timeID!!)
            laudes!!.salmodia?.normalizeByTime(liturgyTime!!.timeID)
            sb.append(Utils.LS2)
            if (santo != null && this.hasSaint) {
                invitatory?.normalizeIsSaint(santo!!.theName)
                sb.append(santo?.vidaSmall)
                sb.append(Utils.LS)
            }
            sb.append(mixto!!.tituloHora)
            sb.append(Utils.fromHtmlToSmallRed(metaInfo))
            sb.append(Utils.LS2)
            sb.append(laudes!!.getSaludoOficio())
            sb.append(Utils.LS2)
            sb.append(oficio!!.invitatorio?.all)
            sb.append(Utils.LS2)
            sb.append(laudes!!.himno!!.all)
            sb.append(Utils.LS2)
            sb.append(laudes!!.salmodia?.all)
            sb.append(laudes!!.lecturaBreve?.getAllWithHourCheck(2))
            sb.append(Utils.LS2)
            sb.append(oficio!!.lhOfficeOfReading?.getAll(liturgyTime!!.timeID))
            sb.append(mixto!!.evangeliosForView)
            sb.append(Utils.LS)
            sb.append(laudes!!.gospelCanticle?.all)
            sb.append(Utils.LS2)
            sb.append(laudes!!.preces?.all)
            sb.append(Utils.LS2)
            sb.append(PadreNuestro.all)
            sb.append(Utils.LS2)
            sb.append(laudes!!.oracion?.all)
            sb.append(Utils.LS2)
            sb.append(getConclusionHorasMayores())
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

}