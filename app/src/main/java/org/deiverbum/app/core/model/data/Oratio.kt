package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class Oratio(
    var prayerID: Int = 0,
    var prayer: String,
    var order: Int = 0
) {

    /**
     * El texto de la oración.
     * El setter verifica si la misma contiene el símbolo *
     * para extraerlo de la cadena. Este símbolo permanece en aquellas
     * oraciones que no han sido pasadas a la nueva versión del Misal Romano (año 2016).
     * TODO("Normalizar en la base de datos")
     */
    init {
        if (prayer.contains("*")) {
            prayer.replace("*", "")
        }
    }

    val header: SpannableStringBuilder
        get() = Utils.formatTitle(Constants.TITLE_PRAYER)

    val all: Spanned
        get() {
            val sb = SpannableStringBuilder("")
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(Utils.fromHtml(prayer))
            return sb
        }
    val allForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_PRAYER) +
                Utils.fromHtml(prayer)
}