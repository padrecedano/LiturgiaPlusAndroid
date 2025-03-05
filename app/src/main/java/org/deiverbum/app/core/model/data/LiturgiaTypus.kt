package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

/**
 *
 * Esta clase sirve para manejar los diferentes tipos de celebraciones litúrgicas.
 *
 * @property typus Una cadena para identificar el tipo de celebración. Con este valor
 * si indica al adapter qué clase debe usarse para mapear los datos procedentes de la red.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 */

abstract class LiturgiaTypus
    (open val typus: String) : Sortable {
    open fun getHeaders(): SpannableStringBuilder {
        return SpannableStringBuilder()
    }

    open fun getTitle(): String {
        return ""
    }



    open fun forRead(): StringBuilder {
        return StringBuilder("")
    }

    override fun sort() {
    }

    open fun normalizeByTime(calendarTime: Int) {
    }
}