package org.deiverbum.app.core.model.data.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

class ParagraphusList(
    override var type: String = "pl",
    var txt: List<String>,

    ) : Content(type) {
    override fun forView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        for (t in txt) {
            ssb.append(Utils.toRed(Constants.PRECES_R))
            ssb.append(" ${t}")
            ssb.append(Constants.LS2)
        }
        return ssb
    }
}