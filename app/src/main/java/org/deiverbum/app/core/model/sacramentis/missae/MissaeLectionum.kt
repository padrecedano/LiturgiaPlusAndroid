package org.deiverbum.app.core.model.data.missae

import android.text.SpannableStringBuilder
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.deiverbum.app.core.model.biblia.BibleBook
import org.deiverbum.app.core.model.biblia.LectioBiblica
import org.deiverbum.app.util.Utils

@JsonClass(generateAdapter = true)
class MissaeLectionum(override var pericopa: String = "", override var biblica: String = "") :
    LectioBiblica(pericopa, biblica) {
    constructor (book: BibleBook, quote: String, tema: String, text: String, theOrder: Int) : this(
        quote,
        text
    ) {
        this.theOrder = theOrder
        this.tema = tema
        this.book = book
    }

    @Json(ignore = true)
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
        sb.append(Utils.LS2)
        if (type != -1) {
            sb.append(Utils.formatTitle(getHeader(type)))
            sb.append(Utils.LS2)
        }
        sb.append(book.liturgyName)
        sb.append("    ")
        sb.append(Utils.toRed(pericopa))
        sb.append(Utils.LS2)
        if (tema != "") {
            sb.append(Utils.toRed(tema))
            sb.append(Utils.LS2)
        }
        sb.append(Utils.fromHtml(biblica))
        sb.append(Utils.LS)
        return sb
    }

    /**
     *
     * Obtiene la lectura bíblica completa formateada para la lectura de voz.
     *
     * @return Un objeto [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    fun getAllForRead(type: Int): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(Utils.normalizeEnd(getHeader(type)))
        sb.append(book.getForRead())
        sb.append(temaForRead)
        sb.append(textoForRead)
        sb.append(getConclusionByType())
        return sb
    }

    fun getHeader(type: Int): String {
        return when (theOrder) {
            in 1..19 -> "PRIMERA LECTURA"
            in 20..29 -> "SALMO RESPONSORIAL"
            in 30..39 -> "SEGUNDA LECTURA"
            in 40..49 -> "EVANGELIO"
            else -> "LECTURA"
        }
        /*return if (type == 0) {
            var header = ""
            if (theOrder in 1..19) {
                header = "PRIMERA LECTURA"
            }
            if (theOrder in 20..29) {
                header = "SALMO RESPONSORIAL"
            }
            if (theOrder in 30..39) {
                header = "SEGUNDA LECTURA"
            }
            if (theOrder >= 40) {
                header = "EVANGELIO"
            }
            header
        } else {
            getHeaderByType(type)
        }*/
    }

    /**
     *
     * Obtiene el encabezado de cada lectura según el tipo.
     *
     *             type -1 es para el módulo Mixto del Breviario
     *
     *             type 1 es la Vigilia Pascual, con el siguiente esquema:
     *             1. 1ª Lectura
     *             2. Salmo
     *             3. O bien: Salmo
     *             4. 1ª Lectura
     *             5. Salmo
     *             6. 3ª Lectura
     *             7. Salmo
     *             8. 4ª Lectura
     *             9. Salmo
     *             10. 5ª Lectura
     *             11. Salmo
     *             12. 6ª Lectura
     *             13. Salmo
     *             14. 7ª Lectura
     *             15. Salmo
     *             16. o bien: Salmo
     *             17. Epístola
     *             18. Salmo
     *             40. Evangelio
     * @return Un objeto [con el contenido.][String]
     * @since 2023.1.2
     */
    fun getHeaderByType(type: Int): String {
        var header = ""
        if (type == 1) {
            if (theOrder == 1) {
                header = "PRIMERA LECTURA"
            }
            if (theOrder == 2 || theOrder == 5 || theOrder == 7 || theOrder == 9 || theOrder == 11 || theOrder == 13 || theOrder == 15 || theOrder == 18) {
                header = "SALMO RESPONSORIAL"
            }
            if (theOrder == 3 || theOrder == 16) {
                header = "O bien: SALMO RESPONSORIAL"
            }
            if (theOrder == 4) {
                header = "SEGUNDA LECTURA"
            }
            if (theOrder == 6) {
                header = "TERCERA LECTURA"
            }
            if (theOrder == 8) {
                header = "CUARTA LECTURA"
            }
            if (theOrder == 10) {
                header = "QUINTA LECTURA"
            }
            if (theOrder == 12) {
                header = "SEXTA LECTURA"
            }
            if (theOrder == 14) {
                header = "SÉPTIMA LECTURA"
            }
            if (theOrder == 17) {
                header = "EPÍSTOLA"
            }
            if (theOrder >= 40) {
                header = "EVANGELIO"
            }
        } else {
            getHeader(type)
        }
        return header
    }

    /**
     *
     * Obtiene la conclusión de cada lectura según el tipo.
     *
     * @return Una cadena con la conclusión.
     * @since 2025.1
     */
    fun getConclusionByType(): String {
        var conclusion = ""
        if ((theOrder in (1..19)) || (theOrder in (30..39))) {
            conclusion = "Palabra de Dios. Te alabamos Señor."
        }
        if (theOrder in 20..29) {
            conclusion = ""
        }
        /*if (theOrder in 30..39) {
            conclusion = "Palabra de Dios."
        }*/
        if (theOrder >= 40) {
            conclusion = "Palabra del Señor. Gloria a ti, Señor, Jesús."
        }
        return conclusion
    }

    /**
     *
     * Obtiene la conclusión de cada lectura según el tipo.
     *
     * @return Una cadena con la conclusión.
     * @since 2025.1
     */
    fun getConclusio(): String {
        var conclusion = ""
        if ((theOrder in (1..19)) || (theOrder in (30..39))) {
            conclusion = "Palabra de Dios."
        }
        if (theOrder in 20..29) {
            conclusion = ""
        }
        /*if (theOrder in 30..39) {
            conclusion = "Palabra de Dios."
        }*/
        if (theOrder >= 40) {
            conclusion = "Palabra del Señor."
        }
        return conclusion
    }

    /**
     *
     * Obtiene la conclusión de cada lectura según el tipo.
     *
     * @return Una cadena con la conclusión.
     * @since 2025.1
     */
    fun getConclusioProVoce(): String {
        var conclusion = ""
        if ((theOrder in (1..19)) || (theOrder in (30..39))) {
            conclusion = "Palabra de Dios. Te alabamos Señor."
        }
        if (theOrder in 20..29) {
            conclusion = ""
        }
        /*if (theOrder in 30..39) {
            conclusion = "Palabra de Dios."
        }*/
        if (theOrder >= 40) {
            conclusion = "Palabra del Señor. Gloria a ti, Señor, Jesús."
        }
        return conclusion

    }
}