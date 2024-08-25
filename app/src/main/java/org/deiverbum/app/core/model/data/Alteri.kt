package org.deiverbum.app.core.model.data

/**
 *
 * Reúne elementos varios de la liturgia, fuera de los habituales.
 *
 *  @property typus Una cadena para identificar el tipo de celebración. Con este valor
 * si indica al adapter qué clase debe usarse para mapear los datos procedentes de la red.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2024.1
 *
 * @see [LiturgiaTypus]
 *
 */
abstract class Alteri(typus: String) : LiturgiaTypus(typus) {

    data class Sancti(
        val sanctus: Sanctus,
        override var typus: String = "sanctii"
    ) : Alteri(typus) {


        override fun forRead(): StringBuilder {
            return StringBuilder(sanctus.forRead)
        }

        override fun sort() {
        }
    }


}