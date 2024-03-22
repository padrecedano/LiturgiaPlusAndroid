package org.deiverbum.app.core.model.data.cic


import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class Chapter(
    var n: Int = 0,
    var txt: String = "",
    var numbers: List<Canon>,

    ) {
    fun getAllForView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.toH4Red("Capítulo $n"))
        ssb.append(Constants.LS)
        ssb.append(Utils.toH4Red(txt))
        ssb.append(Constants.LS2)
        for (n in numbers) {
            ssb.append(n.getForView())
        }
        return ssb
    }
}
