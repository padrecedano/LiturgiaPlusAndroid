package org.deiverbum.app.core.model.data.breviarium

import org.deiverbum.app.core.model.liturgia.Kyrie
import org.deiverbum.app.core.model.liturgia.Oratio

/**
 * Clase que representa las **`Completas`** en la capa de datos externa.
 *
 * @property kyrie Un objeto del tipo [Kyrie] con el salmo invitatorio
 * @property hymnus El himno.
 * @property psalmodia La salmodia, incluyendo antífonas y salmos.
 * @property lectioBrevis Un objeto [LHLectioBrevis] con la lectura breve.
 * @property canticumEvangelicum Un objeto [LHGospelCanticle] con el *Nunc Dimitis*.
 * @property oratio Un objeto [LHOratio] con la oración final.
 * @property conclusio Un objeto [ConclusioCompletorium] con la conclusión de la hora.
 *
 * @see [Breviarium]
 */
data class BreviariumCompletorium(
    var kyrie: Kyrie,
    var hymnus: LHHymn,
    var psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var canticumEvangelicum: LHGospelCanticle,
    var oratio: Oratio,
    var conclusio: ConclusioCompletorium,
    override var typus: String = "completorium"
) : Breviarium(typus) {

    override fun sort() {
        psalmodia.sort()
    }

    override fun normalizeByTime(calendarTime: Int) {
        psalmodia.normalizeByTime(calendarTime)
        lectioBrevis.normalizeByTime(calendarTime)
    }


}