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
 *
 * @author A. Cedano
 * @see [Breviarium]
 */

data class LHMixtus(
    var hasSaint: Boolean = false,
    var invitatorium: LHInvitatory,
    var hymnus: LHHymn,
    val psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var officiumLectionis: LHOfficiumLectionis,

    var canticumEvangelicum: LHGospelCanticle,
    var lectionumList: MissaeLectionumList,

    var preces: LHIntercession,
    var oratio: Oratio, override var typus: String = "mixtus"
    //, override var tempore: LiturgyTime
) : Breviarium(typus) {
    var sanctus: LHSanctus? = null

    //var hasSaint: Boolean = false
    fun forView(calendarTime: Int): SpannableStringBuilder {
        //this.hasSaint = hasSaint
        val ssb = SpannableStringBuilder()
        try {
            if (sanctus != null && hasSaint) {
                invitatorium.normalizeIsSaint(sanctus!!.nomen)
                ssb.append(sanctus!!.forViewVitaBrevis)
            }
            ssb.append(Utils.toH1Red(Constants.TITLE_MIXTO))
            ssb.append(Utils.LS2)
            ssb.append(Introitus.viewDomineLabiaMeaAperis)
            ssb.append(Utils.LS2)
            ssb.append(invitatorium.getAllForView(-1, calendarTime))
            ssb.append(Utils.LS2)
            ssb.append(hymnus.all)
            ssb.append(Utils.LS2)
            ssb.append(psalmodia.getAllForView(-1, calendarTime))
            ssb.append(Utils.LS)
            ssb.append(lectioBrevis.getAllWithHourCheck(2))
            ssb.append(Utils.LS)
            ssb.append(officiumLectionis.allForView)
            if (officiumLectionis.hasTeDeum) {
                ssb.append(TeDeum().all)
            }
            ssb.append(lectionumList.getForView())
            ssb.append(Utils.LS2)
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
                sb.append(sanctus!!.forReadVitaBrevis)
            }
            sb.append(Utils.pointAtEnd(Constants.TITLE_LAUDES))
            sb.append(Introitus.readDomineLabiaMeaAperis)
            sb.append(invitatorium.allForRead)
            sb.append(hymnus.allForRead)
            sb.append(psalmodia.getAllForRead())
            sb.append(lectioBrevis.getAllForRead())
            sb.append(officiumLectionis.allForRead)
            if (officiumLectionis.hasTeDeum) {
                sb.append(TeDeum().allForRead)
            }
            sb.append(canticumEvangelicum.getSalmosByIndexForRead(0))
            sb.append(preces.allForRead)
            sb.append(PadreNuestro.allForRead)
            sb.append(oratio.allForRead)
            sb.append(RitusConclusionis.readDominusNosBenedicat)
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    override fun sort() {
        psalmodia.sort()
        officiumLectionis.sort()
        lectionumList.sort()
    }
}