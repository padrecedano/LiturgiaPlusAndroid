package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

class Visperas : BreviaryHour() {
    private var lecturaBreve: BiblicalShort? = null
    private var gospelCanticle: LHGospelCanticle? = null
    private var preces: LHIntercession? = null
    private var isPrevious = 0
    fun setIsPrevious(isPrevious: Int) {
        this.isPrevious = isPrevious
    }

    @Suppress("unused")
    fun getLecturaBreve(): BiblicalShort? {
        return lecturaBreve
    }

    fun setLecturaBreve(lecturaBreve: BiblicalShort?) {
        this.lecturaBreve = lecturaBreve
    }

    @Suppress("unused")
    fun getPreces(): LHIntercession? {
        return preces
    }

    fun setPreces(preces: LHIntercession?) {
        this.preces = preces
    }

    @Suppress("unused")
    fun getGospelCanticle(): LHGospelCanticle? {
        return gospelCanticle
    }

    fun setGospelCanticle(gospelCanticle: LHGospelCanticle?) {
        this.gospelCanticle = gospelCanticle
    }

    fun getForView(liturgyTime: LiturgyTime?): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        try {
            //¿?lecturaBreve.normalizeByTime(hoy.getCalendarTime());
            salmodia!!.normalizeByTime(liturgyTime!!.timeID)

            //sb.append(today.getAllForView());
            sb.append(Utils.LS2)
            sb.append(getTituloHora())
            sb.append(Utils.fromHtmlToSmallRed(metaInfo))
            sb.append(Utils.LS2)
            sb.append(getSaludoDiosMio())
            sb.append(Utils.LS2)
            sb.append(himno!!.all)
            sb.append(Utils.LS2)
            sb.append(salmodia!!.all)
            sb.append(Utils.LS2)
            sb.append(lecturaBreve!!.getAllWithHourCheck(6))
            sb.append(Utils.LS2)
            sb.append(gospelCanticle!!.all)
            sb.append(Utils.LS2)
            sb.append(preces!!.all)
            sb.append(Utils.LS2)
            sb.append(PadreNuestro.Companion?.all)
            sb.append(Utils.LS2)
            sb.append(oracion?.all)
            sb.append(Utils.LS2)
            sb.append(getConclusionHorasMayores())
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    //@Override
    fun getAllForRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            sb.append(getTituloHoraForRead())
            sb.append(getSaludoDiosMioForRead())
            sb.append(himno!!.allForRead)
            sb.append(salmodia!!.allForRead)
            sb.append(lecturaBreve!!.getAllForRead())
            sb.append(gospelCanticle!!.allForRead)
            sb.append(preces!!.allForRead)
            sb.append(PadreNuestro.all)
            sb.append(oracion?.allForRead)
            sb.append(getConclusionHorasMayoresForRead())
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    private fun getTituloHora(): SpannableStringBuilder {
        val s = if (isPrevious == 0) Constants.TITLE_VISPERAS else Constants.TITLE_I_VISPERAS
        return Utils.toH1Red(s)
    }

    private fun getTituloHoraForRead(): String {
        val s = if (isPrevious == 0) Constants.TITLE_VISPERAS else Constants.TITLE_I_VISPERAS_READ

        //String s = (today.getLiturgiaPrevio() == null) ? TITLE_VISPERAS : TITLE_I_VISPERAS_READ;
        return Utils.pointAtEnd(s)
    }
}