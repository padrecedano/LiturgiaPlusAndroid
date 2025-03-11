package org.deiverbum.app.core.model.liturgia

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import com.squareup.moshi.Json
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 *
 *
 * Ritos conclusivos de la Liturgia.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 */

class RitusConclusionis {
    val minor = listOf(txtBenedicamusDomino, txtDeoGratias)
    val maior = listOf(txtDominusNosBenedicat, txtAmen)

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

        @Json(ignore = true)
        @get:Ignore
        val contentTitle: String = Constants.TITLE_CONCLUSION

        @Json(ignore = true)
        @get:Ignore
        val txtDominusNosBenedicat: String =
            "El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna."

        @Json(ignore = true)
        @get:Ignore
        val txtAmen: String =
            "Amén."

        @Json(ignore = true)
        @get:Ignore
        val txtBenedicamusDomino: String =
            "Bendigamos al Señor."

        @Json(ignore = true)
        @get:Ignore
        val txtDeoGratias: String =
            "Demos gracias a Dios."

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




