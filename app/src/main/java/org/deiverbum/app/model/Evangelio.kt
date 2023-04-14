package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import org.deiverbum.app.utils.Utils

class Evangelio : MassReading() {
    var antifona: String? = null
    override fun getAll(): SpannableStringBuilder? {
        val sb = SpannableStringBuilder()
        sb.append(Utils.formatSubTitle("evangelio del día"))
        sb.append(Utils.LS2)
        sb.append(libro!!.liturgyName)
        sb.append("    ")
        sb.append(Utils.toRed(quote))
        sb.append(Utils.LS2)
        sb.append(textoSpan)
        return sb
    }
}