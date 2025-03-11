package org.deiverbum.app.core.model.data.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class Titulus(
    override var type: String = "t",
    var txt: String

) : Content(type) {
    override fun forView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.toRed(txt))
        ssb.append(Constants.LS2)

        return ssb
    }


}
