package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 * Clase que representa el **`Oficio de Lecturas`** en la capa de datos externa.
 *
 * @property bibleBook Un objeto [BibleBook].
 * @property quote La referencia de la lectura.
 * @property text El texto de la lectura.
 * @property theme El tema de la lectura.
 * @property theOrder El orden de la lectura.
 *
 * @see LectioBiblica
 */

data class LHOfficeBiblicalEaster(
    var bibleBook: BibleBook,
    override var quote: String = "",
    override var text: String = "",
    var theme: String = "",
    override var theOrder: Int = 0
) : LectioBiblica(bibleBook, quote, text, theOrder) {
    var psalm: LHPsalm? = null

    //public int theOrder;
    @Ignore
    var prayer: Oratio? = null
    override fun getHeader(): String {
        var header = ""
        if (theOrder == 1) {
            header = "PRIMERA LECTURA"
        }
        if (theOrder == 2) {
            header = "SEGUNDA LECTURA"
        }
        if (theOrder == 3) {
            header = "TERCERA LECTURA"
        }
        if (theOrder == 4) {
            header = "CUARTA LECTURA"
        }
        return header
    }

    override fun getResponsorioHeaderForRead(): String {
        return Utils.pointAtEnd(Constants.TITLE_RESPONSORY)
    }

    val biblical: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(Utils.formatTitle(getHeader()))
            sb.append(Utils.LS2)
            sb.append(book.liturgyName)
            sb.append("    ")
            sb.append(Utils.toRed(quote))
            sb.append(Utils.LS2)
            sb.append(Utils.toRed(theme))
            sb.append(Utils.LS2)
            sb.append(textoSpan)
            sb.append(Utils.LS)
            return sb
        }

    //sb.append(responsorioLargo.getAll());
    val biblicalForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(getHeader())
            sb.append(Utils.LS2)
            sb.append(book.liturgyName)
            sb.append("    ")
            sb.append(quote)
            sb.append(Utils.LS2)
            sb.append(theme)
            sb.append(Utils.LS2)
            sb.append(textoSpan)
            sb.append(Utils.LS)
            //sb.append(responsorioLargo.getAll());
            return sb
        }

    /**
     *
     * Obtiene la lectura bíblica completa formateada para la lectura de voz.
     *
     * @return Un objeto [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    override fun getAllForRead(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(Utils.pointAtEnd(getHeader()))
        sb.append(biblicalForRead)
        return sb
    }
}