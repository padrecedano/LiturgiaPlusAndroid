package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
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

data class LHLaudes(
    var invitatorium: LHInvitatory,
    var hymnus: LHHymnNew,
    val psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var canticumEvangelicum: LHGospelCanticle,
    var preces: LHIntercession,
    var oratio: Oratio
) : BreviariumNew {
    var sanctus: LHSanctus? = null
    var hasSaint: Boolean = false
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        this.hasSaint = hasSaint
        val ssb = SpannableStringBuilder()
        try {
            //invitatorio!!.normalizeByTime(calendarTime)
            //salmodia!!.normalizeByTime(calendarTime)
            //salmodia!!.sort()
            //officiumLectionis.normalizeByTime(calendarTime)
            ssb.append(Utils.LS2)
            if (sanctus != null && hasSaint) {
                invitatorium.normalizeIsSaint(sanctus!!.nomen)
                ssb.append(sanctus!!.vitaBrevis)
                ssb.append(Constants.LS)
            }
            ssb.append(Utils.toH1Red(Constants.TITLE_LAUDES))
            //ssb.append(Utils.fromHtmlToSmallRed(metaInfo))
            ssb.append(Utils.LS2)
            ssb.append(Introitus.viewDomineLabiaMeaAperis)
            ssb.append(Utils.LS2)
            ssb.append(invitatorium.getAllForView(-1, calendarTime))
            ssb.append(Utils.LS2)
            ssb.append(hymnus.all)
            ssb.append(Utils.LS2)
            ssb.append(psalmodia.getAllForView(-1, calendarTime))
            ssb.append(Utils.LS)

            ssb.append(Utils.LS)
            ssb.append(lectioBrevis.getAllWithHourCheck(2))
            ssb.append(Utils.LS)
            ssb.append(canticumEvangelicum.all)
            ssb.append(Utils.LS2)
            ssb.append(preces.all)
            ssb.append(Utils.LS2)
            ssb.append(PadreNuestro.all)
            ssb.append(Utils.LS2)


            ssb.append(oratio.all)
            ssb.append(Utils.LS2)
            ssb.append(RitusConclusionis.viewDominusnosBenedicat)
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
            sb.append(Utils.pointAtEnd(Constants.TITLE_LAUDES))
            sb.append(Introitus.readDomineLabiaMeaAperis)
            sb.append(invitatorium.allForRead)
            sb.append(hymnus.allForRead)
            sb.append(psalmodia.getAllForRead())

            sb.append(Utils.LS)
            sb.append(lectioBrevis.getAllForRead())
            sb.append(Utils.LS)
            sb.append(canticumEvangelicum.allForRead)
            sb.append(Utils.LS2)
            sb.append(preces.allForRead)
            sb.append(Utils.LS2)
            sb.append(PadreNuestro.allForRead)
            sb.append(Utils.LS2)

            //sb.append(officiumLectionis!!.allForRead)
            //TEDEUM


            sb.append(oratio.allForRead)
            sb.append(RitusConclusionis.readDominusNosBenedicat)
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

