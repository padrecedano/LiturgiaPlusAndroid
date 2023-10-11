package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

/**
 *
 *
 * Ritos conclusivos de la Liturgia.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */

class RitusConclusionis {
    companion object {

        var viewTitle = SpannableStringBuilder(Utils.formatTitle("CONCLUSIÓN"))
        var readTitle = "Conclusión."

        /**
         * Conclusión de las horas mayores.
         */
        var viewDominusNosBenedicat: SpannableStringBuilder =
            SpannableStringBuilder(Utils.toRed("V/. "))
                .append("El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna.")
                .append(Utils.LS)
                .append(Utils.toRed("R/. "))
                .append("Amén.")

        var readDominusNosBenedicat: String =
            "El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna. Amén."

        /**
         * Conclusión de las horas menores.
         */

        var viewBenedicamusDomino: SpannableStringBuilder =
            SpannableStringBuilder(Utils.toRed("V/. "))
                .append("Bendigamos al Señor.")
                .append(Utils.LS)
                .append(Utils.toRed("R/. "))
                .append("Demos gracias a Dios.")
                .append(Utils.LS2)

        var readBenedicamusDomino: String =
            "Bendigamos al Señor. Demos gracias a Dios."


    }
}




