package org.deiverbum.app.core.model.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

/**
 *
 * Representa el contenido de un número en los rituales.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 */
data class Number(
    var n: Int,
    var content: List<Content>

) {
    fun getForView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.toH4Red("${n}. "))
        for (i in content) {
            ssb.append(i.forView())
        }
        return ssb
    }

}
