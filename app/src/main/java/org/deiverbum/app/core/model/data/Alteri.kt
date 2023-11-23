package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder

/**
 *
 * Reúne elementos varios de la liturgia, fuera de los habituales.
 *
 *  @property typus Una cadena para identificar el tipo de celebración. Con este valor
 * si indica al adapter qué clase debe usarse para mapear los datos procedentes de la red.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 *
 * @see [LiturgiaTypus]
 *
 */
abstract class Alteri(typus: String) : LiturgiaTypus(typus) {

    data class Sancti(
        val sanctus: Sanctus,
        override var typus: String = "sanctii"
    ) : Alteri(typus) {
        override fun forView(calendarTime: Int): SpannableStringBuilder {
            return SpannableStringBuilder(sanctus.forView)
        }
    }

    override fun forView(calendarTime: Int): SpannableStringBuilder {
        return SpannableStringBuilder("")
    }
}