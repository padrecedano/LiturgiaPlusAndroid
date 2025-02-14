package org.deiverbum.app.core.model.data.book


/**
 *
 * Contenido proveniente de ficheros en la capa de datos local.
 *
 * @property typus Una cadena para identificar el tipo de archivo. Con este valor
 * se indica al adapter qué clase debe usarse para mapear los datos procedentes de la red.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 *
 * @see [LiberTypus]
 *
 */


abstract class LiberFile(typus: String) :
    LiberTypus(typus) {
    /* fun forView(): SpannableStringBuilder {
         return super.getHeaders()
     }


     override fun getTypus(): Any {
         //return this
         //return Any
         return TODO("Provide the return value")
     }*/
}