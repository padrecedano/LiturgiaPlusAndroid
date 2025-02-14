package org.deiverbum.app.util

import android.text.SpannableStringBuilder
import android.text.Spanned

class LiturgyHelper {
    companion object {
        const val V = "V./ "
        const val R = "R./ "
        val liturgyMap = hashMapOf(
            1 to "Mixto",
            2 to "Oficio",
            3 to "Laudes",
            4 to "Tercia",
            5 to "Sexta",
            6 to "Nona",
            7 to "Vísperas",
            8 to "Completas",
            9 to "Misa",
            11 to "Lecturas",
            12 to "Comentarios",
            13 to "Homilías",
            20 to "Santos",
            30 to "Rosario"
        )

        val liturgyName = hashMapOf(
            1 to "mixto",
            2 to "oficio",
            3 to "laudes",
            4 to "tercia",
            5 to "sexta",
            6 to "nona",
            7 to "vísperas",
            8 to "completas",
            //9 to "misa",
            //10 to "homilias",
            11 to "lecturas",
            12 to "comentarios",
            13 to "homilías",
            20 to "santos",
            30 to "rosario"
        )

        private val liturgyMapLatin = hashMapOf(
            1 to "mixtus",
            2 to "officium",
            3 to "laudes",
            4 to "tertiam",
            5 to "sextam",
            6 to "nonam",
            7 to "vesperas",
            8 to "completorium",
            // to "missae",
            11 to "lectionum",
            12 to "commentarii",
            13 to "homiliae",

            20 to "sanctii"

        )

        fun liturgyByType(type: Int): String {
            return liturgyMap[type]!!
        }

        fun liturgyByName(name: String): Int {
            val keys = liturgyMap.filterValues { it == name }.keys
            return keys.first()
        }

        fun liturgyByTypeLatin(type: Int): String? {
            return liturgyMapLatin[type]
        }
        fun liturgyByNameLatin(name: String): Int {
            val keys = liturgyMapLatin.filterValues { it == name }.keys
            return keys.first()
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

        val gloriaNonDicitur: String
            get() = "No se dice Gloria"

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