package org.deiverbum.app.util

import android.text.SpannableStringBuilder
import android.text.Spanned

class LiturgyHelper {
    companion object {
        const val V = "V./ "
        const val R = "R./ "
        val liturgyMap = hashMapOf(
            1 to "mixto",
            2 to "oficio",
            3 to "laudes",
            4 to "tercia",
            5 to "sexta",
            6 to "nona",
            7 to "visperas",
            8 to "completas",
            9 to "misa",
            10 to "homilias",
            11 to "lecturas",
            12 to "comentarios",
            13 to "santos"
        )

        private val liturgyMapLatin = hashMapOf(
            0 to "mixtus",
            1 to "officium",
            2 to "laudes",
            3 to "tertiam",
            4 to "sextam",
            5 to "nonam",
            6 to "vesperas",
            7 to "completorium",
            8 to "missae",
            9 to "homiliae",
            10 to "lectionum",
            11 to "commentarii",
            12 to "sanctii"
        )

        fun liturgyByType(type: Int): String {
            return liturgyMap[type]!!
        }

        fun liturgyByTypeLatin(type: Int): String? {
            return liturgyMapLatin[type]
        }
        fun getValue(): List<String> {

            return listOf("Test String")

        }

        val finisPsalmus: List<String>
            get() = listOf(
                "Gloria al Padre, y al Hijo, y al Espíritu Santo.",
                "Como era en el principio ahora y siempre, por los siglos de los siglos. Amén."
            )

        //val endPsalm: List<String> get(){return listOf("")}

        val endPsalmForView: Spanned
            get() {
                val fin = ("Gloria al Padre, y al Hijo, y al Espíritu Santo." + Constants.BR
                        + Constants.NBSP_SALMOS + "Como era en el principio ahora y siempre, "
                        + Constants.NBSP_SALMOS + "por los siglos de los siglos. Amén.")
                return Utils.fromHtml(fin)
            }
        val endPsalm: String
            get() {
                val fin = ("Gloria al Padre, y al Hijo, y al Espíritu Santo." + Constants.BR
                        + Constants.NBSP_SALMOS + "Como era en el principio ahora y siempre, "
                        + Constants.NBSP_SALMOS + "por los siglos de los siglos. Amén.")
                return Utils.fromHtml(fin).toString()
            }

        val endPsalmForRead: String
            get() = "Gloria al Padre, y al Hijo, y al Espíritu Santo. Como era en el principio ahora y siempre, por los siglos de los siglos. Amén."

        val introAbreMisLabiosView: SpannableStringBuilder =
            SpannableStringBuilder(Utils.formatTitle(Constants.TITLE_INITIAL_INVOCATION))
                .append(Utils.LS2)
                .append(Utils.toRed("V. "))
                .append("Señor, abre mis labios.")
                .append(Utils.LS)
                .append(Utils.toRed("R. "))
                .append("Y mi boca proclamará tu alabanza.")
                .append(Utils.LS2)
                .append(endPsalmForView)

    }

    /*
        /**
         * Obtiene la conclusión de los salmos y de algunos saludos iniciales para presentar en pantalla.
         *
         * @return Un objeto [Spanned] con el texto.
         * @since 2024.1
         */
        val endPsalmForView: Spanned
            get() {
                val fin = ("Gloria al Padre, y al Hijo, y al Espíritu Santo." + Constants.BR
                        + Constants.NBSP_SALMOS + "Como era en el principio ahora y siempre, "
                        + Constants.NBSP_SALMOS + "por los siglos de los siglos. Amén.")
                return Utils.fromHtml(fin)
            }

        /**
         * Obtiene la conclusión de los salmos y de algunos saludos iniciales para la lectura de voz.
         *
         * @return Un objeto [String] con el texto.
         * @since 2024.1
         */
        val endPsalmForRead: String
            get() = "Gloria al Padre, y al Hijo, y al Espíritu Santo. Como era en el principio ahora y siempre, por los siglos de los siglos. Amén."
    */
}