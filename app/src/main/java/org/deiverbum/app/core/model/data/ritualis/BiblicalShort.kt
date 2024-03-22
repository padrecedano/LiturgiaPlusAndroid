package org.deiverbum.app.core.model.data.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 *
 * Representa un lectura bíblica corta en los rituales.
 *
 *  @property type El tipo para identificar la clase de mapeo desde el JSON.
 *  @property txt El texto de la lectura.
 *  @property pericopa La perícopa o referencia bíblica.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 */
data class BiblicalShort(
    override var type: String = "biblicalShort",
    var txt: List<String>,
    var pericopa: String

) : Content(type) {
    override fun forView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(Utils.toRed(pericopa))
        ssb.append(Constants.LS2)
        for (t in txt) {
            ssb.append(Utils.fromHtml(t))
            ssb.append(Utils.LS)
        }
        ssb.append(Constants.LS2)
        return ssb
    }


}