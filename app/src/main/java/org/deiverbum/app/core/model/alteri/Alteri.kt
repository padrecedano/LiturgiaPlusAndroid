package org.deiverbum.app.core.model.alteri

import org.deiverbum.app.core.model.liturgia.LiturgiaTypus

/**
 *
 * Reúne elementos varios de la liturgia, fuera de los habituales.
 *
 *  @property typus Una cadena para identificar el tipo de celebración. Con este valor
 * si indica al adapter qué clase debe usarse para mapear los datos procedentes de la red.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 *
 * @see [LiturgiaTypus]
 *
 */
abstract class Alteri(typus: String) : LiturgiaTypus(typus)