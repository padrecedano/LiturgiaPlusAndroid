package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForRead
import org.deiverbum.app.core.model.data.Introitus.Companion.initialInvocationForView
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class Visperas : BreviaryHour() {
    private var lecturaBreve: LHLectioBrevis? = null
    private var gospelCanticle: LHGospelCanticle? = null
    private var preces: LHIntercession? = null
    private var isPrevious = 0
    fun setIsPrevious(isPrevious: Int) {
        this.isPrevious = isPrevious
    }

    @Suppress("unused")
    fun getLecturaBreve(): LHLectioBrevis? {
        return lecturaBreve
    }

    fun setLecturaBreve(lecturaBreve: LHLectioBrevis?) {
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

    fun getForView(calendarTime: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        try {
            //salmodia!!.normalizeByTime(calendarTime)
            lecturaBreve!!.normalizeByTime(calendarTime)
            gospelCanticle!!.normalizeByTime(calendarTime)

            sb.append(Utils.LS2)
            sb.append(getTituloHora())
            sb.append(Utils.fromHtmlToSmallRed(metaInfo))
            sb.append(Utils.LS2)
            sb.append(initialInvocationForView)
            sb.append(Utils.LS2)
            sb.append(lhHymn!!.all)
            sb.append(Utils.LS2)
            sb.append(salmodia!!.getAllForView(-1, calendarTime))
            sb.append(Utils.LS)
            sb.append(lecturaBreve!!.getAllWithHourCheck(6))
            sb.append(Utils.LS)
            sb.append(gospelCanticle!!.all)
            sb.append(Utils.LS2)
            sb.append(preces!!.all)
            sb.append(Utils.LS2)
            sb.append(PadreNuestro.all)
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
            sb.append(initialInvocationForRead)
            sb.append(lhHymn!!.allForRead)
            sb.append(salmodia!!.getAllForRead())
            sb.append(lecturaBreve!!.getAllForRead())
            sb.append(gospelCanticle!!.allForRead)
            sb.append(preces!!.allForRead)
            sb.append(PadreNuestro.allForRead)
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