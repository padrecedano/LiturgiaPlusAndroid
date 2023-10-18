package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

/**
 * Clase que representa los **`Laudes`** en la capa de datos externa.
 *
 * @property lectionumList Un objeto [MissaeLectionumList] con la lista de lecturas ordenada.
 * @property typus Una cadena para identificar el tipo de celebración. Con este valor
 * si indica al adapter qué clase debe usarse para mapear los datos procedentes de la red.
 *
 *  @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 *
 * @see [Sacramentis]
 */

data class Missae(
    var calendarTime: Int = 0,
    override var typus: String = "missae"
) : Sacramentis(typus) {

    constructor(calendarTime: Int = 0, typus: String = "missae", homiliaeList: HomilyList?) : this(
        calendarTime,
        typus
    ) {
        this.homiliaeList = homiliaeList
    }

    constructor(
        calendarTime: Int = 0,
        typus: String = "missae",
        lectionumList: MissaeLectionumList
    ) : this(calendarTime, typus) {
        this.lectionumList = lectionumList
    }

    private var homiliaeList: HomilyList? = null
    private var lectionumList: MissaeLectionumList? = null

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
