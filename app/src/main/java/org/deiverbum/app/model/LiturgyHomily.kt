package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.utils.Utils

class LiturgyHomily  {
    var padre = ""
    var homilyID = 0
    var homilyy = ""

    var tema = ""
    var opusFK = 0
    var date = ""
    var homily: List<Homily?>? = null

    var obra = ""
/*
    constructor(padre: String, id: Int, texto: String, tema: String, obra: Int, fecha: String) {
        this.padre = padre
        homilyID = id
        homily = texto
        this.tema = tema
        opusFK = obra
        date = fecha
    }
*/
//    constructor() {}

    val allForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            sb.append(Utils.toH3Red(padre))
            sb.append(Utils.LS2)
            sb.append(Utils.toH4Red(obra))
            sb.append(Utils.LS2)
            if (!tema.isEmpty()) {
                sb.append(Utils.toRed(tema))
                sb.append(Utils.LS2)
            }
            if (!date.isEmpty() && date != "0000-00-00") {
                sb.append(Utils.toRed(date))
                sb.append(Utils.LS2)
            }
            //sb.append(Utils.fromHtml(homilyy))
            sb.append(Utils.LS2)
            return sb
        }
    val allForRead: StringBuilder
        get() {
            val sb = StringBuilder()
            sb.append(Utils.normalizeEnd(padre))
            sb.append(Utils.normalizeEnd(obra))
            if (!tema.isEmpty()) {
                sb.append(Utils.normalizeEnd(tema))
            }
            if (!date.isEmpty() && date != "0000-00-00") {
                sb.append(Utils.normalizeEnd(date))
            }
            //sb.append(Utils.fromHtmlForRead(homily))
            return sb
        }
    val temaForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            if (!tema.isEmpty()) {
                sb.append(Utils.toH4Red(tema))
                sb.append(Utils.LS2)
            }
            return sb
        }
}