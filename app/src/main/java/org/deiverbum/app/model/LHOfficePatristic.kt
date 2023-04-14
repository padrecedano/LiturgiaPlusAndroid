package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.room.Ignore
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils

class LHOfficePatristic {
    var groupFK: Int? = null
    var homilyFK: Int? = null
    var responsoryFK: Int? = null
    var source: String? = null
    var theme: String? = null

    @Ignore
    var padre: String? = null

    @Ignore
    var obra: String? = null

    @Ignore
    var text: String? = null

    @Ignore
    var ref: String? = null

    @JvmField
    @Ignore
    var paterOpus: PaterOpus? = null

    @Ignore
    var responsorioLargo: LHResponsory? = null
    var theOrder: Int? = null
    val padreForRead: String
        get() = Utils.pointAtEnd(padre)


    val obraForView: String
        get() = paterOpus!!.opusForView
    val obraForRead: String
        get() = Utils.pointAtEnd(obra)
    val temaForRead: String
        get() = Utils.pointAtEnd(theme)
    val textForRead: Spanned
        get() = Utils.fromHtmlForRead(text)
    val textoSpan: Spanned
        get() = Utils.fromHtml(text)
    val header: SpannableStringBuilder
        get() = Utils.formatTitle("SEGUNDA LECTURA")
    val responsorioHeaderForRead: String
        get() = Utils.pointAtEnd(Constants.TITLE_RESPONSORY)
    val headerForRead: String
        get() = "Segunda lectura."

    /**
     *
     * Obtiene la lectura patrística completa, incluyendo el responsorio, formateada para la vista.
     *
     * @return Un objeto [con el contenido.][SpannableStringBuilder]
     * @since 2022.01
     */
    val allForRead: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(headerForRead)
            sb.append(padreForRead)
            sb.append(obraForRead)
            sb.append(temaForRead)
            sb.append(textForRead)
            sb.append(padreForRead)
            sb.append(responsorioHeaderForRead)
            sb.append(responsorioLargo?.allForRead)
            return sb
        }
    val all: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(header)
            sb.append(Utils.LS2)
            sb.append(obraForView)
            sb.append(Utils.LS2)
            sb.append(Utils.toSmallSizeRed(source))
            sb.append(Utils.LS2)
            sb.append(Utils.toRed(theme))
            sb.append(Utils.LS2)
            sb.append(textoSpan)
            sb.append(Utils.LS)
            sb.append(responsorioLargo!!.all)
            return sb
        }
}