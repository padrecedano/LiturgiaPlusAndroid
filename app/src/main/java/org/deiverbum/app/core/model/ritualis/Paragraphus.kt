package org.deiverbum.app.core.model.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants.LS
import org.deiverbum.app.util.Constants.LS2
import org.deiverbum.app.util.Utils

/**
 *
 * Representa una párrofo en los rituales.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 */
data class Paragraphus(
    override var type: String = "p",
    var txt: List<String>,
    var ls: Int? = null,
    var bold: Boolean? = null
) : Content(type) {
    override fun forView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        for (t in txt) {
            if (bold != null && bold!!) {
                ssb.append(Utils.fromHtml("<b>${t}</b>"))
            } else {
                ssb.append(Utils.fromHtml(t))
            }
            ssb.append(if (ls != null) LS else LS2)
        }
        if (ls != null) {
            ssb.append(LS2)
        }
        return ssb
    }
}