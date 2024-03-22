package org.deiverbum.app.core.model.data.cic

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

data class Body(
    var txt: String = "",
    var list: List<String>? = null
) {
    fun getForView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(txt)
        ssb.append(Constants.LS2)

        if (list != null) {
            list!!.forEachIndexed { i, s ->
                ssb.append(Utils.toRed("${i + 1}. "))
                ssb.append(s)
                ssb.append(Constants.LS2)
            }
        }

        return ssb
    }
}