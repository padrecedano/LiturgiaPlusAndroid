package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.utils.Utils
@Suppress("unused")
class BibleComment {
    private val obras: PaterOpus? = null
    var tema = ""
    var refBiblia = ""
    var refFuente = ""
    var texto: String? = null
    var padre: String? = null
    var obra: String? = null
    var cita = ""
    var ref = ""
    var fecha: String? = null
    var biblica: MassReading? = null
    private val textoForRead: Spanned
        get() = Utils.fromHtmlForRead(texto)
    private val citaForRead: String
        get() = if (cita != "") Utils.normalizeEnd(cita) else ""
    private val padreForRead: String
        get() = if (padre != "") Utils.normalizeEnd(padre) else ""
    private val obraForRead: String
        get() = if (obra != "") Utils.normalizeEnd(obra) else ""
    private val temaForRead: String
        get() = if (tema != "") Utils.normalizeEnd(tema) else ""
    private val refForRead: String
        get() = if (ref != "") Utils.normalizeEnd(ref) else ""

    val allForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            //sb.append(today.getSingleForView());
            sb.append(Utils.toH2Red(padre))
            sb.append(Utils.LS)
            sb.append(Utils.toH3Red(obra))
            if (tema != "") {
                sb.append(Utils.LS2)
                sb.append(Utils.toH4Red(tema))
            }
            if (ref != "") {
                sb.append(Utils.LS2)
                sb.append(Utils.toRed(ref))
            }
            if (cita != "") {
                sb.append(Utils.LS2)
                sb.append(Utils.toRed(cita))
            }
            if (fecha!!.isNotEmpty() && fecha != "0000-00-00" && fecha != "0") {
                sb.append(Utils.LS2)
                sb.append(Utils.toRed(fecha))
            }
            sb.append(Utils.LS2)
            sb.append(Utils.fromHtml(texto))
            sb.append(Utils.LS2)
            return sb
        }
    val allForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            sb.append(padreForRead)
            sb.append(obraForRead)
            sb.append(temaForRead)
            sb.append(refForRead)
            sb.append(citaForRead)
            sb.append(textoForRead)
            return sb
        }
}