package org.deiverbum.app.core.model.data.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 *
 * Representa una oración en los rituales.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 */
data class Oratio(
    override var type: String = "oratio",
    var responsum: String,
    var txt: List<String>

) : Content(type) {
    override fun forView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        for (t in txt) {
            ssb.append(Utils.fromHtml(t))
            ssb.append(Utils.LS2)
        }
        ssb.append(Utils.toRed(Constants.CHAR_R))
        ssb.append(responsum)
        ssb.append(Utils.LS2)
        return ssb
    }
}