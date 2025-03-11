package org.deiverbum.app.core.model.data.missae

import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.data.breviarium.LHSanctus
import org.deiverbum.app.core.model.data.sacramentis.Sacramentis
import org.deiverbum.app.core.model.data.traditio.Homilieae
import org.deiverbum.app.core.model.data.traditio.Homily
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
 * @since 2024.1
 *
 * @see [Sacramentis]
 */

data class Missae(
    var hasSaint: Boolean = false,
    var calendarTime: Int = 0,
    override var typus: String = "missae"
) : Sacramentis(typus) {

    constructor(
        hasSaint: Boolean = false,
        calendarTime: Int = 0,
        typus: String = "missae",
        homiliae: Homilieae
    ) : this(
        hasSaint,
        calendarTime,
        typus
    )


    constructor(
        hasSaint: Boolean = false,
        calendarTime: Int = 0,
        typus: String = "missae",
        homiliae: MutableList<Homily>
    ) : this(
        hasSaint,
        calendarTime,
        typus
    ) {
        this.homiliae = homiliae
    }

    constructor(
        hasSaint: Boolean = false,
        calendarTime: Int = 0,
        typus: String = "missae",
        lectionumList: MissaeLectionumList
    ) : this(hasSaint, calendarTime, typus) {
        this.lectionumList = lectionumList
    }

    var homiliae: MutableList<Homily>? = null
    //var homilyes: MutableList<Homily> = ArrayList()
    var homilieae: Homilieae? = null

    var lectionumList: MissaeLectionumList? = null

    var sanctus: LHSanctus? = null
    fun forView(calendarTime: Int): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()

        if (lectionumList != null) {
            lectionumList?.lectionum?.sortBy { it!!.theOrder }
            try {
                ssb.append(Utils.toH1Red("Lecturas de la Misa"))

                ssb.append(lectionumList?.getForView())
            } catch (e: Exception) {
                ssb.append(Utils.createErrorMessage(e.message))
            }
            return ssb
        } else if (homiliae != null) {
            ssb.append(Utils.toH1Red("Homilías"))
            homiliae?.forEach {
                ssb.append(it.getAllForView())
            }
        } else {
            ssb.append("No hay datos para mostrar en este día.")
        }
        return ssb
    }

    override fun forRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            if (lectionumList != null) {
                sb.append(lectionumList?.allForRead)
            }
            if (homiliae != null) {
                sb.append("Homilías.")
                homiliae?.forEach {
                    sb.append(it.getAllForRead)
                }
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    override fun sort() {
        if (this.lectionumList != null) {
            this.lectionumList!!.sort()
        }

    }
}
