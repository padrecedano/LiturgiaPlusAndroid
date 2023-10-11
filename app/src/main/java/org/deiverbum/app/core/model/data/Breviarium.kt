package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

/**
 *
 *
 * Reúne aquellos elementos que son comunes a las diversas horas del Breviario.
 * Las clases de las diferentes horas extienden de ésta,
 * y cada una tendrá aquellos elementos que le sean propios.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */

sealed interface Breviarium : LiturgiaTypus {
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return SpannableStringBuilder()
    }
}