package org.deiverbum.app.core.model.breviarium

import org.deiverbum.app.core.model.liturgia.Oratio

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

data class BreviariumOfficium(
    var hasSaint: Boolean = false,
    var invitatorium: LHInvitatory,
    var hymnus: LHHymn,
    val psalmodia: LHPsalmody,
    var officiumLectionis: LHOfficiumLectionis,
    var oratio: Oratio,
    override var typus: String = "officium"
) : Breviarium(typus) {

    var sanctus: LHSanctus? = null

    fun getTypus(): BreviariumOfficium {
        return this
    }

    override fun sort() {
        officiumLectionis.sort()
        psalmodia.sort()
    }

    override fun normalizeByTime(calendarTime: Int) {
        invitatorium.normalizeByTime(calendarTime)
        if (sanctus != null && hasSaint) {
            invitatorium.normalizeIsSaint(sanctus!!.nomen)
        }
        psalmodia.normalizeByTime(calendarTime)
        officiumLectionis.normalizeByTime(calendarTime)
    }
}