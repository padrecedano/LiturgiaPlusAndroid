package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

/**
 * Clase que representa los **`Laudes`** en la capa de datos externa.
 *
 * @property lectionumList Un objeto [MissaeLectionumList] con la lista de lecturas ordenada.
 */

data class Missae(
    var lectionumList: MissaeLectionumList
) : Sacramentis {
    var sanctus: LHSanctus? = null
    var hasSaint: Boolean = false
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        lectionumList.lectionum.sortBy { it!!.theOrder }
        this.hasSaint = hasSaint
        val ssb = SpannableStringBuilder()
        try {
            ssb.append(lectionumList.getForView())
        } catch (e: Exception) {
            ssb.append(Utils.createErrorMessage(e.message))
        }
        return ssb
    }

    override fun forRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            sb.append(lectionumList.allForRead)
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }
}
