package org.deiverbum.app.core.model.data

/**
 *
 * Reúne aquellos elementos que son comunes a los Sacramentos.
 *
 * @property typus Una cadena para identificar el tipo de celebración. Con este valor
 * si indica al adapter qué clase debe usarse para mapear los datos procedentes de la red.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 * @see [LiturgiaTypus]
 *
 */

abstract class Sacramentis(typus: String) :
    LiturgiaTypus(typus)

/*
sealed interface Sacramentis : LiturgiaTypus {
    override fun forView(calendarTime: Int, hasSaint: Boolean): SpannableStringBuilder {
        return SpannableStringBuilder()
    }
}
*/
