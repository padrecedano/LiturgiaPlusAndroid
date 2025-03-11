package org.deiverbum.app.core.model.data.ritualis

import android.text.SpannableStringBuilder

/**
 *
 * Representa el contenido en los rituales.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 */
abstract class Content
    (open val type: String) {
    open fun getHeaders(): SpannableStringBuilder {
        return SpannableStringBuilder()
    }

    open fun forView(): SpannableStringBuilder {
        return SpannableStringBuilder()
    }

    open fun forRead(): StringBuilder {
        return StringBuilder("")
    }

}
