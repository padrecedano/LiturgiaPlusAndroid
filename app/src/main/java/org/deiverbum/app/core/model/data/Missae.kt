package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

/**
 * Clase que representa los **`Laudes`** en la capa de datos externa.
 *
 * @property lectionumList Un objeto [MissaeLectionumList] con la lista de lecturas ordenada.
 */

data class Missae(
    var calendarTime: Int = 0
) : Sacramentis {

    constructor(calendarTime: Int = 0, homiliaeList: HomilyList?) : this(calendarTime) {
        this.homiliaeList = homiliaeList
    }

    constructor(calendarTime: Int = 0, lectionumList: MissaeLectionumList) : this(calendarTime) {
        this.lectionumList = lectionumList
    }

    var homiliaeList: HomilyList? = null
    var lectionumList: MissaeLectionumList? = null

    var sanctus: LHSanctus? = null
    var hasSaint: Boolean = false
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()

        if (lectionumList != null) {
            lectionumList?.lectionum?.sortBy { it!!.theOrder }
            this.hasSaint = hasSaint
            try {
                ssb.append(lectionumList?.getForView())
            } catch (e: Exception) {
                ssb.append(Utils.createErrorMessage(e.message))
            }
            return ssb
        } else if (homiliaeList != null) {
            ssb.append(homiliaeList!!.getAllForView())
        } else {
            ssb.append("No hay datos para mostrar en este día.")
        }

        return ssb
    }

    override fun forRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            sb.append(lectionumList?.allForRead)
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }
}
