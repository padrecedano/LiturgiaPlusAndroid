package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

/**
 * Clase que representa los **`Laudes`** en la capa de datos externa.
 *
 * @property invitatorium Un objeto del tipo [LHInvitatory] con el salmo invitatorio
 * @property hymnus El himno.
 * @property psalmodia La salmodia, incluyendo antífonas y salmos.
 * @property preces Un objeto [LHIntercession] con las preces.
 * @property oratio Un objeto [LHOratio] con la oración final.
 * @property sanctus Un objeto [LHSanctus] con la vida breve del santo del día cuando aplique.
 */

data class Missae(
    var missaeLectionum: MutableList<MissaeLectionum?>
    /*var invitatorium: LHInvitatory,
    var hymnus: LHHymnNew,
    val psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var canticumEvangelicum: LHGospelCanticle,
    var preces: LHIntercession,
    var oratio: Oratio*/
) : BreviariumNew {
    var sanctus: LHSanctus? = null
    var hasSaint: Boolean = false
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        this.hasSaint = hasSaint
        val ssb = SpannableStringBuilder()
        try {

            missaeLectionum.forEach { it ->
                ssb.append(it?.getAll(-1))

            }

            //invitatorio!!.normalizeByTime(calendarTime)
            //salmodia!!.normalizeByTime(calendarTime)
            //salmodia!!.sort()
            //officiumLectionis.normalizeByTime(calendarTime)
            ssb.append(Utils.LS2)

        } catch (e: Exception) {
            ssb.append(Utils.createErrorMessage(e.message))
        }
        return ssb
    }

    override fun forRead(): StringBuilder {
        val sb = StringBuilder()
        try {

            missaeLectionum.forEach { it ->
                sb.append(it?.getAllForRead(-1)) //TODO: Ver type
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

}

/*
    private val tituloHora: SpannableStringBuilder
        get() = Utils.toH1Red(Constants.TITLE_LAUDES)
    private val tituloHoraForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_LAUDES)


    fun getForView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        try {
            this.hasSaint = hasSaint
            //invitatorio!!.normalizeByTime(calendarTime)
            //salmodia!!.normalizeByTime(calendarTime)
            lecturaBreve!!.normalizeByTime(calendarTime)
            gospelCanticle!!.normalizeByTime(calendarTime)
            sb.append(Utils.LS2)
            if (santo != null && this.hasSaint) {
                invitatorio!!.normalizeIsSaint(santo!!.theName)
                sb.append(santo!!.vidaSmall)
                sb.append(Constants.LS)
            }
            sb.append(tituloHora)
            sb.append(Utils.fromHtmlToSmallRed(metaInfo))
            sb.append(Utils.LS2)
            sb.append(introAbreMisLabiosView)
            sb.append(Utils.LS2)
            sb.append(invitatorio!!.getAllForView(-1,calendarTime))
            sb.append(Utils.LS2)
            sb.append(lhHymn!!.all)
            sb.append(Utils.LS2)
            sb.append(salmodia!!.getAllForView(-1,calendarTime))
            sb.append(Utils.LS)
            sb.append(lecturaBreve!!.getAllWithHourCheck(2))
            sb.append(Utils.LS)
            sb.append(gospelCanticle!!.all)
            sb.append(Utils.LS2)
            sb.append(preces!!.all)
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

    val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                if (santo != null && hasSaint) {
                    //invitatorio!!.normalizeIsSaint(santo!!.theName)
                    sb.append(santo!!.vidaSmall)
                }
                sb.append(tituloHoraForRead)
                sb.append(introAbreMisLabiosForRead)
                sb.append(invitatorio!!.allForRead)
                sb.append(lhHymn!!.allForRead)
                sb.append(salmodia!!.getAllForRead())
                sb.append(lecturaBreve!!.getAllForRead())
                sb.append(gospelCanticle!!.allForRead)
                sb.append(preces!!.allForRead)
                sb.append(PadreNuestro.allForRead)
                sb.append(oracion!!.allForRead)
                sb.append(getConclusionHorasMayoresForRead())
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
*/

