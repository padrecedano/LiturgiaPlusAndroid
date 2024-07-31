package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.deiverbum.app.core.designsystem.theme.NiaTypography
import org.deiverbum.app.util.Utils

@JsonClass(generateAdapter = true)
class MissaeLectionum(override var pericopa: String = "", override var biblica: String = "") :
    LectioBiblica(pericopa, biblica) {
    //constructor() : super()

    //constructor (quote: String, text: String) : super(quote, text)

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

    fun getComposable(type: Int, rubricColor: Color): AnnotatedString {
        return buildAnnotatedString {
            append(Utils.LS2)
            if (type != -1) {
                withStyle(
                    SpanStyle(
                        fontSize = NiaTypography.titleMedium.fontSize,
                        color = rubricColor
                    )
                ) {
                    append(getHeader(type))
                }
                append(Utils.LS2)
            }
            append(book.liturgyName)
            append("    ")
            withStyle(SpanStyle(color = rubricColor)) {

                append(pericopa)
            }
            append(Utils.LS2)
            if (tema != "") {
                withStyle(SpanStyle(color = rubricColor)) {

                    append(tema)
                }
                append(Utils.LS2)
            }
            append(Utils.fromHtml(biblica))
            append(Utils.LS)
        }

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
        sb.append(book.getForRead())
        sb.append(temaForRead)
        sb.append(textoForRead)
        sb.append(getConclusionByType())

        return sb
    }

    private fun getHeader(type: Int): String {
        return if (type == 0) {
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
        }
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
    private fun getHeaderByType(type: Int): String {
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
        }
        return header
    }

    /**
     *
     * Obtiene la conclusión de cada lectura según el tipo.
     *
     * @return Una cadena con la conclusión.
     * @since 2024.1
     */
    private fun getConclusionByType(): String {
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