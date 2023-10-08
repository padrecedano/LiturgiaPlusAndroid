package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

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
data class LHIntermedia(
    var hymnus: LHHymnNew,
    val psalmodia: LHPsalmody,
    var lectioBrevis: LHLectioBrevis,
    var oratio: Oratio,
    var horaId: Int
) : BreviariumNew {
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.LS2)
        ssb.append(Introitus.initialInvocationForView)
        ssb.append(hymnus.all)
        ssb.append(Utils.LS2)
        return ssb
    }

    override fun forRead(): StringBuilder {
        return StringBuilder("")
    }
}