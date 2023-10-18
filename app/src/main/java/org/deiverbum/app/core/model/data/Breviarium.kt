package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

/**
 *
 * Reúne aquellos elementos que son comunes a las diversas horas del Breviario.
 * Las clases de las diferentes horas extienden de ésta,
 * y cada una tendrá aquellos elementos que le sean propios.
 *
 * @property typus Una cadena para identificar el tipo de celebración. Con este valor
 * si indica al adapter qué clase debe usarse para mapear los datos procedentes de la red.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 *
 * @see [LiturgiaTypus]
 *
 */


abstract class Breviarium(typus: String) :
    LiturgiaTypus(typus) {
    fun forView(): SpannableStringBuilder {
        return super.getHeaders()
    }
}