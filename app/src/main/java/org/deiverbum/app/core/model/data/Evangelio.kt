package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

class Evangelio : MissaeLectionum() {
    var antifona: String? = null
    override fun getAll(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        sb.append(Utils.formatSubTitle("evangelio del día"))
        sb.append(Utils.LS2)
        sb.append(book!!.liturgyName)
        sb.append("    ")
        sb.append(Utils.toRed(quote))
        sb.append(Utils.LS2)
        sb.append(textoSpan)
        return sb
    }
}