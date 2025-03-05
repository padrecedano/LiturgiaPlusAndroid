package org.deiverbum.app.core.model.data

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

data class LHLaudes(
    var hasSaint: Boolean = false,
    var invitatorium: LHInvitatory,
    var hymnus: LHHymn,
    val psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var canticumEvangelicum: LHGospelCanticle,
    var preces: LHIntercession,
    var oratio: Oratio, override var typus: String = "laudes"
) : Breviarium(typus) {
    var sanctus: LHSanctus? = null

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

    override fun sort() {
        psalmodia.sort()
    }
    override fun normalizeByTime(calendarTime: Int) {
        invitatorium.normalizeByTime(calendarTime)
        psalmodia.normalizeByTime(calendarTime)
        lectioBrevis.normalizeByTime(calendarTime)
    }
}