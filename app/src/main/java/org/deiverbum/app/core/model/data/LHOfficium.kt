package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 * Clase que representa el **`Oficio de Lecturas`** en la capa de datos externa.
 *
 * @property invitatorium Un objeto del tipo [LHInvitatory] con el salmo invitatorio
 * @property hymnus El himno.
 * @property psalmodia La salmodia, incluyendo antífonas y salmos.
 * @property officiumLectionis Un objeto [LHOfficiumLectionis] con las lecturas largas.
 * @property oratio Un objeto [LHOratio] con la oración final.
 * @property sanctus Un objeto [LHSanctus] con la vida breve del santo del día cuando aplique.
 */

data class LHOfficium(
    var invitatorium: LHInvitatory,
    var hymnus: LHHymnNew,
    val psalmodia: LHPsalmody,
    var officiumLectionis: LHOfficiumLectionis,
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
            officiumLectionis.normalizeByTime(calendarTime)
            ssb.append(Utils.LS2)
            if (sanctus != null && hasSaint) {
                invitatorium.normalizeIsSaint(sanctus!!.nomen)
                ssb.append(sanctus!!.vitaBrevis)
                ssb.append(Constants.LS)
            }
            ssb.append(Utils.toH1Red(Constants.TITLE_OFICIO))
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
            ssb.append(officiumLectionis.allForView)
            //TEDEUM
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
            sb.append(Utils.pointAtEnd(Constants.TITLE_OFICIO))
            sb.append(Introitus.readDomineLabiaMeaAperis)
            sb.append(invitatorium.allForRead)
            sb.append(hymnus.allForRead)
            sb.append(psalmodia.getAllForRead())
            sb.append(officiumLectionis.allForRead)
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
class LHOfficium : BreviaryHour() {
    var invitatorio: LHInvitatory? = null
    var teDeum: TeDeum? = null
    @Ignore
    var hasTeDeum: Int = 0

    private val tituloHora: SpannableStringBuilder
        get() = Utils.toH1Red(Constants.TITLE_OFICIO)
    private val tituloHoraForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_OFICIO)

    fun getForView(
        calendarTimes: Int,
        hasSaintt: Boolean
    ): SpannableStringBuilder {
        val sb = SpannableStringBuilder()

        //thishasSaint //= hasSaint
        try {
            //invitatorio!!.normalizeByTime(calendarTime)
            //salmodia!!.normalizeByTime(calendarTime)
            //salmodia!!.sort()
            lhOfficeOfReading!!.normalizeByTime(calendarTime)
            ssb.append(Utils.LS2)
            if (santo != null && hasS) {
                invitatorio!!.normalizeIsSaint(santo!!.theName)
                ssb.append(santo?.vidaSmall)
                ssb.append(Constants.LS)
            }
            ssb.append(tituloHora)
            ssb.append(Utils.fromHtmlToSmallRed(metaInfo))
            ssb.append(Utils.LS2)
            ssb.append(introAbreMisLabiosView)
            ssb.append(Utils.LS2)
            ssb.append(invitatorio!!.getAllForView(-1,calendarTime))
            ssb.append(Utils.LS2)
            ssb.append(lhHymn!!.all)
            ssb.append(Utils.LS2)
            ssb.append(salmodia!!.getAllForView(-1,calendarTime))
            ssb.append(Utils.LS)
            ssb.append(lhOfficeOfReading!!.allForView)
            if (hasTeDeum!=0) {
                ssb.append(TeDeum().all)
            }
            ssb.append(oracion?.all)
            ssb.append(Utils.LS2)
            ssb.append(getConclusionHorasMayores())
        } catch (e: Exception) {
            ssb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }


    val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            try {
                if (santo != null && hasSaint) {
                    ssb.append(santo!!.vidaSmall)
                }
                ssb.append(tituloHoraForRead)
                ssb.append(introAbreMisLabiosForRead)
                ssb.append(invitatorio!!.allForRead)
                ssb.append(lhHymn!!.allForRead)
                ssb.append(salmodia!!.getAllForRead())
                ssb.append(lhOfficeOfReading!!.allForRead)
                if (teDeum!=null) {
                    ssb.append(teDeum?.allForRead)
                }
                ssb.append(oracion?.allForRead)
                ssb.append(getConclusionHorasMayoresForRead())
            } catch (e: Exception) {
                ssb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }
}*/