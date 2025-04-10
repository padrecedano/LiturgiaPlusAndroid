package org.deiverbum.app.core.model.alteri

/**
 * Representa los elementos de tipo "rosarium".
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2025.1
 */
data class AlteriRosarium(
    val rosarium: Rosarium,
    override var typus: String = "rosarium"
) : Alteri(typus)