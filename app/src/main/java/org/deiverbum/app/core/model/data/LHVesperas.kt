package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils


/**
 * Clase que representa las **`Vísperas`** en la capa de datos externa.
 *
 * @property hymnus El himno.
 * @property psalmodia La salmodia, incluyendo antífonas y salmos.
 * @property lectioBrevis Un objeto [LHLectioBrevis] con la lectura breve.
 * @property canticumEvangelicum Un objeto [LHGospelCanticle] con el Magníficat.
 * @property preces Un objeto [LHIntercession] con las preces.
 * @property oratio Un objeto [Oratio] con la oración final.
 * @property sanctus Un objeto [LHSanctus] con la vida breve del santo del día cuando aplique.
 * @property isPrimaVesperas Será `true` cuando la celebración sea Primeras Vísperas.
 *
 * @author A. Cedano
 * @see [Breviarium]
 */

class LHVesperas(
    var hymnus: LHHymn,
    var psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var canticumEvangelicum: LHGospelCanticle,
    var preces: LHIntercession,
    var oratio: Oratio,
    var isPrimaVesperas: Boolean, override var typus: String = "vesperas",
    //override var tempore: LiturgyTime
) : Breviarium(typus) {
    var sanctus: LHSanctus? = null
    var hasSaint: Boolean = false

    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        this.hasSaint = hasSaint
        val ssb = SpannableStringBuilder()
        try {
            if (sanctus != null && hasSaint) {
                //invitatorium.normalizeIsSaint(sanctus!!.nomen)
                //ssb.append(sanctus!!.vitaBrevis)
                //ssb.append(Constants.LS)
            }
            ssb.append(titleForView)
            ssb.append(Utils.LS2)
            ssb.append(Introitus.viewDomineLabiaMeaAperis)
            ssb.append(Utils.LS2)
            //ssb.append(invitatorium.getAllForView(-1, calendarTime))
            ssb.append(hymnus.all)
            ssb.append(Utils.LS2)
            ssb.append(psalmodia.getAllForView(-1, calendarTime))
            ssb.append(Utils.LS)
            ssb.append(Utils.LS)
            ssb.append(lectioBrevis.getAllWithHourCheck(2))
            ssb.append(Utils.LS)
            ssb.append(canticumEvangelicum.getSalmosByIndex(0, calendarTime))
            ssb.append(Utils.LS2)
            ssb.append(preces.all)
            ssb.append(Utils.LS2)
            ssb.append(PadreNuestro.all)
            ssb.append(Utils.LS2)
            ssb.append(oratio.all)
            ssb.append(Utils.LS2)
            ssb.append(RitusConclusionis.viewDominusNosBenedicat)
        } catch (e: Exception) {
            ssb.append(Utils.createErrorMessage(e.message))
        }
        return ssb
    }

    override fun forRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            if (sanctus != null && hasSaint) {
                sb.append(sanctus!!.vitaBrevis)
            }
            sb.append(titleForRead)
            sb.append(Introitus.readDomineLabiaMeaAperis)
            //sb.append(invitatorium.allForRead)
            sb.append(hymnus.allForRead)
            sb.append(psalmodia.getAllForRead())
            sb.append(Utils.LS)
            sb.append(lectioBrevis.getAllForRead())
            sb.append(Utils.LS)
            sb.append(canticumEvangelicum.getSalmosByIndexForRead(0))
            sb.append(Utils.LS2)
            sb.append(preces.allForRead)
            sb.append(Utils.LS2)
            sb.append(PadreNuestro.allForRead)
            sb.append(Utils.LS2)
            sb.append(oratio.allForRead)
            sb.append(RitusConclusionis.readDominusNosBenedicat)
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    val titleForView: SpannableStringBuilder
        get() =
            if (isPrimaVesperas) Utils.toH1Red(Constants.TITLE_I_VISPERAS) else Utils.toH1Red(
                Constants.TITLE_VISPERAS
            )

    val titleForRead: String
        get() =
            if (isPrimaVesperas) Utils.pointAtEnd(Constants.TITLE_I_VISPERAS) else Utils.pointAtEnd(
                Constants.TITLE_VISPERAS
            )


}
/*
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
/*
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
            sb.append(getConclusionHorasMayores())*/
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    //@Override
    fun getAllForRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            /*sb.append(getTituloHoraForRead())
            sb.append(initialInvocationForRead)
            sb.append(lhHymn!!.allForRead)
            sb.append(salmodia!!.getAllForRead())
            sb.append(lecturaBreve!!.getAllForRead())
            sb.append(gospelCanticle!!.allForRead)
            sb.append(preces!!.allForRead)
            sb.append(PadreNuestro.allForRead)
            sb.append(oracion?.allForRead)
            sb.append(getConclusionHorasMayoresForRead())*/
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

 */