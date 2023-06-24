package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

open class MassReading : Biblical(), Comparable<MassReading> {
    var tema: String? = null
    private val temaForRead: String
        get() = Utils.normalizeEnd(tema)

    /**
     *
     * Obtiene la lectura bíblica completa, incluyendo el responsorio, formateada para la vista.
     *
     * @return Un objeto [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    fun getAll(type: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(Utils.LS)
        sb.append(Utils.formatTitle(getHeader(type)))
        sb.append(Utils.LS2)
        sb.append(book?.liturgyName)
        sb.append("    ")
        sb.append(Utils.toRed(quote))
        sb.append(Utils.LS2)
        if (tema != null) {
            sb.append(Utils.toRed(tema))
            sb.append(Utils.LS2)
        }
        sb.append(textoSpan)
        sb.append(Utils.LS2)
        return sb
    }

    /**
     *
     * Obtiene la lectura bíblica completa formateada para la lectura de voz.
     *
     * @return Un objeto [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    //@Override
    fun getAllForRead(type: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(Utils.normalizeEnd(getHeader(type)))
        sb.append(book?.getForRead())
        sb.append(temaForRead)
        sb.append(textoForRead)
        return sb
    }

    private fun getHeader(type: Int): String {
        return if (type == 0) {
            var header = ""
            if (order!! >= 1 && order!! <= 19) {
                header = "PRIMERA LECTURA"
            }
            if (order!! >= 20 && order!! <= 29) {
                header = "SALMO RESPONSORIAL"
            }
            if (order!! >= 30 && order!! <= 39) {
                header = "SEGUNDA LECTURA"
            }
            if (order!! >= 40) {
                header = "EVANGELIO"
            }
            header
        } else {
            getHeaderByType(type)
        }
    }

    /**
     *
     * Obtiene el encabezado de cada lectura según el tipo.
     *
     * @return Un objeto [con el contenido.][String]
     * @since 2023.1.2
     */
    private fun getHeaderByType(type: Int): String {
        /*
            type 1 es la Vigilia Pascual, con el siguiente esquema:
            1. 1ª Lectura
            2. Salmo
            3. O bien: Salmo
            4. 1ª Lectura
            5. Salmo
            6. 3ª Lectura
            7. Salmo
            8. 4ª Lectura
            9. Salmo
            10. 5ª Lectura
            11. Salmo
            12. 6ª Lectura
            13. Salmo
            14. 7ª Lectura
            15. Salmo
            16. o bien: Salmo
            17. Epístola
            18. Salmo
            40. Evangelio
         */
        var header = ""
        if (type == 1) {
            if (order == 1) {
                header = "PRIMERA LECTURA"
            }
            if (order == 2 || order == 5 || order == 7 || order == 9 || order == 11 || order == 13 || order == 15 || order == 18) {
                header = "SALMO RESPONSORIAL"
            }
            if (order == 3 || order == 16) {
                header = "O bien: SALMO RESPONSORIAL"
            }
            if (order == 4) {
                header = "SEGUNDA LECTURA"
            }
            if (order == 6) {
                header = "TERCERA LECTURA"
            }
            if (order == 8) {
                header = "CUARTA LECTURA"
            }
            if (order == 10) {
                header = "QUINTA LECTURA"
            }
            if (order == 12) {
                header = "SEXTA LECTURA"
            }
            if (order == 14) {
                header = "SÉPTIMA LECTURA"
            }
            if (order == 17) {
                header = "EPÍSTOLA"
            }
            if (order!! >= 40) {
                header = "EVANGELIO"
            }
        }
        return header
    }

    override fun compareTo(other: MassReading): Int {
        return getOrden()!!.compareTo(other.getOrden()!!)
    }
}