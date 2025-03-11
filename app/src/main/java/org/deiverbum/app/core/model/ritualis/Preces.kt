package org.deiverbum.app.core.model.data.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Constants.CHAR_R
import org.deiverbum.app.util.Constants.PRECES_R
import org.deiverbum.app.util.Utils
import org.deiverbum.app.util.Utils.LS2

data class Preces(
    override var type: String = "preces",
    var intro: String? = null,
    var responsum: String,
    var preces: List<String>

) : Content(type) {
    override fun forView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        if (intro != null) {
            ssb.append(Utils.fromHtml(intro!!))
            ssb.append(": ${responsum}")
            ssb.append(LS2)
        }
        for (t in preces) {
            ssb.append(Utils.toRed(PRECES_R))
            ssb.append(t)
            ssb.append(LS2)
            ssb.append(Utils.toRed(CHAR_R))
            ssb.append(responsum)

            //ssb.append(" ")
            //ssb.append(Utils.toRed(pericopa))
            ssb.append(Constants.LS2)
        }
        return ssb


    }
}