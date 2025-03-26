package org.deiverbum.app.core.model.cic

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class Intro(
    var type: String = "",
    var n: Int = 0,
    var txt: List<String>
) {
    fun getAllForView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        for (s in txt) {
            ssb.append(Utils.toRed("$n. "))
            ssb.append(s)
        }
        ssb.append(Constants.LS2)
        return ssb
    }
}