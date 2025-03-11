package org.deiverbum.app.core.model.data.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.Utils

data class Rubrica(
    override var type: String = "r",
    var txt: List<String>,
    var bold: Boolean = false

) : Content(type) {
    override fun forView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        for (t in txt) {
            if (bold) {
                ssb.append(Utils.toRedBold(t))
            } else {
                ssb.append(Utils.toRed(t))
            }
            ssb.append(LS2)
        }
        return ssb
    }
}
