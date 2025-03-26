package org.deiverbum.app.core.model.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class ParagraphusRubrica(
    override var type: String = "pr",
    var p: String? = null,
    var r: String? = null
) : Content(type) {
    override fun forView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(p)
        if (r != null) {
            ssb.append(Constants.LS2)
            ssb.append(Utils.toRed(r))
            //ssb.append(Constants.LS)
        }
        ssb.append(Constants.LS2)

        return ssb
    }


}