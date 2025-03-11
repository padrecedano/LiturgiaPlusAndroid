package org.deiverbum.app.core.model.data.breviarium

import com.squareup.moshi.JsonClass
import org.deiverbum.app.core.model.liturgia.Oratio

/**
 * Clase que representa los tres horas intermedias del salterio:
 * **`Tercia`**, **`Sexta`** y **`Nona`** en la capa de datos externa.
 *
 * @property hymnus El himno.
 * @property psalmodia La salmodia, incluyendo antífonas y salmos.
 * @property oratio Un objeto [LHOratio] con la oración final.
 * @property horaId Un entero para identificar la hora:
 *           `3` para `Tercia`, `4` para `Sexta` y `5` para `Nona`.
 */
@JsonClass(generateAdapter = true)
data class BreviariumIntermedia(
    var hymnus: LHHymn,
    val psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var oratio: Oratio,
    var typusID: Int = 0,
    override var typus: String = "intermedia"
) : Breviarium(typus) {

    override fun sort() {
        psalmodia.sort()
    }

    override fun normalizeByTime(calendarTime: Int) {
        psalmodia.normalizeByTime(calendarTime)
        lectioBrevis.normalizeByTime(calendarTime)
    }


}