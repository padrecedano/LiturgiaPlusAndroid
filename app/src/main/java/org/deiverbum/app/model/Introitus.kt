package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

/**
 *
 *
 * Saludos iniciales de la Liturgia.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */

class Introitus {

    var inNomineForView: SpannableStringBuilder = SpannableStringBuilder()

    init {
        val ssb = SpannableStringBuilder("")
        ssb.append(Utils.toRed("V/. "))
        ssb.append("En el nombre del Padre, y del Hijo, y del Espíritu Santo.")
        ssb.append(Utils.LS)
        ssb.append(Utils.toRed("R/. "))
        ssb.append("Amén.")
        ssb.append(Utils.LS2)
        inNomineForView = ssb
    }

    var inNomineForRead: String = "En el nombre del Padre, y del Hijo, y del Espíritu Santo. Amén."
}




