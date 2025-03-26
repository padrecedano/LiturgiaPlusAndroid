package org.deiverbum.app.core.model.book


import android.text.SpannableStringBuilder
import org.deiverbum.app.core.model.data.Sortable

/**
 *
 * Esta clase sirve para manejar los diferentes tipos de libros.
 *
 * @property typus Una cadena para identificar el tipo de libro. Con este valor
 * se indica al adapter qué clase debe usarse para mapear los datos procedentes de la fuente de datos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 *
 */

//@JsonClass(generateAdapter = true)
abstract class LiberTypus
    (open val typus: String, open val bookType: Int) : Sortable {
    open fun getHeaders(): SpannableStringBuilder {
        return SpannableStringBuilder()
    }


    open fun forRead(): StringBuilder {
        return StringBuilder("")
    }

    override fun sort() {
    }
}