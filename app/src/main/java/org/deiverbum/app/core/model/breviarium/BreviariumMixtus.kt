package org.deiverbum.app.core.model.data.breviarium

import org.deiverbum.app.core.model.data.missae.MissaeLectionumList
import org.deiverbum.app.core.model.liturgia.Oratio

/**
 * Clase que representa **`Laudes + Lecturas del Oficio`** en la capa de datos externa.
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

data class BreviariumMixtus(
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
) : Breviarium(typus) {
    var sanctus: LHSanctus? = null

    override fun sort() {
        psalmodia.sort()
        officiumLectionis.sort()
        lectionumList.sort()
    }
    override fun normalizeByTime(calendarTime: Int) {
        invitatorium.normalizeByTime(calendarTime)
        if (sanctus != null && hasSaint) {
            invitatorium.normalizeIsSaint(sanctus!!.nomen)
        }
        psalmodia.normalizeByTime(calendarTime)
        officiumLectionis.normalizeByTime(calendarTime)
        canticumEvangelicum.normalizeByTime(calendarTime)
        lectioBrevis.normalizeByTime(calendarTime)
    }
}