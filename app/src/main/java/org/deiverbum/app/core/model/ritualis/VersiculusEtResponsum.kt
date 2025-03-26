package org.deiverbum.app.core.model.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

data class VersiculusEtResponsum(
    override var type: String = "vr",
    var items: List<Responsum>

) : Content(type) {
    override fun forView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()

        for (t in items) {
            ssb.append(t.forView(""))
            ssb.append(Utils.LS2)


        }
        return ssb


    }
}