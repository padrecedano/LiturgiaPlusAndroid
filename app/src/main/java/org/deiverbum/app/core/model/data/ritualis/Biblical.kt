package org.deiverbum.app.core.model.data.ritualis

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Constants
import org.deiverbum.app.util.Utils

/**
 *
 * Representa un lectura bíblica en los rituales.
 *
 *  @property type El tipo para identificar la clase de mapeo desde el JSON.
 *  @property book El nombre del libro.
 *  @property pericopa La perícopa o referencia bíblica.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 */
data class Biblical(
    override var type: String = "biblical",
    var book: String,
    var pericopa: String

) : Content(type) {
    override fun forView(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder()
        ssb.append(book)
        ssb.append(" ")
        ssb.append(Utils.toRed(pericopa))
        ssb.append(Constants.LS2)
        return ssb
    }


}