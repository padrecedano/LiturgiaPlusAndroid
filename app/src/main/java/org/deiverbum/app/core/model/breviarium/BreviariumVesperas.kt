package org.deiverbum.app.core.model.data.breviarium

import org.deiverbum.app.core.model.liturgia.Oratio
import org.deiverbum.app.util.Constants.TITLE_I_VISPERAS
import org.deiverbum.app.util.Constants.TITLE_VISPERAS


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

class BreviariumVesperas(
    var hasSaint: Boolean = false,
    var hymnus: LHHymn,
    var psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var canticumEvangelicum: LHGospelCanticle,
    var preces: LHIntercession,
    var oratio: Oratio,
    var isPrimaVesperas: Boolean, override var typus: String = "vesperas",
) : Breviarium(typus) {
    var sanctus: LHSanctus? = null

    override fun getTitle(): String {
        return when (isPrimaVesperas) {
            true -> TITLE_I_VISPERAS
            false -> TITLE_VISPERAS
        }
    }

    override fun sort() {
        psalmodia.sort()
    }

    override fun normalizeByTime(calendarTime: Int) {
        psalmodia.normalizeByTime(calendarTime)
        lectioBrevis.normalizeByTime(calendarTime)
    }
}