package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import android.text.Spanned
import org.deiverbum.app.util.Utils
@Suppress("unused")
/**
 * Clase para los comentarios bíblicos.
 */

class BibleComment(
    var paterOpus: PaterOpus,
    var refFuente: String = "",
    var tema: String = "",
    var refBiblia: String = "",
    var fecha: Int = 0,
    var texto: String = ""
) {

    var padre: String? = null
    var obra: String? = null
    var cita = ""
    var ref = ""
    var biblica: MissaeLectionum? = null
    var dateString: String = ""

    private val textoForRead: Spanned
        get() = Utils.fromHtmlForRead(texto)
    private val citaForRead: String
        get() = if (cita != "") Utils.normalizeEnd(cita) else ""
    private val padreForRead: String
        get() = if (padre != "") Utils.normalizeEnd(padre!!) else ""
    private val obraForRead: String
        get() = if (obra != "") Utils.normalizeEnd(obra!!) else ""
    private val temaForRead: String
        get() = if (tema != "") Utils.normalizeEnd(tema) else ""
    private val refForRead: String
        get() = if (ref != "") Utils.normalizeEnd(ref) else ""

    val allForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(Utils.toH2Red(paterOpus.paterForView))
            sb.append(Utils.LS)
            sb.append(Utils.toH3Red(paterOpus.opusName))
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
            if (fecha != 0) {
                sb.append(Utils.LS2)
                this.dateString =
                    Utils.formatDate(fecha.toString(), "yyyyMMdd", "d 'de' MMMM 'de' yyyy")
                sb.append(Utils.toRed(dateString))
            }
            sb.append(Utils.LS2)
            sb.append(Utils.fromHtml(texto))
            sb.append(Utils.LS2)
            return sb
        }
    val allForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            //sb.append(Utils.normalizeEnd(paterOpus?.paterForView!!))
            sb.append(Utils.normalizeEnd(paterOpus.opusForView))
            if (tema != "") {
                sb.append(Utils.normalizeEnd(tema))
            }
            if (cita != "") {
                sb.append(Utils.normalizeEnd(cita))
            }
            sb.append(textoForRead)
            return sb
        }
}