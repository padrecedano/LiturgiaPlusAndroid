package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Utils

class OracionSimple {
    var titulo: String? = null
    var texto: String? = null
    private var info: String? = null
    fun getForView(nightMode: Boolean): SpannableStringBuilder {
        ColorUtils.isNightMode = nightMode
        val sb = SpannableStringBuilder()
        sb.append(Utils.toH3Red(titulo))
        if (info != null) {
            sb.append(Utils.LS2)
            sb.append(Utils.toRed(info))
        }
        sb.append(Utils.LS2)
        sb.append(Utils.fromHtml(texto))
        return sb
    }

    val forRead: StringBuilder
        get() {
            val sb = StringBuilder()
            sb.append(titulo)
            sb.append(".")
            sb.append(Utils.fromHtml(texto))
            return sb
        }
}