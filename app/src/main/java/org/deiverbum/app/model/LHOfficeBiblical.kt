package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

class LHOfficeBiblical : Biblical() {
    var tema: String? = null
    var responsorioLargo: LHResponsory? = null
    private val temaForRead: String
        get() = "$tema."

    override fun getHeader(): String {
        return "PRIMERA LECTURA"
    }

    override fun getResponsorioHeaderForRead(): String? {
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
        sb.append(book?.liturgyName)
        sb.append("    ")
        sb.append(Utils.toRed(quote))
        sb.append(Utils.LS2)
        sb.append(Utils.toRed(tema))
        sb.append(Utils.LS2)
        sb.append(textoSpan)
        sb.append(Utils.LS)
        sb.append(responsorioLargo!!.all)
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
        sb.append(book!!.getForRead())
        sb.append(temaForRead)
        sb.append(textoForRead)
        sb.append(getConclusionForRead())
        sb.append(getResponsorioHeaderForRead())
        sb.append(responsorioLargo?.allForRead)
        return sb
    }

    override fun getOrden(): Int? {
        return order
    }

    override fun setOrden(orden: Int?) {
        order = orden
    }
}