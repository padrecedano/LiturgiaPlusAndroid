package org.deiverbum.app.core.model.data

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
 *
 * @see [Breviarium]
 */

data class LHOfficium(
    var hasSaint: Boolean = false,
    var invitatorium: LHInvitatory,
    var hymnus: LHHymn,
    val psalmodia: LHPsalmody,
    var officiumLectionis: LHOfficiumLectionis,
    var oratio: Oratio,
    override var typus: String = "officium"
) : Breviarium(typus) {

    var sanctus: LHSanctus? = null

    fun getTypus(): LHOfficium {
        return this
    }

    override fun forRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            /*if (sanctus != null && hasSaint) {
                sb.append(sanctus!!.forReadVitaBrevis)
            }
            sb.append(Utils.pointAtEnd(Constants.TITLE_OFICIO))
            sb.append(Introitus.readDomineLabiaMeaAperis)
            sb.append(invitatorium.allForRead)
            sb.append(hymnus.allForRead)
            sb.append(psalmodia.getAllForRead())*/
            sb.append(officiumLectionis.allForRead)
            if (officiumLectionis.hasTeDeum) {
                sb.append(TeDeum().allForRead)
            }
            sb.append(oratio.allForRead)
            sb.append(RitusConclusionis.readDominusNosBenedicat)
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    override fun sort() {
        officiumLectionis.sort()
        psalmodia.sort()
    }

    override fun normalizeByTime(calendarTime: Int) {
        invitatorium.normalizeByTime(calendarTime)
        psalmodia.normalizeByTime(calendarTime)
        officiumLectionis.normalizeByTime(calendarTime)
    }
}