package org.deiverbum.app.core.model.cic

import android.text.SpannableStringBuilder
import com.squareup.moshi.JsonClass
import org.deiverbum.app.util.Constants.LS
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.Utils

@JsonClass(generateAdapter = true)
data class Title(
    var n: Int = 0,
    var txt: String = "",
    var intro: List<Intro>,
    var content: List<Content>,

    ) {
    fun getForView(isNightMode: Boolean): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()

        ssb.append(Utils.toH3Red("Título $n"))
        ssb.append(LS)
        ssb.append(Utils.toH3Red(txt))
        ssb.append(LS2)
        for (i in intro) {
            ssb.append(i.getAllForView())
        }
        for (c in content) {
            ssb.append(c.getAllForView())
        }
        return ssb
    }
}