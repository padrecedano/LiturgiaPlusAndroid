package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class LHOfficiumLectioPrior(
    override var book: BibleBook,
    override var quote: String,
    override var text: String,
    override var tema: String = "",
    override var theOrder: Int = 1,

    var responsorium: LHResponsorium
) : LectioBiblica(book, quote, text, tema, theOrder) {


    //var responsorioLargo: LHResponsorium?=null
    @get:Ignore
    private val temaForRead: String
        get() = "$tema."

    override fun getHeader(): String {
        return "PRIMERA LECTURA"
    }

    override fun getResponsorioHeaderForRead(): String {
        return Utils.pointAtEnd(Constants.TITLE_RESPONSORY)
    }

    /**
     *
     * Obtiene la lectura bíblica completa, incluyendo el responsorio, formateada para la vista.
     *
     * @return Un objeto [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    override fun getAll(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(Utils.formatTitle(getHeader()))
        sb.append(Utils.LS2)
        sb.append(book.liturgyName)
        sb.append("    ")
        sb.append(Utils.toRed(quote))
        sb.append(Utils.LS2)
        sb.append(Utils.toRed(tema))
        sb.append(Utils.LS2)
        sb.append(textoSpan)
        //sb.append(Utils.LS)
        sb.append(responsorium.all)
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
        sb.append(book.getForRead())
        sb.append(temaForRead)
        sb.append(textoForRead)
        sb.append(getConclusionForRead())
        //sb.append(getResponsorioHeaderForRead())
        //sb.append(book!!.getForRead())
        sb.append("Responsorio.")
        sb.append(responsorium.allForRead)
        return sb
    }

}