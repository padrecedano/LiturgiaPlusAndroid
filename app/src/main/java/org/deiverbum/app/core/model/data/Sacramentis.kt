package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

/**
 *
 * Reúne aquellos elementos que son comunes a los Sacramentos.
 *
 * @property typus Una cadena para identificar el tipo de celebración. Con este valor
 * si indica al adapter qué clase debe usarse para mapear los datos procedentes de la red.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 *
 * @see [LiturgiaTypus]
 *
 */

abstract class Sacramentis(typus: String) :
    LiturgiaTypus(typus) {
    fun forView(): SpannableStringBuilder {
        return super.getHeaders()
    }
}

/*
sealed interface Sacramentis : LiturgiaTypus {
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return SpannableStringBuilder()
    }
}
*/
